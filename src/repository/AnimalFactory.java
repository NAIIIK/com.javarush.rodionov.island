package repository;

import entity.Animal;

public class AnimalFactory<T extends Animal> implements Factory<T> {
    private final Class<T> type;

    public AnimalFactory(Class<T> type) {
        this.type = type;
    }

    @Override
    public T create() {
        try {
            T animal = type.getDeclaredConstructor().newInstance();
            return animal;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
