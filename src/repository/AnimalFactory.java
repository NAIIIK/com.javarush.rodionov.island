package repository;

import config.Settings;
import entity.Animal;
import entity.Island;
import entity.Location;

public class AnimalFactory<T extends Animal> implements Factory<T> {
    private final Class<T> type;
    private final Island island = Island.getInstance();
    private static final int MAX_ATTEMPTS = Settings.ISLAND_LENGTH * Settings.ISLAND_WIDTH;

    public AnimalFactory(Class<T> type) {
        this.type = type;
    }

    @Override
    public T create() {
        try {
            T animal = type.getDeclaredConstructor().newInstance();

            for (int i = 0; i < MAX_ATTEMPTS; i++) {
                Location location = island.getRandomLocation();
                if (location.canAddAnimal(animal)) {
                    location.addAnimal(animal);
                    return animal;
                }
            }

            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
