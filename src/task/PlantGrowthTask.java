package task;

import entity.island.Island;
import service.PlantService;

public class PlantGrowthTask implements Runnable {
    private final Island island;
    private final PlantService plantService = PlantService.getInstance();

    public PlantGrowthTask(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        plantService.growAllPlants(island);
    }
}