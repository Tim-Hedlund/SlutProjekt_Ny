//supposed to be the class that handles saving the game state. Everything that isn't the game but is still necessary for the game to function
public class SessionController {
    private final Game currentGame;

    public SessionController(){

        this.currentGame = new Game();

    }

}
