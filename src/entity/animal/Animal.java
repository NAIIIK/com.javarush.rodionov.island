package entity.animal;

import config.stat.AnimalStat;
import config.Settings;
import entity.Eatable;
import entity.island.Location;
import repository.AnimalFactory;
import util.Util;

import java.util.List;

public abstract class Animal implements Eatable {
    private final AnimalStat animalStat;
    private Location location;
    private double satiety;

    protected Animal() {
        this.animalStat = Settings.ANIMAL_STATS.get(this.getClass());
        this.satiety = animalStat.getFedUpWeight()/2;
    }

    protected abstract boolean canEat(Eatable food);

    public void tryEat() {
        List<Eatable> foodOptions = location.getAllOrganisms();

        if (foodOptions.isEmpty()) return;

        Eatable food = foodOptions.get(Util.getRandomInt(foodOptions.size()));

        if (!canEat(food)) return;

        int eatingChance = Util.getEatingChance(this, food);
        int random = Util.getRandomInt(100);

        if (random < eatingChance) {
            eat(food);
        }
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

    public void tryMove() {
        if (animalStat.getMaxMoveSpeed() == 0) return;

        int steps = Util.getRandomInt(animalStat.getMaxMoveSpeed() + 1);

        if (steps == 0) return;

        Location current = location;

        for (int i = 0; i < steps; i++) {
            List<Location> accessibleLocations = current.getAccessibleLocations(this);

            if (accessibleLocations.isEmpty()) break;

            current = accessibleLocations.get(Util.getRandomInt(accessibleLocations.size()));
        }

        if (current != location) {
            location.removeAnimal(this);
            current.addAnimal(this);
            location = current;
        }
    }

    public AnimalStat getAnimalStat() {
        return animalStat;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void decreaseSatiety() {
        satiety *= 0.9;

        if (satiety < (0.2 * animalStat.getFedUpWeight())) die();
    }
}
