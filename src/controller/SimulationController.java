package controller;

import config.Settings;
import entity.island.Island;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationController {
    private final Island island;
    private final ScheduledExecutorService executor;
    private final AtomicInteger tick = new AtomicInteger();

    public SimulationController(Island island, ScheduledExecutorService executor) {
        this.island = island;
        this.executor = executor;
    }

    public void checkSimulation() {
        if (executor.isShutdown()) return;

        int currentTick = tick.incrementAndGet();

        boolean noAnimals = island.getTotalAnimalsCount() == 0;
        boolean noPlants = island.getPlantsCount() == 0;

        if (noAnimals ||
                noPlants ||
                currentTick >= Settings.MAX_SIMULATION_TICK_DURATION) {

            try {
                System.out.println("\n=== SIMULATION STOPPED ===");
                executor.shutdown();
                if (!executor.awaitTermination(2, TimeUnit.SECONDS)) executor.shutdownNow();
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
