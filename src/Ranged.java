//ranged weapon type class, has all the data that is specific for ranged weapons.
public class Ranged extends Weapon {
    private float aimMultiplier;
    private float accuracyLossPerRange;
    private float damageLossPerRange;

    public float getAimMultiplier() {
        return aimMultiplier;
    }

    public float getAccuracyLossPerRange() {
        return accuracyLossPerRange;
    }

    public float getDamageLossPerRange() {
        return damageLossPerRange;
    }

    public Ranged(String name, int damagePerShot, int shots, float critMultiplier, float critChance, float aimMultiplier, float accuracyLossPerRange, float damageLossPerRange) {

        super.setName(name);
        super.setDamagePerShot(damagePerShot);
        super.setShots(shots);
        super.setCritChance(critChance);
        super.setCritMultiplier(critMultiplier);
        this.aimMultiplier = aimMultiplier;
        this.accuracyLossPerRange = accuracyLossPerRange;
        this.damageLossPerRange = damageLossPerRange;

    }
}
