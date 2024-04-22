import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import Input.*;

public class ObjectBuilder {

    final int PARAGRAPH_SKIP = 1;



    ObjectBuilder() {}

    ArrayList<Weapon> returnWeapons() {

        File rangedWeaponFile = new File("src/objectFileWeaponRanged.txt");
        FileMaster rangedWeaponFileMaster = new FileMaster(rangedWeaponFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Weapon> weapons = new ArrayList<>();
        ArrayList<String> currentWeaponData;

        int paragraphCount = rangedWeaponFileMaster.getFileParagraphCount();

        for (int i = PARAGRAPH_SKIP; i < paragraphCount; i++) {

            currentWeaponData = rangedWeaponFileMaster.returnParagraphStringList(i);

            weapons.add(returnSingleWeapon(currentWeaponData, inputChecker, true)); //ranged weapons

        }

        File meleeWeaponFile = new File("src/objectFileWeaponMelee.txt");
        FileMaster meleeWeaponFileMaster = new FileMaster(meleeWeaponFile);

        paragraphCount = meleeWeaponFileMaster.getFileParagraphCount();

        for (int i = PARAGRAPH_SKIP; i < paragraphCount; i++) {

            currentWeaponData = meleeWeaponFileMaster.returnParagraphStringList(i);

            weapons.add(returnSingleWeapon(currentWeaponData, inputChecker, false)); //melee weapons

        }

        return weapons;

    }

    ArrayList<Armor> returnArmors() {

        File armorFile = new File("src/objectFileArmor.txt");
        FileMaster armorFileMaster = new FileMaster(armorFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Armor> armors = new ArrayList<>();

        ArrayList<String> currentArmorData;

        int paragraphCount = armorFileMaster.getFileParagraphCount();

        for (int i = PARAGRAPH_SKIP; i < paragraphCount; i++) {

            currentArmorData = armorFileMaster.returnParagraphStringList(i);

            armors.add(returnSingleArmor(currentArmorData, inputChecker));

        }

        return armors;

    }

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

    ArrayList<Character> returnCharacters(ArrayList<Weapon> allWeapons, ArrayList<Armor> allArmors) {

        File charactersFile = new File("src/objectFileCharacter.txt");
        FileMaster characterFileMaster = new FileMaster(charactersFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Character> characters = new ArrayList<>();

        ArrayList<String> currentCharacterData;

        int paragraphCount = characterFileMaster.getFileParagraphCount();

        for (int i = PARAGRAPH_SKIP; i < paragraphCount; i++) {

            currentCharacterData = characterFileMaster.returnParagraphStringList(i);

            characters.add(returnSingleCharacter(currentCharacterData, inputChecker, allWeapons, allArmors));

        }

        return characters;

    }

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

    private Weapon returnSingleRangedWeapon(String name, int damagePerShot, int shots, float critMultiplier, float critChance, ArrayList<String> currentWeaponData, InputChecker inputChecker) {

        String aimMultiplierString = currentWeaponData.get(5);
        String accuracyLossPerRangeString = currentWeaponData.get(6);
        String damageLossPerRangeString = currentWeaponData.get(7);

        float aimMultiplier = inputChecker.toFloat(aimMultiplierString);
        float accuracyLossPerRange = inputChecker.toFloat(accuracyLossPerRangeString);
        float damageLossPerRange = inputChecker.toFloat(damageLossPerRangeString);

        return new Ranged(name, damagePerShot, shots, critMultiplier, critChance, aimMultiplier, accuracyLossPerRange, damageLossPerRange);

    }

    private Weapon returnSingleMeleeWeapon(String name, int damagePerShot, int shots, float critMultiplier, float critChance, ArrayList<String> currentWeaponData, InputChecker inputChecker) {

        String techniqueMultiplierString = currentWeaponData.get(5);
        String strengthMultiplierString = currentWeaponData.get(6);
        String hitChanceString = currentWeaponData.get(7);

        float techniqueMultiplier = inputChecker.toFloat(techniqueMultiplierString);
        float strengthMultiplier = inputChecker.toFloat(strengthMultiplierString);
        float hitChance = inputChecker.toFloat(hitChanceString);

        return new Melee(name, damagePerShot, shots, critMultiplier, critChance, techniqueMultiplier, strengthMultiplier, hitChance);

    }

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
