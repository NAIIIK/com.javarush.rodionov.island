package config.stat;

public final class AnimalStat implements Stat {
    private double weight;
    private int maxQuantityOnCell;
    private int maxMoveSpeed;
    private double fedUpWeight;
    private String emoji;

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
