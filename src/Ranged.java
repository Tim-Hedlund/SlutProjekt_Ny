public class Ranged extends Weapon {
    float aimMultiplier;
    float accuracyLossPerRange;
    float damageLossPerRange;

    public Ranged(String name, int damagePerShot, int shots, float critMultiplier, float critChance, float aimMultiplier, float accuracyLossPerRange, float damageLossPerRange) {

        super.name = name;
        super.damagePerShot = damagePerShot;
        super.shots = shots;
        super.critMultiplier = critMultiplier;
        super.critChance = critChance;
        this.aimMultiplier = aimMultiplier;
        this.accuracyLossPerRange = accuracyLossPerRange;
        this.damageLossPerRange = damageLossPerRange;

    }
}
