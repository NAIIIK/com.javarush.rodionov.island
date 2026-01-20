package app;

import config.Settings;
import controller.SimulationController;
import entity.island.Island;
import service.AnimalService;
import service.PlantService;
import task.TickTask;
import view.ConsoleUI;
import view.View;

import java.util.concurrent.*;

public class Application {
    public static void main(String[] args) {
        Island island = Island.getInstance();

        PlantService.getInstance().growAllPlants(island);
        AnimalService.getInstance().populateAllAnimals(island);

        View view = new ConsoleUI(island);

        ScheduledExecutorService simulationScheduler = Executors.newSingleThreadScheduledExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(Settings.CORE_POOL_SIZE);

        SimulationController controller = new SimulationController(island, simulationScheduler);

        TickTask tickTask = new TickTask(island, executorService, view, simulationScheduler, controller);

        simulationScheduler.scheduleAtFixedRate(tickTask, 0, Settings.DELAY_SECONDS, TimeUnit.SECONDS);
    }
}