package app;

import entity.island.Island;

public class Application {
    public static void main(String[] args) {
        ApplicationRunner.run(Island.getInstance());
    }
}