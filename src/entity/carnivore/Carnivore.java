package entity.carnivore;

import entity.Animal;
import entity.Eatable;

public class Carnivore extends Animal {
    @Override
    protected boolean canEat(Eatable food) {
        return food instanceof Animal;
    }
}