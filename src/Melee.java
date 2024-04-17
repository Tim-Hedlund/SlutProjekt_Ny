public class Melee extends Weapon {

    float techniqueMultiplier;
    float strengthMultiplier;
    float hitChance;

    public Melee(String name, int damagePerShot, int shots, float critMultiplier, float critChance, float techniqueMultiplier, float strengthMultiplier, float hitChance) {
        super.name = name;
        super.damagePerShot = damagePerShot;
        super.shots = shots;
        super.critMultiplier = critMultiplier;
        super.critChance = critChance;
        this.techniqueMultiplier = techniqueMultiplier;
        this.strengthMultiplier = strengthMultiplier;
        this.hitChance = hitChance;
    }
}
