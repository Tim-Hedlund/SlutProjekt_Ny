import java.util.ArrayList;


public class Fight {

    private final ArrayList<Fighter<Enemy>> enemies = new ArrayList<>();
    private final ArrayList<Fighter<Character>> characters = new ArrayList<>();
    private final int size;
    private final float TARGET_ACCURACY = 0.5F;

    Fight (ArrayList<Enemy> enemies, ArrayList<Character> characters, int size) {

        this.size = size;

        for (Enemy enemy : enemies) {

            this.enemies.add(new Fighter<>(enemy, size));

        }

        for (Character character : characters) {

            this.characters.add(new Fighter<>(character, 0));

        }

    }

    public ArrayList<Character> startFight() {

        while (true) {
            for (Fighter<Character> character : this.characters) {

                makeCharacterMove(character);

            }
            int totalEnemyMoves = 0;
            for (Fighter<Enemy> enemy : enemies) {

                if (!enemy.obj.isDead()) {
                    totalEnemyMoves ++;
                    makeEnemyMove(enemy);
                }

            }

            if (totalEnemyMoves == 0) {
                break;
            }

        }


        ArrayList<Character> returnCharacters = new ArrayList<>();
        for (Fighter<Character> character : this.characters) {

            returnCharacters.add(character.obj);

        }

        return returnCharacters;

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

        boolean isMelee = checker.obj.rangedPower == 0;

        if (distance > 0 && isMelee) {
            moveEnemyForward(checker);
        } else if (distance == 0) {
            attackMeleeEnemy(checker, targetCharacter);
        }

        double accuracy = getAccuracy(checker, distance);

        if (accuracy < TARGET_ACCURACY) {
            moveEnemyForward(checker);
        } else {
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

        target.obj.takeBlockableDamage(damage, hasCrit);

    }

    private double getAccuracy(Fighter<Enemy> checker, int distance) {

        return Math.pow(1-checker.obj.rangedPowerLossPerRange, distance);


    }

    private void moveEnemyForward(Fighter<Enemy> checker) {

        checker.position --;

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
    }

}
