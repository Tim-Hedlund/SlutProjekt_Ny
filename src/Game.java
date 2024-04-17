import Input.InputMaster;
import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Game {
    InputMaster input = new InputMaster();
    Team team;
    ArrayList<Character> allCharacters;
    ArrayList<Weapon> allWeapons;
    ArrayList<Armor> allArmors;

    Game () {

        this.team = new Team();

        ObjectBuilder objectBuilder = new ObjectBuilder();

        allWeapons = objectBuilder.returnWeapons();
        allArmors = objectBuilder.returnArmors();
        allCharacters = objectBuilder.returnCharacters(allWeapons, allArmors);

        startGame();

    }

    public static <T> T getByName(ArrayList<T> list, String name) {
        for (T obj : list) {
            try {
                Field nameField = obj.getClass().getField("name");
                nameField.setAccessible(true);

                String objName = (String) nameField.get(obj);

                if (objName.equals(name)) {
                    return obj;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("There is no item in input list with " + name + " as name");
            }
        }
        return null;
    }

    void startGame() {




        System.out.println("Welcome to \"Escape the motherland\"");
        System.out.println("Chose to create a character or use a default one");
        System.out.println("write 1 for custom or 2 for a default character");

        new ObjectBuilder();

        int choice = input.getInt();

        System.out.println(choice);

    }





}
