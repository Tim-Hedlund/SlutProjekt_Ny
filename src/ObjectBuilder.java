import java.io.File;
import java.util.ArrayList;
import java.util.function.Function;

import Input.*;

//The object builder class is used to create objects from data stored in files by using the file-master class.

public class ObjectBuilder {

    final static int PARAGRAPH_SKIP = 1;

    public ObjectBuilder() {}

    //generic method for loading data, converts each paragraph using the provided parse function.
    public <T> ArrayList<T> loadData(File file, Function<ArrayList<String>, T> parseFunction) {

        FileMaster fileMaster = new FileMaster(file);
        ArrayList<T> data = new ArrayList<>();
        ArrayList<String> currentData;

        int paragraphCount = fileMaster.getFileParagraphCount();
        for (int i = PARAGRAPH_SKIP; i < paragraphCount; i++) {
            currentData = fileMaster.returnParagraphStringList(i);
            data.add(parseFunction.apply(currentData));
        }
        return data;
    }

    //returns all enemies from the enemy file using the loadData method.
    ArrayList<Enemy> returnEnemies() {
        File enemyFile = new File("src/objectFileEnemy.txt");
        InputChecker inputChecker = new InputChecker();
        return loadData(enemyFile, data -> returnSingleEnemy(data, inputChecker));
    }

    //returns all armors from the armor file using the loadData method.
    ArrayList<Armor> returnArmors() {
        File armorFile = new File("src/objectFileArmor.txt");
        InputChecker inputChecker = new InputChecker();
        return loadData(armorFile, data -> returnSingleArmor(data, inputChecker));
    }

    //returns all characters from the character file using the loadData method.
    ArrayList<Character> returnCharacters(ArrayList<Weapon> allWeapons, ArrayList<Armor> allArmors) {
        File armorFile = new File("src/objectFileCharacter.txt");
        InputChecker inputChecker = new InputChecker();
        return loadData(armorFile, data -> returnSingleCharacter(data, inputChecker, allWeapons, allArmors));
    }

    //returns all weapons from the weapon file using the loadData method, adds all weapon types to the same return list.
    ArrayList<Weapon> returnWeapons() {

        File weaponFileMelee = new File("src/objectFileWeaponMelee.txt");
        InputChecker inputChecker = new InputChecker();
        ArrayList<Weapon> returnWeapons = loadData(weaponFileMelee, data -> returnSingleWeapon(data, inputChecker, false));

        File weaponFileRanged = new File("src/objectFileWeaponRanged.txt");
        returnWeapons.addAll(loadData(weaponFileRanged, data -> returnSingleWeapon(data, inputChecker, true)));

        return returnWeapons;

    }

    //returns a single enemy from the String ArrayList
    private Enemy returnSingleEnemy(ArrayList<String> currentEnemyData, InputChecker inputChecker) {

        if (currentEnemyData.size() == 5) {

            String name = currentEnemyData.get(0);
            String meleePowerString = currentEnemyData.get(1);
            String rangedPowerString = currentEnemyData.get(2);
            String rangedPowerLossPerRangeString = currentEnemyData.get(3);
            String healthString = currentEnemyData.get(4);

            int meleePower = inputChecker.toInt(meleePowerString);
            int rangedPower = inputChecker.toInt(rangedPowerString);
            float rangedPowerLossPerRange = inputChecker.toFloat(rangedPowerLossPerRangeString);
            float health = inputChecker.toFloat(healthString);

            return new Enemy(name, meleePower, rangedPower, rangedPowerLossPerRange, health);

        } else {

            System.out.println("Error,");
            System.out.println("Incorrect data length");

            return null;
        }

    }

    //returns a single armor from the String ArrayList
    private Armor returnSingleArmor(ArrayList<String> currentArmorData, InputChecker inputChecker) {

        if (currentArmorData.size() == 5) {

            String name = currentArmorData.get(0);
            String flatDamageReductionString = currentArmorData.get(1);
            String percentDamageReductionString = currentArmorData.get(2);
            String reductionChanceString = currentArmorData.get(3);
            String critReductoinChanceString = currentArmorData.get(4);

            int flatDamageReduction = inputChecker.toInt(flatDamageReductionString);
            float percentDamageReduction = inputChecker.toFloat(percentDamageReductionString);
            float reductionChance = inputChecker.toFloat(reductionChanceString);
            float critReductionChance = inputChecker.toFloat(critReductoinChanceString);

            return new Armor(name, flatDamageReduction, percentDamageReduction, reductionChance, critReductionChance);

        } else {

        System.out.println("Error,");
        System.out.println("Incorrect data length");

        return null;
        }

    }

