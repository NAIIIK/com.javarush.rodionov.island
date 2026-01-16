package entity;

import config.stat.PlantStat;
import config.Settings;

public class Plant implements Eatable {
    private final PlantStat plantStat;
    private Location location;
    private boolean alive;

    public Plant() {
        this.plantStat = Settings.PLANT_STATS;
        this.alive = true;
    }

    @Override
    public void die() {
        alive = false;
        location.removePlant(this);
    }

    @Override
    public double getWeight() {
        return plantStat.getWeight();
    }

    public PlantStat getPlantStat() {
        return plantStat;
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

    @Override
    public String toString() {
        return "\uD83C\uDF31";
    }
}
