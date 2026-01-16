package entity.animal.herbivore;

import entity.Eatable;

public class Mouse extends Herbivore {
    @Override
    protected boolean canEat(Eatable food) {
        return super.canEat(food) ||
                food instanceof Caterpillar;
    }
}
