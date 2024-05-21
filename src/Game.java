import Input.InputMaster;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.math.*;
import java.util.Random;
import java.util.Scanner;

//The "main" class of the game, handles almost everything about the current game state.
public class Game {
    private Team team;
    private ArrayList<Character> allCharacters;
    private ArrayList<Weapon> allWeapons;
    private ArrayList<Armor> allArmors;
    private ArrayList<Enemy> allEnemies;
    double distanceLeft = 200000;

    //the method that starts the game, loads all items, characters and enemies here to be used later
    public Game () {

        this.team = new Team();

        ObjectBuilder objectBuilder = new ObjectBuilder();

        allWeapons = objectBuilder.returnWeapons();
        allArmors = objectBuilder.returnArmors();
        allCharacters = objectBuilder.returnCharacters(allWeapons, allArmors);
        allEnemies = objectBuilder.returnEnemies();

        startGame();

    }

    //getByName is a generic method that takes in an arraylist of any type and checks each "name" field.
    //this is very useful in order to get something of a specific name from an arraylist because almost all of my data that is often accessed has a "name".
    //extra explanation in method as it is more complicated
    public static <T> T getByName(ArrayList<T> list, String name) {
        //loops through all items in the list.
        for (T obj : list) {
            try {
                //gets the class and sets name field to null
                Class<?> currentClass = obj.getClass();
                Field nameField = null;

                //this checks if there is a name field and if there isn't it checks its Superclass for a name field as some names are stored in the superclass,
                //this repeats until all superclasses are checked
                while (currentClass != null) {
                    try {
                        nameField = currentClass.getDeclaredField("name");
                        break;
                    } catch (NoSuchFieldException e) {
                        currentClass = currentClass.getSuperclass();
                    }
                }

                //checks if name field has changed from null, if it has this sets object name to the name-field of the input item.
                if (nameField != null) {
                    nameField.setAccessible(true);
                    String objName = (String) nameField.get(obj);

                    //if the name matches with the target name it returns the current item.
                    if (objName.equals(name)) {
                        return obj;
                    }
                } else {
                    //if not it prints this message.
                    System.out.println("This item or its parent classes do not have a \"name\" field");
                }
            } catch (IllegalAccessException e) {
                //if the name field is inaccessible for some reason
                System.out.println("Cannot access field \"name\" in this item");
            }
        }
        //if it loops through the whole list without finding an item with the input name
        System.out.println("There is no object with the name \"" + name + "\" in the input list");
        return null;
    }

    //Starts the real game, currently very bad because the game is not finished. It should not be this hardcoded.
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

    //reduces distance left until game is won and generates a new event.
    private void nextGameEvent() {

       reduceDistance();

       generateEvent();

    }

    //generates a new event, only fight because it is the only event
    private void generateEvent() {
        Random rnd = new Random();
        int mapSize = rnd.nextInt(4, 9);
        int enemyCount = rnd.nextInt(1, 7);

        ArrayList<Enemy> enemies = new ArrayList<>();

        //adds new enemies and prints their names, this code is to get the code working mostly and not a finished product.
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

        Fight fight = new Fight(enemies, this.team.getTeamMembers(), mapSize);
        this.team.setTeam(fight.getCharacters());

    }

    //this method is used to calculate the distance to an event
    private void reduceDistance() {

        final double K = 0.00000000046053; //number to get the correct distance for input
        double dist = 100000; //how many meters you have to travel to reach a 99% certainty in having reached an event

        double random = Math.random();
        double distance = Math.log((-1)/(random - 1))/Math.log(1 + dist * K);//equation to get distance, longer distances are rarer but as it is very close in rarity to get a "long" distance and a "very long" distance.

        System.out.print("After " + distance + " meters traveled, you encounter a ");

        this.distanceLeft -= distance;

    }

}
