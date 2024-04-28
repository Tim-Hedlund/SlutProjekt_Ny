import Input.InputMaster;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.math.*;
import java.util.Random;

public class Game {
    private final InputMaster input = new InputMaster();
    private Team team;
    private ArrayList<Character> allCharacters;
    private ArrayList<Weapon> allWeapons;
    private ArrayList<Armor> allArmors;
    private ArrayList<Enemy> allEnemies;
    double distanceLeft = 200000;

    Game () {

        this.team = new Team();

        ObjectBuilder objectBuilder = new ObjectBuilder();

        allWeapons = objectBuilder.returnWeapons();
        allArmors = objectBuilder.returnArmors();
        allCharacters = objectBuilder.returnCharacters(allWeapons, allArmors);
        allEnemies = objectBuilder.returnEnemies();

        for (Weapon weapon : allWeapons) {
            System.out.println(weapon.getName());
        }
        for (Armor armor : allArmors) {
            System.out.println(armor.name);
        }
        for (Enemy enemy : allEnemies) {
            System.out.println(enemy.getName());
        }
        for (Character character : allCharacters) {
            System.out.println(character.getName());
            System.out.println(character.getWeapon().getName());
        }

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

        this.team = new Team();

        this.team.addCharacter(allCharacters.get(0));
        this.team.addCharacter(allCharacters.get(1));
        this.team.addCharacter(allCharacters.get(2));
        this.team.addCharacter(allCharacters.get(3));

        int choice = input.getInt();

        System.out.println(choice);

        nextGameEvent();

    }

    private void nextGameEvent() {

       reduceDistance();

       generateEvent();

    }

    private void generateEvent() {

        Fight fight = new Fight(this.allEnemies, this.team.getTeamMembers(), 5);
        this.team.setTeam(fight.getCharacters());

    }

    private void reduceDistance() {

        final double K = 0.00000000046053; //nummer uträknat för att få rätt distance för input
        double dist = 100000; //hur många meter man behöver flyttas för att nå 99% säkerhet för att ha nått ett event

        double random = Math.random();
        double distance = Math.log((-1)/(random - 1))/Math.log(1 + dist * K);
        for (int i = 0; i < 10; i++) {
            random = Math.random();
            distance = Math.log((-1)/(random - 1))/Math.log(1 + dist * K);

            System.out.println(distance);
        }

        this.distanceLeft -= distance;

    }


}
