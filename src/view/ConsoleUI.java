package view;

import entity.island.Island;

public class ConsoleUI implements View {
    private final Island island;

    public ConsoleUI(Island island) {
        this.island = island;
    }

    @Override
    public void print() {
        System.out.println("=== STATISTICS ===\nAnimals:");

        for (var entry : island.getAnimalsCountByType().entrySet()) {
            System.out.printf("%s - %d  ", entry.getKey().getSimpleName().toUpperCase(), entry.getValue());
        }

        System.out.println("\nPLANTS - " + island.getPlantsCount() + "\n==================");
    }
}