    //returns a single weapons data from the String ArrayList, if is ranged it uses the ranged method otherwise it uses the melee method.
    private Weapon returnSingleWeapon(ArrayList<String> currentWeaponData, InputChecker inputChecker, boolean isRanged) {

        if (currentWeaponData.size() == 8) {

            String name = currentWeaponData.get(0);
            String damagePerShotString = currentWeaponData.get(1);
            String shotsString = currentWeaponData.get(2);
            String critMultiplierString = currentWeaponData.get(3);
            String critChanceString = currentWeaponData.get(4);

            int damagePerShot = inputChecker.toInt(damagePerShotString);
            int shots = inputChecker.toInt(shotsString);
            float critMultiplier = inputChecker.toFloat(critMultiplierString);
            float critChance = inputChecker.toFloat(critChanceString);

            if (isRanged) {

                return returnSingleRangedWeapon(name, damagePerShot, shots, critMultiplier, critChance, currentWeaponData, inputChecker);

            } else {

                return returnSingleMeleeWeapon(name, damagePerShot, shots, critMultiplier, critChance, currentWeaponData, inputChecker);

            }

        } else {

            System.out.println("Error,");
            System.out.println("Incorrect data length");

            return null;

        }


    }

    //returns a single rangedWeapon from the data provided by the returnSingleWeapon method and the String Arraylist
    private Weapon returnSingleRangedWeapon(String name, int damagePerShot, int shots, float critMultiplier, float critChance, ArrayList<String> currentWeaponData, InputChecker inputChecker) {

        String aimMultiplierString = currentWeaponData.get(5);
        String accuracyLossPerRangeString = currentWeaponData.get(6);
        String damageLossPerRangeString = currentWeaponData.get(7);

        float aimMultiplier = inputChecker.toFloat(aimMultiplierString);
        float accuracyLossPerRange = inputChecker.toFloat(accuracyLossPerRangeString);
        float damageLossPerRange = inputChecker.toFloat(damageLossPerRangeString);

        return new Ranged(name, damagePerShot, shots, critMultiplier, critChance, aimMultiplier, accuracyLossPerRange, damageLossPerRange);

    }

    //returns a single melee from the data provided by the returnSingleWeapon method and the String Arraylist
    private Weapon returnSingleMeleeWeapon(String name, int damagePerShot, int shots, float critMultiplier, float critChance, ArrayList<String> currentWeaponData, InputChecker inputChecker) {

        String techniqueMultiplierString = currentWeaponData.get(5);
        String strengthMultiplierString = currentWeaponData.get(6);
        String hitChanceString = currentWeaponData.get(7);

        float techniqueMultiplier = inputChecker.toFloat(techniqueMultiplierString);
        float strengthMultiplier = inputChecker.toFloat(strengthMultiplierString);
        float hitChance = inputChecker.toFloat(hitChanceString);

        return new Melee(name, damagePerShot, shots, critMultiplier, critChance, techniqueMultiplier, strengthMultiplier, hitChance);

    }

    //returns a single character from the String ArrayList
    Character returnSingleCharacter(ArrayList<String> currentCharacterData, InputChecker inputChecker, ArrayList<Weapon> allWeapons, ArrayList<Armor> allArmors) {

        if (currentCharacterData.size() == 9) {

            String name = currentCharacterData.get(0);
            String description = currentCharacterData.get(1);
            String weaponName = currentCharacterData.get(2);
            String armorName =  currentCharacterData.get(3);
            String maxHealthString = currentCharacterData.get(4);
            String currentHealthString = currentCharacterData.get(5);
            String strengthString =  currentCharacterData.get(6);
            String techniqueString = currentCharacterData.get(7);
            String aimString = currentCharacterData.get(8);

            int maxHealth = inputChecker.toInt(maxHealthString);
            int currentHealth = inputChecker.toInt(currentHealthString);
            int strength = inputChecker.toInt(strengthString);
            int technique = inputChecker.toInt(techniqueString);
            int aim = inputChecker.toInt(aimString);

            Weapon weapon = Game.getByName(allWeapons, weaponName);
            Armor armor = Game.getByName(allArmors, armorName);

            return new Character(name, description, maxHealth, currentHealth, strength, technique, aim, weapon, armor);

        } else {

            System.out.println("Error,");
            System.out.println("Incorrect data length");

            return null;

        }

    }

}
