import Input.InputMaster;

import java.io.File;
import java.util.ArrayList;

public class Game {
    InputMaster input = new InputMaster();
    Team team;
    ArrayList<Character> allCharacters = new ArrayList<Character>();

    Game () {

        this.team = new Team();
        startGame();

    }

    void startGame() {




        System.out.println("Welcome to \"Escape the motherland\"");
        System.out.println("Chose to create a character or use a default one");
        System.out.println("write 1 for custom or 2 for a default character");

        File characters = new File("src/characters.txt");
        FileMaster characterFileMaster = new FileMaster(characters);

        int choice = input.getInt();

        System.out.println(choice);

    }





}
