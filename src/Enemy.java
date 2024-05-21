//class used for each enemy. Work similar to characters but only for fights and have set values instead of equipped items.
public class Enemy {

    private final String name;
    private final int meleePower;
    private final int rangedPower;
    private final float rangedPowerLossPerRange;
    private double health;

    //default constructor
    public Enemy(String name, int meleePower, int rangedPower, float rangedPowerLossPerRange, float health) {

        this.name = name;
        this.meleePower = meleePower;
        this.rangedPower = rangedPower;
        this.rangedPowerLossPerRange = rangedPowerLossPerRange;
        this.health = health;

    }

    //used to copy Enemy so that there is only one of a single enemy in a fight
    public Enemy(Enemy otherEnemy) {
        this.name = otherEnemy.name;
        this.meleePower = otherEnemy.meleePower;
        this.rangedPower = otherEnemy.rangedPower;
        this.rangedPowerLossPerRange = otherEnemy.rangedPowerLossPerRange;
        this.health = otherEnemy.health;
    }

    //checks if the enemy has negative or zero health
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

    public double getHealth() {
        return this.health;
    }
}
