import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import Input.*;

public class ObjectBuilder {

    ObjectBuilder() {}

    ArrayList<Weapon> returnWeapons() {

        File weaponFile = new File("src/weaponRanged.txt");
        FileMaster weaponFileMaster = new FileMaster(weaponFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Weapon> weapons = new ArrayList<>();
        ArrayList<String> currentWeaponData = new ArrayList<>();

        int paragraphCount = weaponFileMaster.getFileParagraphCount();

        for (int i = 0; i < paragraphCount; i++) {

            currentWeaponData = weaponFileMaster.returnParagraphStringList(i);

            weapons.add(returnSingleWeapon(currentWeaponData, inputChecker));

        }

        return weapons;

    }

    ArrayList<Character> returnCharacters(ArrayList<Weapon> allWeapons, ArrayList<Armor> allArmors) {

        File charactersFile = new File("src/characters.txt");
        FileMaster characterFileMaster = new FileMaster(charactersFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<Armor> armors = new ArrayList<>();

        ArrayList<String> currentCharacterData;

        int paragraphCount = characterFileMaster.getFileParagraphCount();

        for (int i = 0; i < paragraphCount; i++) {

            currentCharacterData = characterFileMaster.returnParagraphStringList(i);

            characters.add(returnSingleCharacter(currentCharacterData, inputChecker, allWeapons, allArmors));

        }

        return characters;

    }

    private Weapon returnSingleWeapon(ArrayList<String> currentWeaponData, InputChecker inputChecker) {

        if (currentWeaponData.size() == 9) {

            String name = currentWeaponData.get(0);
            String damagePerShotString = currentWeaponData.get(1);
            String shotsString = currentWeaponData.get(2);
            String critMultiplierString = currentWeaponData.get(3);
            String critChanceString = currentWeaponData.get(4);

            int damagePerShot = inputChecker.toInt(damagePerShotString);
            int shots = inputChecker.toInt(shotsString);
            float critMultiplier = inputChecker.toFloat(critMultiplierString);
            float critChance = inputChecker.toFloat(critChanceString);

            return new Ranged(name, damagePerShot, shots, critMultiplier, critChance);


        }


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
