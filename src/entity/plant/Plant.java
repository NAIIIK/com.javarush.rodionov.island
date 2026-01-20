package entity.plant;

import config.stat.PlantStat;
import config.Settings;
import entity.Eatable;
import entity.island.Location;
import service.PlantService;

public class Plant implements Eatable {
    private final PlantStat stat;
    private Location location;
    private final PlantService service = PlantService.getInstance();

    public Plant() {
        this.stat = Settings.PLANT_STATS;
    }

    @Override
    public void die() {
        service.rot(this);
    }

    @Override
    public double getWeight() {
        return stat.getWeight();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
