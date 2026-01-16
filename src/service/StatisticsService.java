package service;

import entity.Animal;
import entity.Island;
import entity.Plant;

public class StatisticsService implements IslandAppService {
    private final Island island;

    public StatisticsService(Island island) {
        this.island = island;
    }

    @Override
    public void perform() {
        System.out.println("=== STATISTICS ===\nAnimals:");

        try {
            for (var entry : island.getAnimalsCount().entrySet()) {
                Animal animal = entry.getKey().getDeclaredConstructor().newInstance();
                System.out.printf("%s - %d  ", animal, entry.getValue());
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        Plant plant = new Plant();

        System.out.printf("\nPlants:\n%s - %d", plant, island.getPlantsCount());
        System.out.println("\n==================");
    }
}
