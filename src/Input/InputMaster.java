package Input;
import java.util.Scanner;

//Class used to take input and uses the input checker to check, didn't use this one just used input checker instead.
public class InputMaster {

    public InputMaster() {}

    public int getInt() {//tar input och fortsätter tills kraven för parseInt är nådda

        Scanner scan = new Scanner(System.in);
        InputChecker checker = new InputChecker();
        int intInput;

        while (true) {

            String input = scan.nextLine();
            intInput = checker.toInt(input);

            if (intInput != -1) {return(intInput);}


        }

    }

}
