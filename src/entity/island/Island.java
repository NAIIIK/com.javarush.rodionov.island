package entity.island;

import config.Settings;
import config.stat.AnimalStat;
import entity.animal.Animal;
import entity.plant.Plant;
import exception.AnimalCreationException;
import repository.AnimalFactory;
import repository.PlantFactory;
import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Island {
    private static Island instance;

    private final Location[][] locations = new Location[Settings.ISLAND_WIDTH][Settings.ISLAND_LENGTH];

    private Island() {
        init();
        initNeighbours();
        populate();
    }

    public static Island getInstance() {
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
                if (y < locations[x].length - 1) current.addNeighbourLocation(locations[x][y+1]);
            }
        }
    }

    private void populate() {
        for (Location location : getAllLocations()) {
            growPlants(location);
            populateAnimals(location);
        }
    }
    public List<Location> getAllLocations() {
        List<Location> allLocations = new ArrayList<>();

        for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
            for (int y = 0; y < Settings.ISLAND_LENGTH; y++) {
                allLocations.add(locations[x][y]);
            }
        }

        return allLocations;
    }

    public void growPlants(Location location) {
        int maxPlants = Settings.PLANT_STATS.getMaxQuantityOnCell();
        int current = location.countPlants();

        int count = Util.getRandomInt((maxPlants - current) + 1);

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

            try {
                AnimalFactory<? extends Animal> factory = new AnimalFactory<>(animalClass);

                for (int i = 0; i < count; i++) {
                    Animal animal = factory.create();
                    location.addAnimal(animal);
                }
            } catch (AnimalCreationException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public Map<Class<? extends Animal>, Integer> getAnimalsCountByType() {
        Map<Class<? extends Animal>, Integer> animalsCount = new HashMap<>();

        for (Class<? extends Animal> animalClass : Settings.ANIMAL_STATS.keySet()) {
            animalsCount.put(animalClass, 0);
        }

        for (Location location : getAllLocations()) {
            for (Class<? extends Animal> c : Settings.ANIMAL_STATS.keySet()) {
                int current = animalsCount.get(c);
                animalsCount.put(c, current + location.countAnimals(c));
            }
        }

        return animalsCount;
    }

    public int getTotalAnimalsCount() {
        return getAnimalsCountByType().values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getPlantsCount() {
        int sum = 0;

        for (Location location : getAllLocations()) {
            sum += location.countPlants();
        }

        return sum;
    }
}
