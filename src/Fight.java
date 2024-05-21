import java.util.ArrayList;
import java.util.Scanner;

//fight class used for each fight the team encounter. A fight continues until one side is dead. Supposed to be part of the event system and attack class.
public class Fight {

    private ArrayList<Fighter<Enemy>> enemies = new ArrayList<>();
    private ArrayList<Fighter<Character>> characters = new ArrayList<>();
    private final int size;
    private final double TARGET_ACCURACY = 0.5;

    //prepares the fight, prepares the arraylists for characters and enemies and turns them into the
    //generic Fighter class in order to store position data
    public Fight (ArrayList<Enemy> enemies, ArrayList<Character> characters, int size) {

        this.size = size;

        for (Enemy enemy : enemies) {
            this.enemies.add(new Fighter<>(enemy, size));
        }

        for (Character character : characters) {
            this.characters.add(new Fighter<>(character, 0));
        }

        startFight();

    }

    //the "main method" of each fight, checks the status variable for if anybody has won
    public void startFight() {

        Scanner scan = new Scanner(System.in);

        while (true) {

            scan.nextLine();

            boolean teamIsAlive = MakeCharacterMoves(); //returns true as long as team is alive should continue

            int status = makeEnemyMoves(teamIsAlive);

            if (status == 0) {

                System.out.println("enemies have won!");
                break;

            } else if (status == 1) {

                System.out.println("The team has won!");
                break;

            } else if (status == 2) {

                System.out.println("The fight continues!");

            }

        }

        System.out.println("The fight is over...");

    }

    //Makes one move for each enemy if the team is alive and in some cases returns if the fight is over as well
    private int makeEnemyMoves(boolean teamIsAlive) {

        if (!teamIsAlive) {
            return 0;
        }

        int totalEnemyMoves = 0;
        System.out.println();
        System.out.println("Its the enemies turn!");
        System.out.println(":");

        ArrayList<Fighter<Enemy>> deadEnemies = new ArrayList<>();
        for (Fighter<Enemy> enemy : enemies) {
            System.out.println(enemy.obj.getHealth());

            if (enemy.obj.isDead()) {
                deadEnemies.add(enemy);
                System.out.println(enemy.obj.getName() + " is dead!");
            } else {
                totalEnemyMoves ++;
                makeEnemyMove(enemy);
            }

        }

        for (Fighter<Enemy> deadEnemy : deadEnemies) {
            this.enemies.remove(deadEnemy);
        }

        if (totalEnemyMoves == 0) {
            return 1;
        }

        return 2;

    }

    //returns the character object from the Fighter<Character> arraylist
    public ArrayList<Character> getCharacters() {

        ArrayList<Character> returnCharacters = new ArrayList<>();

        for (Fighter<Character> character : characters) {

            returnCharacters.add(character.obj);

        }

        return returnCharacters;

    }

    //makes all the moves for characters
    private boolean MakeCharacterMoves() {

        ArrayList<Fighter<Character>> characterRemoveArray = new ArrayList<>();
        for (Fighter<Character> character : this.characters) {

            if(character.obj.getCurrentHealth() <= 0) {
                System.out.println(character.obj.getName() + " is dead");
                characterRemoveArray.add(character);
            }

        }

        for (Fighter<Character> character : characterRemoveArray) {

            if(character.obj.getCurrentHealth() <= 0) {
                this.characters.remove(character);
            }

        }

        if (this.characters.size() == 0) {
            System.out.println("Enemies have killed your whole team");
            return false;
        }

        System.out.println();
        System.out.println("Its your teams turn!");
        System.out.println(":");

        for (Fighter<Character> character : this.characters) {
            makeCharacterMove(character);
        }

        return true;

    }

    //The enemy makes their move if they aren't dead
    private void makeEnemyMove(Fighter<Enemy> enemy) {

        if (enemy.obj.isDead()) {
            return;
        }

        choseEnemyMove(enemy);

    }

    //The enemy choses their move based on their
    private void choseEnemyMove(Fighter<Enemy> checker) {

        Fighter<Character> targetCharacter = this.characters.get(getShortestCharacterDistanceIndex(checker));

        int distance = Math.abs(checker.position - targetCharacter.position);

        boolean isMelee = checker.obj.getRangedPower() == 0;

        double accuracy = getAccuracy(checker, distance);

        if (distance > 0 && isMelee) { //if you can only Melee and are far away, walk closer
            moveEnemyForward(checker);

        } else if (distance == 0) { //if you are in enemies face, Melee
            attackMeleeEnemy(checker, targetCharacter);

        } else if (accuracy < TARGET_ACCURACY) { //if you are too far away to shoot accurately, walk closer
            moveEnemyForward(checker);

        } else { //if you are close enough to shoot accurately, shoot Enemy
            attackRangedEnemy(checker, targetCharacter);

        }

    }

