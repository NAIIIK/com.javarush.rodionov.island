package repository;

import config.Settings;
import entity.Island;
import entity.Location;
import entity.Plant;

public class PlantFactory implements Factory<Plant> {
    private final Island island = Island.getInstance();
    private static final int MAX_ATTEMPTS = Settings.ISLAND_LENGTH * Settings.ISLAND_WIDTH;


    @Override
    public Plant create() {
        Plant plant = new Plant();

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            Location location = island.getRandomLocation();
            if (location.canAddPlant(plant)) {
                location.addPlant(plant);
                return plant;
            }
        }

        throw new RuntimeException();
    }
}
