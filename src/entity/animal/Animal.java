package entity.animal;

import config.stat.AnimalStat;
import config.Settings;
import entity.Eatable;
import entity.island.Location;
import service.AnimalService;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Animal implements Eatable {
    private final AnimalStat stat;
    private volatile Location location;
    private double satiety;
    private final AtomicBoolean alive = new AtomicBoolean(true);
    private final AnimalService service = AnimalService.getInstance();

    protected Animal() {
        this.stat = Settings.ANIMAL_STATS.get(this.getClass());
        this.satiety = stat.getFedUpWeight()/2;
    }

    public abstract boolean canEat(Eatable food);

    public void eat() {
        service.eat(this);
    }

    public void breed() {
        service.breed(this);
    }

    public void move() {
        service.move(this);
    }

    public void increaseSatiety(double amount) {
        satiety += amount;

        if (satiety > stat.getFedUpWeight()) {
            satiety = stat.getFedUpWeight();
        }
    }

    public void decreaseSatiety() {
        satiety *= 0.9;

        if (satiety < (0.2 * stat.getFedUpWeight())) die();
    }

    public double getSatiety() {
        return satiety;
    }

    @Override
    public void die() {
        if (!alive.compareAndSet(true, false)) return;
        service.die(this);
    }

    @Override
    public double getWeight() {
        return stat.getWeight();
    }

    public AnimalStat getStat() {
        return stat;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
