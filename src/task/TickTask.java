package task;

import controller.SimulationController;
import entity.island.Island;
import view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;
import java.util.concurrent.ScheduledExecutorService;

public class TickTask implements Runnable{
    private final Island island;
    private final View view;
    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduler;
    private final SimulationController controller;

    public TickTask (
            Island island,
            View view,
            ExecutorService executorService,
            ScheduledExecutorService scheduler,
            SimulationController controller) {
        this.island = island;
        this.view = view;
        this.executorService = executorService;
        this.scheduler = scheduler;
        this.controller = controller;
    }

    @Override
    public void run() {
        Phaser phaser = new Phaser(1);

        executorService.submit(new SimulationTask(new AnimalActivityTask(island), phaser));
        executorService.submit(new SimulationTask(new PlantGrowthTask(island), phaser));
        executorService.submit(new SimulationTask(new StatisticsTask(view), phaser));

        phaser.arriveAndAwaitAdvance();

        if (controller.checkSimulation()) {
            scheduler.shutdown();
            executorService.shutdown();
            try {
                scheduler.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);
                executorService.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\n=== SIMULATION STOPPED ===");
        }
    }
}
