package entity;

import config.stat.PlantStat;
import config.Settings;

public class Plant implements Eatable {
    private final PlantStat plantStat;
    private Location location;

    public Plant() {
        this.plantStat = Settings.PLANT_STATS;
    }

    @Override
    public void die() {
        location.removePlant(this);
    }

    @Override
    public double getWeight() {
        return plantStat.getWeight();
    }

    public PlantStat getPlantStat() {
        return plantStat;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "\uD83C\uDF31";
    }
}
