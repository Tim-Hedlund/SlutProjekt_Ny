import Input.InputMaster;
import java.io.File;
import java.util.ArrayList;

public class Game {
    InputMaster input = new InputMaster();
    Team team;
    ArrayList<Character> allCharacters = new ArrayList<>();
    ArrayList<Weapon> allWeapons = new ArrayList<>();

    Game () {

        this.team = new Team();

        ObjectBuilder objectBuilder = new ObjectBuilder();

        allCharacters = objectBuilder.returnCharacters();

        startGame();

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
