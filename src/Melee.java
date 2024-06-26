//melee weapon type class, has all the data that is specific for melee weapons.
public class Melee extends Weapon {


    private final float techniqueMultiplier;
    private final float strengthMultiplier;
    private final float hitChance;

    public Melee(String name, int damagePerShot, int shots, float critMultiplier, float critChance, float techniqueMultiplier, float strengthMultiplier, float hitChance) {
        super.setName(name);
        super.setDamagePerShot(damagePerShot);
        super.setShots(shots);
        super.setCritMultiplier(critMultiplier);
        super.setCritChance(critChance);
        this.techniqueMultiplier = techniqueMultiplier;
        this.strengthMultiplier = strengthMultiplier;
        this.hitChance = hitChance;
    }

    public float getHitChance() {
        return hitChance;
    }
}
