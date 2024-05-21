package Input;

//used to check inputs without having to code a try catch with error messages each time.
public class InputChecker {

    public InputChecker() {}

    //input checker for integers, tries to parse, if it fails it returns -1
    public int toInt (String input) {
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

    //input checker for floats, tries to parse, if it fails it returns -1
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


