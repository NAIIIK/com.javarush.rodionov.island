package entity.island;

import entity.Eatable;
import entity.animal.Animal;
import entity.plant.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class Location {
    private final Integer coordinateX;
    private final Integer coordinateY;
    private final List<Animal> animals = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();
    private final List<Location> neighbourLocations = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public Location(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public int countPlants() {
        return plants.size();
    }

    public int countAnimals() {
        return animals.size();
    }

    public int countAnimalsByType(Class<? extends Animal> type) {
        return (int) animals.stream()
                .filter(animal -> animal.getClass() == type)
                .count();
    }

    public boolean canAddPlant(Plant plant) {
        int current = countPlants();
        return current < plant.getPlantStat().getMaxQuantityOnCell();
    }

    public boolean canAddAnimal(Animal animal) {
        int current = countAnimalsByType(animal.getClass());
        return current < animal.getAnimalStat().getMaxQuantityOnCell();
    }

    public void addAnimal(Animal animal) {
        if (!canAddAnimal(animal)) return;

        animals.add(animal);
        animal.setLocation(this);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public void addPlant(Plant plant) {
        if (!canAddPlant(plant)) return;

        plants.add(plant);
        plant.setLocation(this);
    }

    public List<Location> getNeighbourLocations() {
        return neighbourLocations;
    }

    public List<Location> getAccessibleLocations(Animal animal) {
        return getNeighbourLocations().stream()
                .filter(loc -> loc.countAnimalsByType(animal.getClass()) < animal.getAnimalStat().getMaxQuantityOnCell())
                .toList();
    }

    public void addNeighbourLocation(Location location) {
        neighbourLocations.add(location);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Eatable> getAllOrganisms() {
        return Stream.concat(animals.stream(), plants.stream())
                .toList();
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
