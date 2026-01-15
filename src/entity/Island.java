package entity;

import config.Settings;
import config.stat.AnimalStat;
import repository.AnimalFactory;
import repository.PlantFactory;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Island {
    private static Island instance;

    private final Location[][] locations = new Location[Settings.ISLAND_WIDTH][Settings.ISLAND_LENGTH];

    private Island() {
        init();
        initNeighbours();
        populate();
    }

    public static synchronized Island getInstance() {
        if (instance == null) instance = new Island();
        return instance;
    }

    private void init() {
        for (int x = 0; x < locations.length; x++) {
            for (int y = 0; y < locations[x].length; y++) {
                locations[x][y] = new Location(x, y);
            }
        }
    }

    private void initNeighbours() {
        for (int x = 0; x < locations.length; x++) {
            for (int y = 0; y < locations[x].length; y++) {
                Location current = locations[x][y];
                if (x > 0) current.addNeighbourLocation(locations[x-1][y]);
                if (x < locations.length - 1) current.addNeighbourLocation(locations[x+1][y]);
                if (y > 0) current.addNeighbourLocation(locations[x][y-1]);
                if (y < locations[x].length) current.addNeighbourLocation(locations[x][y+1]);
            }
        }
    }

    public Location getRandomLocation() {
        int x = Util.getRandomInt(Settings.ISLAND_WIDTH);
        int y = Util.getRandomInt(Settings.ISLAND_LENGTH);

        return locations[x][y];
    }

    private void populate() {
        for (Location location : getAllLocations()) {
            populatePlants(location);
            populateAnimals(location);
        }
    }
    private List<Location> getAllLocations() {
        List<Location> allLocations = new ArrayList<>();

        for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
            for (int y = 0; y < Settings.ISLAND_LENGTH; y++) {
                allLocations.add(locations[x][y]);
            }
        }

        return allLocations;
    }

    private void populatePlants(Location location) {
        int maxPlants = Settings.PLANT_STATS.getMaxQuantityOnCell();
        int count = Util.getRandomInt(maxPlants + 1);

        PlantFactory factory = new PlantFactory();

        for (int i = 0; i < count; i++) {
            Plant plant = factory.create();
            location.addPlant(plant);
        }
    }

    private void populateAnimals(Location location) {
        for (Class<? extends Animal> animalClass : Settings.ANIMAL_STATS.keySet()) {
            AnimalStat animalStat = Settings.ANIMAL_STATS.get(animalClass);

            int maxAnimals = animalStat.getMaxQuantityOnCell();
            int count = Util.getRandomInt(maxAnimals + 1);

            AnimalFactory<? extends Animal> factory = new AnimalFactory<>(animalClass);

            for (int i = 0; i < count; i++) {
                Animal animal = factory.create();
                location.addAnimal(animal);
            }
        }
    }
}
