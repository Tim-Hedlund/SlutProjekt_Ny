import java.util.ArrayList;
import java.util.Scanner;


public class Fight {

    private ArrayList<Fighter<Enemy>> enemies = new ArrayList<>();
    private ArrayList<Fighter<Character>> characters = new ArrayList<>();
    private int size;
    private final double TARGET_ACCURACY = 0.5;

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

            if (enemy.obj.isDead()) {
                deadEnemies.add(enemy);
                System.out.println(enemy.obj.getName() + " is dead!");
            } else {
                totalEnemyMoves ++;
                makeEnemyMove(enemy);
            }

        }

        this.enemies.removeAll(deadEnemies);

        if (totalEnemyMoves == 0) {
            return 1;
        }

        return 2;

    }

    public ArrayList<Character> getCharacters() {

        ArrayList<Character> returnCharacters = new ArrayList<>();

        for (Fighter<Character> character : characters) {

            returnCharacters.add(character.obj);

        }

        return returnCharacters;

    }

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

    //alla kan antingen flytta sig mot fienden eller skjuta.

    private void makeEnemyMove(Fighter<Enemy> enemy) {

        if (enemy.obj.isDead()) {
            return;
        }

        choseEnemyMove(enemy);

    }

    void choseEnemyMove(Fighter<Enemy> checker) {

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

    private void attackRangedEnemy(Fighter<Enemy> checker, Fighter<Character> target) {

        final double RANGED_MULTIPLIER = 12;
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

    private boolean checkCritChance(double critChance) {

        double randomNumber = Math.random();

        return randomNumber < critChance;

    }

    private void attackMeleeEnemy(Fighter<Enemy> checker, Fighter<Character> target) {

        final double MELEE_MULTIPLIER = 10;
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

    private double getAccuracy(Fighter<Enemy> checker, int distance) {

        return Math.pow(1-checker.obj.getRangedPowerLossPerRange(), distance);


    }

    private void moveEnemyForward(Fighter<Enemy> checker) {

        checker.position --;
        System.out.println(checker.obj.getName() + " Moves forward");

    }

    int getShortestCharacterDistanceIndex(Fighter<Enemy> checker) {

        int pos = checker.position;

        ArrayList<Integer> oppositionDistance = new ArrayList<>();

        ArrayList<Fighter<Character>> oppositionPositions = new ArrayList<>(characters);

        for (Fighter<Character> character: oppositionPositions) {

            oppositionDistance.add(Math.abs(character.position - pos));

        }

        return returnSmallest(oppositionDistance);


    }

    int getShortestEnemyDistanceIndex(Fighter<Character> checker) {

        int pos = checker.position;

        ArrayList<Integer> oppositionDistance = new ArrayList<>();

        ArrayList<Fighter<Enemy>> oppositionPositions = new ArrayList<>(enemies);

        for (Fighter<Enemy> enemy: oppositionPositions) {

            oppositionDistance.add(Math.abs(enemy.position - pos));

        }

        return returnSmallest(oppositionDistance);

    }

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

    private void makeCharacterMove(Fighter<Character> character) {

        Fighter<Enemy> targetEnemy = this.enemies.get(getShortestEnemyDistanceIndex(character));

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
            character.obj.attackMelee(targetEnemy.obj.getName());

        } else if (accuracy < TARGET_ACCURACY) { //if you are too far away to shoot accurately, walk closer
            moveCharacterForward(character);

        } else { //if you are close enough to shoot accurately, shoot Enemy
            targetEnemy.obj.takeDamage(character.obj.attackRanged(accuracy, targetEnemy.obj.getName()));

        }

    }

    private void moveCharacterForward(Fighter<Character> character) {

        character.position += 1;
        System.out.println(character.obj.getName() + "MovesForward");

    }

}
