import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        MaxMinDriverClass maxMinDriverClass = new MaxMinDriverClass(scanner, 600);
        maxMinDriverClass.execute();
        scanner.close();
    }
}

class MaxMinDriverClass {
    private static final String INVALID_INTEGER_MSG = "Invalid input: %s is not a valid integer.";
    private static final String INVALID_ARRAY_MSG = "Invalid array entered.";
    private static final String EMPTY_ARRAY_MSG = "Cannot find Max and Min in empty array.";
    private static final String EMPTY_ELEMENT_MSG = "Empty elements not allowed in array.";
    private static final String INPUT_TOO_LONG_MSG = "Input length too long. Input length should be less than %s";
    private static final String EMPTY_ELEMENT_STRING = ",,";

    private Scanner scanner;
    private int maxInputSize;

    public MaxMinDriverClass(Scanner scanner, int maxInputSize) {
        this.scanner = scanner;
        this.maxInputSize = maxInputSize;
    }

    public void execute() {// gets array from user and return max and min values in the array

        System.out.println("Find Maximum and Minimum value in an integer array.");

        boolean toContinue;
        do {
            Optional<int[]> arrInt = getIntegerArray();
            if (arrInt.isPresent()) { // checks if elements were added successfully in the array

                int[] ans = findMaxMin(arrInt.get());
                System.out.println(String.format("Maximum: %s, Minimum: %s", ans[0], ans[1]));
            }

            System.out.println("Enter Y, to start over finding Maximum and Minimum in a new array: (Y/N)"); // re-run the code if user needs
            String continuePrompt = this.scanner.nextLine();
            toContinue = continuePrompt.trim().equalsIgnoreCase("Y");
        } while (toContinue);
    }

    private Optional<int[]> getIntegerArray() {//gets valid integer array from user

        System.out.println("Enter an integer array in the form [1,-2,0]: ");
        String input = scanner.nextLine();
        if (!isInputStringValid(input)) {
            return Optional.empty();
        }

        String[] stringIntegers = input.substring(1, input.length() - 1).split(",");//exclude the brackets when splitting
        if (stringIntegers.length == 0) {//if empty array was entered
            System.out.println(EMPTY_ARRAY_MSG);
            return Optional.empty();
        }
        int[] intArray = new int[stringIntegers.length];
        for (int i = 0; i < stringIntegers.length; i++) {
            Optional<Integer> arrElement = getIntValue(stringIntegers[i]);
            if (arrElement.isEmpty()) {
                return Optional.empty();
            }
            intArray[i] = arrElement.get();
        }
        return Optional.of(intArray);
    }

    private boolean isInputStringValid(String input) {
        if (input.length() < 2) {//input should have atleast opening and closing brackets
            System.out.println(INVALID_ARRAY_MSG);
            return false;
        }
        if (input.length() > maxInputSize) {// maxlength set to avoid out of memory exceptions
            System.out.println(String.format(INPUT_TOO_LONG_MSG, maxInputSize));
            return false;
        }

        if (input.contains(EMPTY_ELEMENT_STRING)) {//empty element should not be entered
            System.out.println(EMPTY_ELEMENT_MSG);
            return false;
        }
        if (input.charAt(0) != '[' || input.charAt(input.length() - 1) != ']') {

            System.out.println(INVALID_ARRAY_MSG);//if start and end of input do not have brackets
            return false;
        }
        if (input.charAt(0) == '[' && input.charAt(input.length() - 1) == ']' && input.length()==2) {

            System.out.println(EMPTY_ARRAY_MSG);//if start and end of input have brackets but no elements
            return false;
        }
        return true;
    }

    private Optional<Integer> getIntValue(String input) {
        try {
            if (input.trim().isEmpty()) {
                System.out.println(EMPTY_ELEMENT_MSG);
                return Optional.empty();
            }
            Integer intValue = Integer.parseInt(input.trim());
            return Optional.of(intValue);
        } catch (NumberFormatException numberFormatException) {
            System.out.println(String.format(INVALID_INTEGER_MSG, input)); // entered input is not a valid integer
        }
        return Optional.empty();
    }

    private static int[] findMaxMin(int[] arrInt) {//finds max and min values in the given array

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < arrInt.length; i++) {
            if (arrInt[i] < minValue) {
                minValue = arrInt[i];
            }
            if (arrInt[i] > maxValue) {
                maxValue = arrInt[i];
            }
        }
        return new int[]{maxValue, minValue};// Returning max first, min second
    }
}
