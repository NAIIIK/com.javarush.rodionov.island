package repository;

import entity.Plant;

public class PlantFactory implements Factory<Plant> {
    @Override
    public Plant create() {
        return new Plant();
    }
}
