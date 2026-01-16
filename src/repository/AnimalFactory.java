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
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
