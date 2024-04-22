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

        /*
        for (Weapon weapon : allWeapons) {
            System.out.println(weapon.name);
        }
        for (Armor armor : allArmors) {
            System.out.println(armor.name);
        }
        for (Character character : allCharacters) {
            System.out.println(character.name);
            System.out.println(character.weapon.name);
        }
        */

        startGame();

    }

    public static <T> T getByName(ArrayList<T> list, String name) {
        for (T obj : list) {
            try {
                Class<?> currentClass = obj.getClass();
                Field nameField = null;

                while (currentClass != null) {
                    try {
                        nameField = currentClass.getDeclaredField("name");
                        break;
                    } catch (NoSuchFieldException e) {
                        currentClass = currentClass.getSuperclass();
                    }
                }

                if (nameField != null) {
                    nameField.setAccessible(true);
                    String objName = (String) nameField.get(obj);

                    if (objName.equals(name)) {
                        return obj;
                    }
                } else {
                    System.out.println("This item or its parent classes do not have a \"name\" field");
                }
            } catch (IllegalAccessException e) {
                System.out.println("Cannot access field \"name\" in this item");
            }
        }
        System.out.println("There is no object with the name \"" + name + "\" in the input list");
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
