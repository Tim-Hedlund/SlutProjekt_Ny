package Input;

import java.util.Scanner;

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
