package entity.island;

import config.Settings;
import entity.animal.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Island {
    private static Island instance;

    private final Location[][] locations = new Location[Settings.ISLAND_WIDTH][Settings.ISLAND_LENGTH];
    private final List<Location> allLocations = new ArrayList<>();

    private Island() {
        init();
        initNeighbours();
    }

    public static synchronized Island getInstance() {
        if (instance == null) instance = new Island();
        return instance;
    }

    private void init() {
        for (int x = 0; x < locations.length; x++) {
            for (int y = 0; y < locations[x].length; y++) {
                Location loc = new Location(x, y);
                locations[x][y] = loc;
                allLocations.add(loc);
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

    public List<Location> getAllLocations() {
        return allLocations;
    }

    public Map<Class<? extends Animal>, Integer> getAnimalsCountByType() {
        Map<Class<? extends Animal>, Integer> animalsCount = new HashMap<>();

        for (Class<? extends Animal> animalClass : Settings.ANIMAL_STATS.keySet()) {
            animalsCount.put(animalClass, 0);
        }

        for (Location location : allLocations) {
            location.getLock().lock();
            try {
                for (Class<? extends Animal> c : Settings.ANIMAL_STATS.keySet()) {
                    int current = animalsCount.get(c);
                    animalsCount.put(c, current + location.countAnimalsByType(c));
                }
            } finally {
                location.getLock().unlock();
            }
        }

        return animalsCount;
    }

    public int getAllAnimalsCount() {
        return allLocations.stream()
                .mapToInt(Location::countAnimals)
                .sum();
    }

    public int getPlantsCount() {
        return allLocations.stream()
                .mapToInt(Location::countPlants)
                .sum();
    }
}
