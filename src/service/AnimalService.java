package service;

import config.Settings;
import config.stat.AnimalStat;
import entity.animal.Animal;
import entity.island.Island;
import entity.island.Location;
import exception.AnimalCreationException;
import repository.AnimalFactory;
import util.Util;

public class AnimalService {
    public void populateAnimals(Location location) {
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

    public void populateAllAnimals(Island island) {
        island.getAllLocations().forEach(this::populateAnimals);
    }
}
