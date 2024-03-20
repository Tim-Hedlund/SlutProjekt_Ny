public class Character {
    String name;
    String description;
    Weapon weapon;
    Armor armor;
    int maxHealth;
    float currentHealth;
    int strength;
    int technique;
    int aim;
    int hunger;
    float currentHunger;

    Character (String name, String description, int maxHealth, int strength, int technique, int aim, int hunger, Weapon weapon, Armor armor) {

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


}
