package controller;

import config.Settings;
import entity.island.Island;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationController {
    private final Island island;
    private final ScheduledExecutorService executor;
    private AtomicInteger tick = new AtomicInteger();

    public SimulationController(Island island, ScheduledExecutorService executor) {
        this.island = island;
        this.executor = executor;
    }

    public boolean checkSimulation() {
        if (executor.isShutdown()) return true;

        int currentTick = tick.incrementAndGet();

        boolean noAnimals = island.getAllAnimalsCount() == 0;
        boolean noPlants = island.getPlantsCount() == 0;
        boolean timeout = currentTick >= Settings.MAX_SIMULATION_TICK_DURATION;

        return noAnimals || noPlants || timeout;
    }
}