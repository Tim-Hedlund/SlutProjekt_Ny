import java.util.ArrayList;
import java.util.Collections;

public class Fight <C extends Character, E extends Enemy> {

    ArrayList<Fighter<E>> enemies = new ArrayList<>();
    ArrayList<Fighter<C>> characters = new ArrayList<>();
    int size;
    final float TARGET_ACCURACY = 0.5F;

    Fight (ArrayList<E> enemies, ArrayList<C> characters, int size) {

        this.size = size;

        for (E enemy : enemies) {

            this.enemies.add(new Fighter<>(enemy, 0));

        }

        for (C character : characters) {

            this.characters.add(new Fighter<>(character, 0));

        }

    }

    public ArrayList<Character> startFight() {

        boolean enemiesAreAlive = true;

        while (enemiesAreAlive) {
            for (Fighter<C> character : characters) {

                makeCharacterMove(character);

            }
            for (Fighter<E> enemy : enemies) {

                makeEnemyMove(enemy);

            }

        }

    }

    //alla kan antingen flytta sig mot fienden eller skjuta.

    private void makeEnemyMove(Fighter<E> enemy) {

        if (enemy.t.isDead()) {
            return;
        }

        choseMove(enemy);




    }

    void choseMove(Fighter<E> checker) {

        int distance = getShortestDistanceIndex(checker);

        boolean isMelee = checker.t.rangedPower == 0;

        if (distance > 0 && isMelee) {
            moveEnemyForward(checker);
        } else if (distance == 0) {
            attackMeleeEnemy(checker);
        }

        double accuracy = getAccuracy(checker, distance);

        if (accuracy < TARGET_ACCURACY) {
            moveEnemyForward(checker);
        } else {
            attackRangedEnemy(checker);
        }

    }

    private void attackRangedEnemy(Fighter<E> checker) {
    }

    private void attackMeleeEnemy(Fighter<E> checker) {
    }

    private double getAccuracy(Fighter<E> checker, int distance) {

        return Math.pow(1-checker.t.rangedPowerLossPerRange, distance);


    }

    private void moveEnemyForward(Fighter<E> checker) {

        checker.position --;

    }

    private float getCharacterTarget(Fighter<C> checker) {


    }

    int getShortestDistanceIndex(Fighter checker) {

        int pos = checker.position;

        ArrayList<Integer> oppositionDistance = new ArrayList<>();

        if (checker.t instanceof Character) {

            ArrayList<Fighter<E>> oppositionPositions = new ArrayList<>(enemies);

            for (Fighter<E> enemy: oppositionPositions) {

                oppositionDistance.add(Math.abs(enemy.position - pos));

            }

            return returnSmallest(oppositionDistance);


        } else if (checker.t instanceof Enemy) {

            ArrayList<Fighter<C>> oppositionPositions = new ArrayList<>(characters);

            for (Fighter<C> character: oppositionPositions) {

                oppositionDistance.add(Math.abs(character.position - pos));

            }

            return returnSmallest(oppositionDistance);

        }

        return -1;

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

    private void makeCharacterMove(Fighter<C> character) {
    }


}
