package config.stat;

public final class AnimalStat implements Stat {
    private final Double weight;
    private final Integer maxQuantityOnCell;
    private final Integer maxMoveSpeed;
    private final Double fedUpWeight;

    public AnimalStat(double weight, int maxQuantityOnCell, int maxMoveSpeed, double fedUpWeight) {
        this.weight = weight;
        this.maxQuantityOnCell = maxQuantityOnCell;
        this.maxMoveSpeed = maxMoveSpeed;
        this.fedUpWeight = fedUpWeight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getMaxQuantityOnCell() {
        return maxQuantityOnCell;
    }

    public int getMaxMoveSpeed() {
        return maxMoveSpeed;
    }

    public double getFedUpWeight() {
        return fedUpWeight;
    }
}
