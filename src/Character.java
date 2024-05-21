//character class, each team member is a character
public class Character {
    private String name;
    private String description;
    private Weapon weapon;
    private Armor armor;
    private int maxHealth;
    private double currentHealth;
    private int strength;
    private int technique;
    private int aim;
    private int hunger;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private float currentHunger;

    //Default constructor
    public Character (String name, String description, int maxHealth, int strength, int technique, int aim, int hunger, Weapon weapon, Armor armor) {

        this.name = name;
        this.description = description;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.technique = technique;
        this.aim = aim;
        this.hunger = hunger;

        this.weapon = weapon;
        this.armor = armor;

        this.currentHealth = this.maxHealth;
        this.currentHunger = this.hunger;

    }

    //takes damage without checking for armor
    public void takeTrueDamage(double damage) {
        currentHealth-=damage;
    }

    //takes damage with check for armor
    public void takeBlockableDamage(double damage, boolean isCrit) {

        Armor armor = this.armor;

        damage -= armor.flatDamageReduction;

        double randomNumber = Math.random();

        if (randomNumber < armor.reductionChance) {

            if (isCrit) {
                damage = ((1-armor.critReductionChance) * damage);
            } else {
                damage = ((1-armor.percentDamageReduction) * damage);
            }

        }

        this.currentHealth -= damage;

        System.out.println(damage + " damage!");

    }

    public double getCurrentHealth() {
        return this.currentHealth;
    }


    public Weapon getWeapon() {
        return this.weapon;
    }

    //returns ranged weapon accuracy for a distance, shorter distance and higher aim means higher hit chance
    public double getAccuracy(int distance) {

        if (!(this.weapon instanceof Ranged)) {
            return -1;
        }

        double accuracyLossPerRange = ((Ranged)this.weapon).getAccuracyLossPerRange();
        double aimMultiplier = ((Ranged)this.weapon).getAimMultiplier();

        double trueAccuracy = ((accuracyLossPerRange * distance));
        double trueAim = ((this.aim * aimMultiplier) + 1);

        return (1 - (trueAccuracy/trueAim));

    }

    //attacks with melee weapon
    public double attackMelee(String targetName) {

        if (!(this.weapon instanceof Melee)) {
            return -1;
        }

        double hitChance = ((Melee)this.weapon).getHitChance();

        for (int i = 0; i < this.weapon.getShots(); i++) {

            if (checkRandom(hitChance)) {

                return dealMeleeDamage(targetName);

            }

        }

        return -1;

    }

    //Deals one melee attack of damage, used multiple times for multiple hits.
    private double dealMeleeDamage(String targetName) {

        final int TARGET_SKILL_NORMAL = 4;

        double damage = getWeapon().getDamagePerShot();

        double defaultTechniqueMultiplier = (2 * (1 - (TARGET_SKILL_NORMAL/((double)this.technique+TARGET_SKILL_NORMAL))));
        double defaultStrengthMultiplier = (2* (1 - (TARGET_SKILL_NORMAL/((double)this.strength+TARGET_SKILL_NORMAL))));

        double defaultMultiplier = Math.max(defaultTechniqueMultiplier, defaultStrengthMultiplier);

        damage = damage * defaultMultiplier;

        if (checkRandom(this.weapon.getCritChance())) {
            damage *= this.weapon.getCritMultiplier();
        }

        System.out.println(this.name + " Hits " + targetName + " with " + this.weapon.getName() + " for " + damage);
        return damage;

    }

    //random number generator for chance
    private boolean checkRandom(double randomChance) {

        double randomNumber = Math.random();

        return randomNumber < randomChance;

    }

    //attacks with ranged weapon.
    public double attackRanged(double accuracy, String targetName) {
        if (!(this.weapon instanceof Ranged)) {
            return -1;
        }

        for (int i = 0; i < this.weapon.getShots(); i++) {

            if (checkRandom(accuracy)) {

                return dealRangedDamage(targetName);

            } else {
                System.out.println(this.name + " Misses the shot on " + targetName);
            }

        }

        return -1;

    }

    //Deals one ranged attack of damage, used multiple times for multiple shots.
    private double dealRangedDamage(String targetName) {

        double damage = getWeapon().getDamagePerShot();

        if (checkRandom(weapon.getCritChance())) {
            damage *= getWeapon().getCritMultiplier();
        }

        System.out.println(this.name + " shoots " + targetName + " with " + this.weapon.getName() + " for " + damage);
        return damage;

    }
}
