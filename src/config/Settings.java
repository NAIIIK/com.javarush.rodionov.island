package config;

import config.stat.AnimalStat;
import config.stat.PlantStat;
import entity.Animal;
import entity.Eatable;
import entity.Plant;
import entity.carnivore.*;
import entity.herbivore.*;

import java.util.Map;

public final class Settings {
    public static final Integer CORE_POOL_SIZE = 4;
    public static final Integer MAX_SIMULATION_DURATION = 10;

    public static final Integer ISLAND_WIDTH = 100;
    public static final Integer ISLAND_LENGTH = 20;

    public static final Map<Class<? extends Animal>, AnimalStat> ANIMAL_STATS =
            Map.ofEntries(
                    Map.entry(Wolf.class, new AnimalStat(50.0, 30, 3, 8.0, "\uD83D\uDC3A")),
                    Map.entry(BoaConstrictor.class, new AnimalStat(15.0, 30, 1, 3.0, "\uD83D\uDC0D")),
                    Map.entry(Fox.class, new AnimalStat(8.0, 30, 2, 2.0, "\uD83E\uDD8A")),
                    Map.entry(Bear.class, new AnimalStat(500.0, 5, 2, 80.0, "\uD83D\uDC3B")),
                    Map.entry(Eagle.class, new AnimalStat(6.0, 20, 3, 1.0, "\uD83E\uDD85")),
                    Map.entry(Horse.class, new AnimalStat(400.0, 20, 4, 60.0, "\uD83D\uDC0E")),
                    Map.entry(Deer.class, new AnimalStat(300.0, 20, 4, 50.0, "\uD83E\uDD8C")),
                    Map.entry(Rabbit.class, new AnimalStat(2.0, 150, 2, 0.45, "\uD83D\uDC07")),
                    Map.entry(Mouse.class, new AnimalStat(0.05, 500, 1, 0.01, "\uD83D\uDC01")),
                    Map.entry(Goat.class, new AnimalStat(60.0, 140, 3, 10.0, "\uD83D\uDC10")),
                    Map.entry(Sheep.class, new AnimalStat(70.0, 140, 3, 15.0, "\uD83D\uDC11")),
                    Map.entry(Boar.class, new AnimalStat(400.0, 50, 2, 50.0, "\uD83D\uDC17")),
                    Map.entry(Buffalo.class, new AnimalStat(700.0, 10, 3, 100.0, "\uD83D\uDC03")),
                    Map.entry(Duck.class, new AnimalStat(1.0, 200, 4, 0.15, "\uD83E\uDD86")),
                    Map.entry(Caterpillar.class, new AnimalStat(0.01, 1000, 0, 0, "\uD83D\uDC1B"))
            );

    public static final PlantStat PLANT_STATS = new PlantStat(1.0, 200);

    public static final Map<Class<? extends Animal>, Map<Class<? extends Eatable>, Integer>> EATING_CHANCE_TABLE =
            Map.ofEntries(
                    Map.entry(Wolf.class, Map.of(
                            Horse.class, 10,
                            Deer.class, 15,
                            Rabbit.class, 60,
                            Mouse.class, 80,
                            Goat.class, 60,
                            Sheep.class, 70,
                            Boar.class, 15,
                            Buffalo.class, 10,
                            Duck.class, 40
                    )),

                    Map.entry(BoaConstrictor.class, Map.of(
                            Fox.class, 15,
                            Rabbit.class, 20,
                            Mouse.class, 40,
                            Duck.class, 10
                    )),

                    Map.entry(Fox.class, Map.of(
                            Rabbit.class, 70,
                            Mouse.class, 90,
                            Duck.class, 60,
                            Caterpillar.class, 40
                    )),

                    Map.entry(Bear.class, Map.of(
                            BoaConstrictor.class, 80,
                            Horse.class, 40,
                            Deer.class, 80,
                            Rabbit.class, 80,
                            Mouse.class, 90,
                            Goat.class, 70,
                            Sheep.class, 70,
                            Boar.class, 50,
                            Buffalo.class, 20,
                            Duck.class, 10
                    )),

                    Map.entry(Eagle.class, Map.of(
                            Fox.class, 10,
                            Rabbit.class, 90,
                            Mouse.class, 90,
                            Duck.class, 80
                    )),

                    Map.entry(Horse.class, Map.of(
                            Plant.class, 100
                    )),

                    Map.entry(Deer.class, Map.of(
                            Plant.class, 100
                    )),

                    Map.entry(Rabbit.class, Map.of(
                            Plant.class, 100
                    )),

                    Map.entry(Mouse.class, Map.of(
                            Caterpillar.class, 90,
                            Plant.class, 100
                    )),

                    Map.entry(Goat.class, Map.of(
                            Plant.class, 100
                    )),

                    Map.entry(Sheep.class, Map.of(
                            Plant.class, 100
                    )),

                    Map.entry(Boar.class, Map.of(
                            Mouse.class, 50,
                            Caterpillar.class,90,
                            Plant.class, 100
                    )),

                    Map.entry(Buffalo.class, Map.of(
                            Plant.class, 100
                    )),

                    Map.entry(Duck.class, Map.of(
                            Caterpillar.class, 90,
                            Plant.class, 100
                    )),

                    Map.entry(Caterpillar.class, Map.of(
                            Plant.class, 100
                    ))
            );

    private Settings() {}
}
