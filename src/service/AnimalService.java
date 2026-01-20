package service;

import config.Settings;
import config.stat.AnimalStat;
import entity.Eatable;
import entity.animal.Animal;
import entity.island.Island;
import entity.island.Location;
import exception.AnimalCreationException;
import repository.AnimalFactory;
import util.Util;

import java.util.List;
import java.util.stream.Stream;

public final class AnimalService {
    public static AnimalService instance;
    private final LockService lockService = LockService.getInstance();

    private AnimalService() {}

    public static synchronized AnimalService getInstance() {
        if (instance == null) instance = new AnimalService();
        return instance;
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
                    addAnimal(location, animal);
                }
            } catch (AnimalCreationException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void populateAllAnimals(Island island) {
        island.getAllLocations().forEach(this::populateAnimals);
    }

    public void addAnimal(Location location, Animal animal) {
        lockService.withLocationLock(location, () -> {
            long count = location.getAnimalsSnapshot().stream()
                    .filter(a -> a.getClass() == animal.getClass())
                    .count();

            if (count < animal.getStat().getMaxQuantityOnCell()) {
                location.addAnimal(animal);
                animal.setLocation(location);
            }
        });
    }

    public void eat(Animal animal) {
        Location loc = animal.getLocation();
        lockService.withLocationLock(loc, () -> {
            if (!loc.getAnimalsSnapshot().contains(animal)) return;

            List<Eatable> foodOptions = Stream.concat(loc.getAnimalsSnapshot().stream(), loc.getPlantsSnapshot().stream())
                    .filter(f -> f != animal)
                    .toList();

            if (foodOptions.isEmpty()) return;

            Eatable food = foodOptions.get(Util.getRandomInt(foodOptions.size()));

            if (!animal.canEat(food)) return;

            int eatingChance = Util.getEatingChance(animal, food);
            int random = Util.getRandomInt(100);

            if (random < eatingChance) {
                double canEat = Math.min(food.getWeight(),
                        animal.getStat().getFedUpWeight() - animal.getSatiety());

                if (canEat > 0) {
                    animal.increaseSatiety(canEat);
                    food.die();
                }
            }
        });
    }

    public void move(Animal animal) {
        Location from = animal.getLocation();
        int maxSteps = animal.getStat().getMaxMoveSpeed();

        if (maxSteps == 0) return;

        int steps = Util.getRandomInt(maxSteps + 1);
        if (steps == 0) return;

        Location to = from;
        for (int i = 0; i < steps; i++) {
            List<Location> accessible = to.getNeighbourLocations().stream()
                    .filter(loc -> loc.countAnimalsByType(animal.getClass()) < animal.getStat().getMaxQuantityOnCell())
                    .toList();

            if (accessible.isEmpty()) break;

            to = accessible.get(Util.getRandomInt(accessible.size()));
        }

        if (to != from) {
            Location tempTo = to;
            lockService.withDoubleLocationLock(from, tempTo, () -> {
                if (!from.getAnimalsOriginal().contains(animal)) return;
                if (tempTo.countAnimalsByType(animal.getClass()) >= animal.getStat().getMaxQuantityOnCell()) return;

                from.removeAnimal(animal);
                tempTo.addAnimal(animal);
                animal.setLocation(tempTo);
            });
        }
    }

    public void breed(Animal animal) {
        Location loc = animal.getLocation();
        lockService.withLocationLock(loc, () -> {
            if (animal.getSatiety() < animal.getStat().getFedUpWeight()/2) return;

            int count = loc.countAnimalsByType(animal.getClass());
            if (count >= animal.getStat().getMaxQuantityOnCell() || count < 4) return;

            try {
                Animal baby = new AnimalFactory<>(animal.getClass()).create();
                baby.setLocation(loc);
                loc.addAnimal(baby);
            } catch (AnimalCreationException e) {
                System.err.println(e.getMessage());
            }
        });
    }

    public void die(Animal animal) {
        Location loc = animal.getLocation();
        lockService.withLocationLock(loc, () -> {
            loc.removeAnimal(animal);
        });
    }
}
