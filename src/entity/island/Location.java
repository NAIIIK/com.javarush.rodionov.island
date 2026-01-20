package entity.island;

import entity.animal.Animal;
import entity.plant.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public final class Location {
    private final Integer coordinateX;
    private final Integer coordinateY;
    private final Integer id;
    private final List<Animal> animals = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();
    private final List<Location> neighbourLocations = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public Location(int coordinateX, int coordinateY, int id) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.id = id;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
    public void addPlant(Plant plant) {
        plants.add(plant);
}
    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public int countAnimalsByType(Class<? extends Animal> type) {
        return (int) animals.stream().filter(animal -> animal.getClass() == type).count();
    }

    public List<Location> getNeighbourLocations() {
        return neighbourLocations;
    }

    public void addNeighbourLocation(Location location) {
        neighbourLocations.add(location);
    }

    public List<Animal> getAnimalsSnapshot() {
        return List.copyOf(animals);
    }

    public List<Animal> getAnimalsOriginal() {
        return animals;
    }

    public List<Plant> getPlantsSnapshot() {
        return List.copyOf(plants);
    }

    public List<Plant> getPlantsOriginal() {
        return plants;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public Integer getId() {
        return id;
    }
}
