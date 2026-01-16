package entity.animal.carnivore;

import entity.animal.Animal;
import entity.Eatable;

public class Carnivore extends Animal {
    @Override
    protected boolean canEat(Eatable food) {
        if (food instanceof Animal prey) {
            return prey.getClass() != this.getClass();
        }
        return false;
    }
}