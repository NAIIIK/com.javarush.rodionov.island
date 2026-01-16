package app;

import config.Settings;
import entity.island.Island;
import service.AnimalActivityService;
import service.PlantGrowthService;
import service.SimulationService;
import service.StatisticsService;
import controller.SimulationController;
import task.IslandAppTask;
import view.ConsoleUI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ApplicationRunner {
    private ApplicationRunner() {}

    public static void run(Island island) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Settings.CORE_POOL_SIZE);
        SimulationController controller = new SimulationController(island, executorService);
        ConsoleUI consoleUI = new ConsoleUI(island);

        StatisticsService statisticsService = new StatisticsService(consoleUI);
        AnimalActivityService animalActivityService = new AnimalActivityService(island);
        PlantGrowthService plantGrowthService = new PlantGrowthService(island);
        SimulationService simulationService = new SimulationService(controller);

        executorService.scheduleAtFixedRate(new IslandAppTask(animalActivityService),
                0, 1, TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new IslandAppTask(plantGrowthService),
                0, 1, TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new IslandAppTask(statisticsService),
                0, 1, TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new IslandAppTask(simulationService),
                0, 1, TimeUnit.SECONDS);
    }
}
