package entity.herbivore;

import entity.Animal;
import entity.Eatable;
import entity.Plant;

public class Herbivore extends Animal {
    @Override
    protected boolean canEat(Eatable food) {
        return food instanceof Plant;
    }
}
