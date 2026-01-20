package service;

import config.Settings;
import entity.island.Island;
import entity.island.Location;
import entity.plant.Plant;
import repository.PlantFactory;
import util.Util;

public final class PlantService {
    private static PlantService instance;
    private final PlantFactory factory = new PlantFactory();
    private final LockService lockService = LockService.getInstance();

    private PlantService() {}

    public static synchronized PlantService getInstance() {
        if (instance == null) instance = new PlantService();
        return instance;
    }

    public void growPlants(Location location) {
        lockService.withLocationLock(location, () -> {
            int maxPlants = Settings.PLANT_STATS.getMaxQuantityOnCell();
            int current = location.getPlantsOriginal().size();
            int freeSpace = maxPlants - current;

            if (freeSpace <= 0) return;

            int count = Util.getRandomInt(freeSpace + 1);

            for (int i = 0; i < count; i++) {
                Plant plant = factory.create();
                plant.setLocation(location);
                location.addPlant(plant);
            }
        });
    }

    public void growAllPlants(Island island) {
        island.getAllLocations().forEach(this::growPlants);
    }

    public void rot(Plant plant) {
        Location loc = plant.getLocation();
        lockService.withLocationLock(loc, () -> loc.removePlant(plant));
    }
}
