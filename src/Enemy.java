public class Enemy {

    private String name;
    private int meleePower;
    private int rangedPower;
    private float rangedPowerLossPerRange;
    private float health;

    public Enemy(String name, int meleePower, int rangedPower, float rangedPowerLossPerRange, float health) {

        this.name = name;
        this.meleePower = meleePower;
        this.rangedPower = rangedPower;
        this.rangedPowerLossPerRange = rangedPowerLossPerRange;
        this.health = health;

    }

    boolean isDead() {

        return this.health <= 0;

    }

    public double getRangedPower() {
        return this.rangedPower;
    }

    public double getMeleePower() {
        return this.meleePower;
    }

    public String getName() {
        return this.name;
    }

    public float getRangedPowerLossPerRange() {
        return rangedPowerLossPerRange;
    }

    public void takeDamage(double damage) {

        this.health -= damage;

    }
}
