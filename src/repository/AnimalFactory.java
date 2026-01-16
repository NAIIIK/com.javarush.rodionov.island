package repository;

import config.Settings;
import entity.animal.Animal;
import exception.AnimalCreationException;

public class AnimalFactory<T extends Animal> implements Factory<T> {
    private final Class<T> type;

    public AnimalFactory(Class<T> type) {
        this.type = type;
    }

    @Override
    public T create() {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new AnimalCreationException(Settings.ANIMAL_CREATION_EXCEPTION_MESSAGE, e);
        }
    }
}
