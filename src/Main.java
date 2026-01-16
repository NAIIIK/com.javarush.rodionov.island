import config.Settings;
import service.*;
import service.controller.SimulationController;
import service.task.IslandAppTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        entity.Island island = entity.Island.getInstance();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Settings.CORE_POOL_SIZE);
        SimulationController controller = new SimulationController(island, executorService);


        StatisticsService statisticsService = new StatisticsService(island);
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