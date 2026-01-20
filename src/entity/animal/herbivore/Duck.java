package entity.animal.herbivore;

import entity.Eatable;

public class Duck extends Herbivore {

    @Override
    public boolean canEat(Eatable food) {
        return super.canEat(food) ||
                food instanceof Caterpillar;
    }
}
