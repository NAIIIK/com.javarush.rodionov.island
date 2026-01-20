package task;

import entity.animal.Animal;
import entity.island.Island;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class AnimalActivityTask implements Runnable {

    private final Island island;

    public AnimalActivityTask(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        island.getAllLocations().forEach(location -> {
            List<Animal> animalSnapshot;

            location.getLock().lock();
            try {
                animalSnapshot = new ArrayList<>(location.getAnimals());
            } finally {
                location.getLock().unlock();
            }

            for (Animal animal : animalSnapshot) {
                Util.performRandomActivity(animal);
            }
        });
    }
}
