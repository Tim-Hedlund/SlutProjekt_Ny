package Input;

public class InputChecker {

    public InputChecker() {}

    public int toInt (String input) { //input checker för integers, returnerar -1 när den inte är integer
        int intInput;

        try {
            intInput = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            System.out.println("Your input was either not an integer or too long");
            System.out.println("input should look like this: ( 123 )");
            intInput = -1;
        }

        return intInput;

    }

    public float toFloat (String input) {
        float floatInput;

        try {
            floatInput = Float.parseFloat(input);
        }
        catch (NumberFormatException e) {
            System.out.println("Your input was either not a float or too long");
            System.out.println("input should look like this: ( 1.23 )");
            floatInput = -1;
        }

        return  floatInput;


    }


}


