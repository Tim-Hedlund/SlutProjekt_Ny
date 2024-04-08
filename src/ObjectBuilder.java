import java.io.File;
import java.util.ArrayList;
import Input.*;

public class ObjectBuilder {

    ObjectBuilder() {}

    ArrayList<Weapon> returnWeapons() {

        File weaponFile = new File("src/weapons.txt");
        FileMaster weaponFileMaster = new FileMaster(weaponFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Weapon> weapons = new ArrayList<>();
        ArrayList<String> currentWeaponData = new ArrayList<>();

        int paragraphCount = weaponFileMaster.getFileParagraphCount();

        for (int i = 0; i < paragraphCount; i++) {

            weaponFileMaster = weaponFileMaster.returnParagraphStringList(i);

            weapons.add(returnSingleWeapon(currentWeaponData, inputChecker));

        }

    }

    ArrayList<Character> returnCharacters(ArrayList<Weapon> allWeapons) {

        File charactersFile = new File("src/characters.txt");
        FileMaster characterFileMaster = new FileMaster(charactersFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<String> currentCharacterData;

        int paragraphCount = characterFileMaster.getFileParagraphCount();

        for (int i = 0; i < paragraphCount; i++) {

            currentCharacterData = characterFileMaster.returnParagraphStringList(i);

            characters.add(returnSingleCharacter(currentCharacterData, inputChecker, allWeapons));

        }

        return characters;

    }

    Character returnSingleCharacter(ArrayList<String> currentCharacterData, InputChecker inputChecker, ArrayList<Weapon> allWeapons) {

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

            Weapon weapon = allWeapons.getByName(weaponName);


            Character character = new Character(name, description, maxHealth, currentHealth, strength, technique, aim, weaponName, armorName);

        } else {

            System.out.println("Error,");
            System.out.println("Incorrect data length");

            return null;

        }

    }

    ArrayList<Weapon> returnWeapons() {



    }
}
