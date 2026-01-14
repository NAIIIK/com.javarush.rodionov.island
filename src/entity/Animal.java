package entity;

import config.stat.AnimalStat;
import config.Settings;
import repository.AnimalFactory;
import util.Util;

import java.util.List;

public abstract class Animal implements Eatable {
    private final AnimalStat animalStat;
    public Location location;
    private double satiety;
    private boolean alive;

    public Animal() {
        this.animalStat = Settings.ANIMAL_STATS.get(this.getClass());
        this.satiety = animalStat.getFedUpWeight()/2;
        this.alive = true;
    }

    public boolean tryEat(Eatable food) {
        int eatingChance = Util.getEatingChance(this, food);
        int random = Util.getRandomInt(100);

        if (random < eatingChance) {
            eat(food);
            return true;
        }

        return false;
    }

    private void eat(Eatable food) {
        double foodWeight = food.getWeight();

        double canEat = Math.min(foodWeight, animalStat.getFedUpWeight() - satiety);

        if (canEat <= 0) return;

        satiety += canEat;

        food.die();
    }

    @Override
    public double getWeight() {
        return animalStat.getWeight();
    }

    @Override
    public void die() {
        alive = false;
        location.removeAnimal(this);
    }

    public void breed() {
        AnimalFactory<? extends Animal> factory = new AnimalFactory<>(this.getClass());

        int count = location.countAnimals(this.getClass());

        if (satiety < animalStat.getFedUpWeight()/2) return;
        if (count >= animalStat.getMaxQuantityOnCell() || count < 4) return;

        Animal baby = factory.create();
        location.addAnimal(baby);
    }

    public void move() {
        //TODO: добавить учет максимального кол-ва клеток за тик
        List<Location> neighbourLocations = location.getNeighbourLocations();

        List<Location> accessibleLocations = neighbourLocations.stream()
                .filter(loc -> loc.countAnimals(this.getClass()) < animalStat.getMaxQuantityOnCell())
                .toList();

        if (accessibleLocations.isEmpty()) return;

        Location newLocation = accessibleLocations.get(Util.getRandomInt(accessibleLocations.size()));
        location.removeAnimal(this);
        newLocation.addAnimal(this);
        location = newLocation;
    }

    public AnimalStat getAnimalStat() {
        return animalStat;
    }

    public boolean isAlive() {
        return alive;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
