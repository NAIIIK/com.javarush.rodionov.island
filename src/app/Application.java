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

        new PlantService().growAllPlants(island);
        new AnimalService().populateAllAnimals(island);

        View view = new ConsoleUI(island);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(Settings.CORE_POOL_SIZE); // для задач симуляции

        SimulationController controller = new SimulationController(island, scheduler);

        TickTask tickTask = new TickTask(island, view, executorService, scheduler, controller);

        scheduler.scheduleAtFixedRate(tickTask, 0, 1, TimeUnit.SECONDS);
    }
}