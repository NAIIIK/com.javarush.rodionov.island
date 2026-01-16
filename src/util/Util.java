package util;

import config.Settings;
import entity.Animal;
import entity.Eatable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class Util {

    private Util() {}

    public static int getEatingChance(Animal animal, Eatable food) {
        Map<Class<? extends Animal>, Map<Class<? extends Eatable>, Integer>> eatingChanceTable =
                Settings.EATING_CHANCE_TABLE;

        return eatingChanceTable.getOrDefault(animal.getClass(), Map.of())
                .getOrDefault(food.getClass(), 0);
    }

    public static int getRandomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static void performRandomActivity(Animal animal) {
        List<Runnable> activities = List.of(
                animal::tryMove,
                animal::tryEat,
                animal::breed
        );

        activities.get(Util.getRandomInt(activities.size())).run();
    }
}