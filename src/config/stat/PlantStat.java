package config.stat;

public final class PlantStat implements Stat {
    private final Double weight;
    private final Integer maxQuantityOnCell;

    public PlantStat(double weight, int maxQuantityOnCell) {
        this.weight = weight;
        this.maxQuantityOnCell = maxQuantityOnCell;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getMaxQuantityOnCell() {
        return maxQuantityOnCell;
    }
}
