package task;

import controller.SimulationController;
import entity.island.Island;
import view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;
import java.util.concurrent.ScheduledExecutorService;

public class TickTask implements Runnable{
    private final Island island;
    private final ExecutorService executorService;
    private final View view;
    private final ScheduledExecutorService simulationScheduler;
    private final SimulationController controller;
    private final Phaser phaser = new Phaser(1);

    public TickTask(
            Island island,
            ExecutorService executorService,
            View view,
            ScheduledExecutorService simulationScheduler,
            SimulationController controller) {
        this.island = island;
        this.executorService = executorService;
        this.view = view;
        this.simulationScheduler = simulationScheduler;
        this.controller = controller;
    }

    @Override
    public void run() {
        SimulationTask[] tasks = new SimulationTask[] {
                new SimulationTask(new AnimalActivityTask(island), phaser),
                new SimulationTask(new PlantGrowthTask(island), phaser),
                new SimulationTask(new StatisticsTask(view), phaser)
        };

        phaser.bulkRegister(tasks.length);

        for (SimulationTask task : tasks) executorService.submit(task);

        phaser.arriveAndAwaitAdvance();

        if (controller.checkSimulation()) {
            simulationScheduler.shutdown();
            executorService.shutdown();
            try {
                if (!simulationScheduler.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) simulationScheduler.shutdownNow();
                if (!executorService.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) executorService.shutdownNow();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\n=== SIMULATION STOPPED ===");
        }
    }
}
