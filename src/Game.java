import Input.InputMaster;

public class Game {
    InputMaster input = new InputMaster();
    Team team;

    Game () {

        this.team = new Team();
        startGame();

    }

    void startGame() {

        System.out.println("Welcome to \"Escape the motherland\"");
        System.out.println("Chose to create a character or use a default one");
        System.out.println("write 1 for custom or 2 for a default character");

        int choice = input.getInt();

        System.out.println(choice);

    }





}