    //Enemy attacks from range, only if they have ranged power
    private void attackRangedEnemy(Fighter<Enemy> checker, Fighter<Character> target) {

        final double RANGED_MULTIPLIER = 6;
        final double RANGED_CRIT_CHANCE = 0.25;
        final double RANGED_CRIT_MULTIPLIER = 1.5;

        double damage = RANGED_MULTIPLIER * checker.obj.getRangedPower();

        int distance = Math.abs(checker.position - target.position);
        double damagePercent = getAccuracy(checker, distance);

        boolean hasCrit = checkCritChance(RANGED_CRIT_CHANCE);

        if (hasCrit) {
            damage = damage*RANGED_CRIT_MULTIPLIER;
        }

        damage = damage*damagePercent;


        System.out.print(checker.obj.getName() + " Shoots " + target.obj.getName() + " for ");
        target.obj.takeBlockableDamage(damage, hasCrit);



    }

    //Checks for crit
    private boolean checkCritChance(double critChance) {

        double randomNumber = Math.random();

        return randomNumber < critChance;

    }

    //Enemy attacks melee if they are close enough
    private void attackMeleeEnemy(Fighter<Enemy> checker, Fighter<Character> target) {

        final double MELEE_MULTIPLIER = 5;
        final double MELEE_CRIT_CHANCE = 0.3;
        final double MELEE_CRIT_MULTIPLIER = 1.5;

        double damage = MELEE_MULTIPLIER * checker.obj.getMeleePower();

        boolean hasCrit = checkCritChance(MELEE_CRIT_CHANCE);

        if (hasCrit) {
            damage = damage*MELEE_CRIT_MULTIPLIER;
        }

        System.out.print(checker.obj.getName() + " Hits " + target.obj.getName() + " for ");
        target.obj.takeBlockableDamage(damage, hasCrit);



    }

    //Enemy gets their ranged accuracy
    private double getAccuracy(Fighter<Enemy> checker, int distance) {

        return Math.pow(1-checker.obj.getRangedPowerLossPerRange(), distance);


    }

    //Enemy moves forward
    private void moveEnemyForward(Fighter<Enemy> checker) {

        checker.position --;
        System.out.println(checker.obj.getName() + " Moves forward");

    }

    //Gets the shortest distance from inputted enemy to character
    private int getShortestCharacterDistanceIndex(Fighter<Enemy> checker) {

        int pos = checker.position;

        ArrayList<Integer> oppositionDistance = new ArrayList<>();

        ArrayList<Fighter<Character>> oppositionPositions = new ArrayList<>(characters);

        for (Fighter<Character> character: oppositionPositions) {

            oppositionDistance.add(Math.abs(character.position - pos));

        }

        return returnSmallest(oppositionDistance);


    }

    //Gets the shortest distance from inputted character to enemy
    private int getShortestEnemyDistanceIndex(Fighter<Character> checker) {

        int pos = checker.position;

        ArrayList<Integer> oppositionDistance = new ArrayList<>();

        ArrayList<Fighter<Enemy>> oppositionPositions = new ArrayList<>(enemies);

        for (Fighter<Enemy> enemy: oppositionPositions) {

            oppositionDistance.add(Math.abs(enemy.position - pos));

        }

        return returnSmallest(oppositionDistance);

    }

    //Returns the smallest integer in an arraylist. Useful for getting the shortest distance.
    private int returnSmallest(ArrayList<Integer> input) {

        int shortestDistance = input.get(0);
        int shortestDistanceIndex = 0;
        for (int i = 1; i < input.size(); i++) {

            if (input.get(i) < shortestDistance) {
                shortestDistance = input.get(i);
                shortestDistanceIndex = i;
            }

        }

        return shortestDistanceIndex;

    }

    //Makes one character's move.
    private void makeCharacterMove(Fighter<Character> character) {

        int closestEnemyIndex = getShortestEnemyDistanceIndex(character);
        Fighter<Enemy> targetEnemy = this.enemies.get(closestEnemyIndex);

        int distance = Math.abs(character.position - targetEnemy.position);

        boolean isMelee = character.obj.getWeapon() instanceof Melee;

        double accuracy;

        if (isMelee) {
            accuracy = 0;
        } else {
            accuracy = character.obj.getAccuracy(distance);
        }

        if (distance > 0 && isMelee) { //if you can only Melee and are far away, walk closer
            moveCharacterForward(character);

        } else if (distance == 0 && isMelee) { //if you are in enemies face, Melee
            targetEnemy.obj.takeDamage(character.obj.attackMelee(targetEnemy.obj.getName()));

        } else if (accuracy < TARGET_ACCURACY) { //if you are too far away to shoot accurately, walk closer
            moveCharacterForward(character);

        } else { //if you are close enough to shoot accurately, shoot Enemy
            targetEnemy.obj.takeDamage(character.obj.attackRanged(accuracy, targetEnemy.obj.getName()));
        }
    }


    //Moves the character forward
    private void moveCharacterForward(Fighter<Character> character) {

        character.position += 1;
        System.out.println(character.obj.getName() + "MovesForward");

    }

}
