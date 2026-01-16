package service.controller;

import config.Settings;
import entity.Island;

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
        // TODO: fix try-catch block
        int currentTick = tick.incrementAndGet();

        int animalsCount = 0;

        for (var entry : island.getAnimalsCount().entrySet()) {
            animalsCount += entry.getValue();
        }

        if (animalsCount == 0 ||
                island.getPlantsCount() == 0 ||
                currentTick > Settings.MAX_SIMULATION_DURATION) {

            try {
                System.out.println("=== SIMULATION STOPPED ===");
                executor.shutdown();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) executor.shutdownNow();
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
