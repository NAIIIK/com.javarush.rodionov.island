package entity.animal.herbivore;

import entity.Eatable;

public class Boar extends Herbivore {
    @Override
    protected boolean canEat(Eatable food) {
        return super.canEat(food) ||
                food instanceof Mouse ||
                food instanceof Caterpillar;
    }
}
