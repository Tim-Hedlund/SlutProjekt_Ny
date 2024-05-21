//Weapon abstract class, there are no weapons as all weapons are either melee or ranged, has the data any weapon-type would need
public abstract class Weapon {
    private String name;
    private int damagePerShot;
    private int shots;
    private float critMultiplier;
    private float critChance;

    public void setName(String name) {
        this.name = name;
    }

    public void setDamagePerShot(int damagePerShot) {
        this.damagePerShot = damagePerShot;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public float getCritMultiplier() {
        return critMultiplier;
    }

    public void setCritMultiplier(float critMultiplier) {
        this.critMultiplier = critMultiplier;
    }

    public float getCritChance() {
        return critChance;
    }

    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }
    public String getName() {
        return this.name;
    }
    public int getDamagePerShot() {
        return this.damagePerShot;
    }

}