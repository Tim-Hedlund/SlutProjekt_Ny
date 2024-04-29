import Input.InputMaster;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.math.*;
import java.util.Random;
import java.util.Scanner;

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
        System.out.println("Press enter to continue:");

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();

        new ObjectBuilder();

        this.team = new Team();

        this.team.addCharacter(allCharacters.get(0));
        this.team.addCharacter(allCharacters.get(1));
        this.team.addCharacter(allCharacters.get(2));
        this.team.addCharacter(allCharacters.get(3));

        nextGameEvent();

    }

    private void nextGameEvent() {

       reduceDistance();

       generateEvent();

    }

    private void generateEvent() {
        Random rnd = new Random();
        int size = rnd.nextInt(4, 9);
        int enemyCount = rnd.nextInt(1, 7);

        ArrayList<Enemy> enemies = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            int enemyIndex = rnd.nextInt(0, this.allEnemies.size());
            enemies.add(new Enemy (allEnemies.get(enemyIndex)));
            System.out.print(allEnemies.get(enemyIndex).getName());
            System.out.print(", ");
        }
        int enemyIndex = rnd.nextInt(0, this.allEnemies.size());
        enemies.add(new Enemy (allEnemies.get(enemyIndex)));
        System.out.print("and a ");
        System.out.print(allEnemies.get(enemyIndex).getName());
        System.out.println("!");
        System.out.println();
        System.out.println("You still have " + distanceLeft + " meters left");

        Fight fight = new Fight(enemies, this.team.getTeamMembers(), size);
        this.team.setTeam(fight.getCharacters());

    }

    private void reduceDistance() {

        final double K = 0.00000000046053; //nummer uträknat för att få rätt distance för input
        double dist = 100000; //hur många meter man behöver flyttas för att nå 99% säkerhet för att ha nått ett event

        double random = Math.random();
        double distance = Math.log((-1)/(random - 1))/Math.log(1 + dist * K);

        System.out.print("After " + distance + " meters traveled, you encounter a ");

        this.distanceLeft -= distance;

    }

}
