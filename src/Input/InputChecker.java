package Input;

public class InputChecker {

    InputChecker() {}

    int toInt (String input) { //input checker för integers, returnerar -1 när den inte är integer
        int intInput;

        try {
            intInput = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            System.out.println("Your input was either not a number or too long");
            System.out.println("input should look like this: ( 123 )");
            intInput = -1;
        }

        return intInput;

    }


}


