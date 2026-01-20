package service;

import config.Settings;
import entity.island.Island;
import entity.island.Location;
import repository.PlantFactory;
import util.Util;

public class PlantService {
    private final PlantFactory factory = new PlantFactory();

    public void growPlants(Location location) {
        int maxPlants = Settings.PLANT_STATS.getMaxQuantityOnCell();

        location.getLock().lock();
        try {
            int current = location.countPlants();
            int freeSpace = maxPlants - current;

            if (freeSpace <= 0) return;

            int count = Util.getRandomInt(freeSpace + 1);

            for (int i = 0; i < count; i++) {
                location.addPlant(factory.create());
            }
        } finally {
            location.getLock().unlock();
        }
    }

    public void growAllPlants(Island island) {
        island.getAllLocations().forEach(this::growPlants);
    }
}
