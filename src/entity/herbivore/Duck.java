package entity.herbivore;

import entity.Eatable;

public class Duck extends Herbivore {

    @Override
    protected boolean canEat(Eatable food) {
        return super.canEat(food) ||
                food instanceof Caterpillar;
    }
}
