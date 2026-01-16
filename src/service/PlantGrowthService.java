package service;

import entity.island.Island;
import entity.island.Location;

public class PlantGrowthService implements IslandAppService {
    private final Island island;

    public PlantGrowthService(Island island) {
        this.island = island;
    }

    @Override
    public void perform() {
        for (Location location : island.getAllLocations()) {
            island.growPlants(location);
        }
    }
}