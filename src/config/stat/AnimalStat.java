package config.stat;

public final class AnimalStat implements Stat {
    private final double weight;
    private final int maxQuantityOnCell;
    private final int maxMoveSpeed;
    private final double fedUpWeight;
    private final String emoji;

    public AnimalStat(double weight, int maxQuantityOnCell, int maxMoveSpeed, double fedUpWeight, String emoji) {
        this.weight = weight;
        this.maxQuantityOnCell = maxQuantityOnCell;
        this.maxMoveSpeed = maxMoveSpeed;
        this.fedUpWeight = fedUpWeight;
        this.emoji = emoji;
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

    public String getEmoji() {
        return emoji;
    }
}
