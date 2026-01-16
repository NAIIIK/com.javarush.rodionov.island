package entity.animal.herbivore;

import entity.animal.Animal;
import entity.Eatable;
import entity.plant.Plant;

public class Herbivore extends Animal {
    @Override
    protected boolean canEat(Eatable food) {
        return food instanceof Plant;
    }
}
