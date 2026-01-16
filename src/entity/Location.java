package entity;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private final Integer coordinateX;
    private final Integer coordinateY;
    private List<Animal> animals;
    private List<Plant> plants;
    private List<Location> neighbourLocations;

    public Location(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        animals = new ArrayList<>();
        plants = new ArrayList<>();
        neighbourLocations = new ArrayList<>();
    }

    public int countPlants() {
        return plants.size();
    }

    public int countAnimals(Class<? extends Animal> type) {
        return (int) animals.stream()
                .filter(animal -> animal.getClass() == type)
                .count();
    }

    public boolean canAddPlant(Plant plant) {
        int current = countPlants();
        return current < plant.getPlantStat().getMaxQuantityOnCell();
    }

    public boolean canAddAnimal(Animal animal) {
        int current = countAnimals(animal.getClass());
        return current < animal.getAnimalStat().getMaxQuantityOnCell();
    }

    public void addAnimal(Animal animal) {
        if (!canAddAnimal(animal)) throw new RuntimeException();

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
        if (!canAddPlant(plant)) throw new RuntimeException();

        plants.add(plant);
        plant.setLocation(this);
    }

    public List<Location> getNeighbourLocations() {
        return neighbourLocations;
    }

    public List<Location> getAccessibleLocations(Animal animal) {
        return getNeighbourLocations().stream()
                .filter(loc -> loc.countAnimals(animal.getClass()) < animal.getAnimalStat().getMaxQuantityOnCell())
                .toList();
    }

    public void addNeighbourLocation(Location location) {
        neighbourLocations.add(location);
    }
}
