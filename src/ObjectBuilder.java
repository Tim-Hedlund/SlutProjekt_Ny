import java.io.File;
import java.util.ArrayList;
import Input.*;

public class ObjectBuilder {

    ObjectBuilder() {}

    ArrayList<Character> returnCharacters() {

        File charactersFile = new File("src/characters.txt");
        FileMaster characterFileMaster = new FileMaster(charactersFile);

        InputChecker inputChecker = new InputChecker();

        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<String> currentCharacterData = new ArrayList<>();

        int paragraphCount = characterFileMaster.getFileParagraphCount();

        for (int i = 0; i < paragraphCount; i++) {

            currentCharacterData = characterFileMaster.returnParagraphStringList(i);

            if (currentCharacterData.size() == 9) {

                String name = currentCharacterData.get(0);
                String description = currentCharacterData.get(1);
                String weaponName = currentCharacterData.get(2);
                String armorName =  currentCharacterData.get(3);
                String maxHealthString = currentCharacterData.get(4);
                String currentHealthString = currentCharacterData.get(5);
                String strengthString =  currentCharacterData.get(6);
                String tecniqueString = currentCharacterData.get(7);
                String aimString = currentCharacterData.get(8);



                int maxHealth = inputChecker.

            } else {

                System.out.println("Error,");
                System.out.println("Incorrect data length");

                return null;

            }






            //Bob
            //Short and dumb but very strong, his eyes are too far apart and a tad small just like his brain.
            //Axe Bodyspray Dark Temptation
            //Null
            //200
            //200
            //7
            //2
            //2

        }

    }
}
