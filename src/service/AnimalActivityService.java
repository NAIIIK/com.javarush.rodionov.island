package service;

import entity.Animal;
import entity.Island;
import entity.Location;
import util.Util;

public class AnimalActivityService implements IslandAppService {

    private final Island island;

    public AnimalActivityService(Island island) {
        this.island = island;
    }

    @Override
    public void perform() {
        for (Location location : island.getAllLocations()) {
            for (Animal animal : location.getAnimals()) {
                Util.performRandomActivity(animal);
                animal.decreaseSatiety();
            }
        }
    }
}
