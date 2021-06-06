package numbers;

import java.util.Arrays;

/**
 * Takes an instance of Input.
 * Based on Input values, creates one or more AmazingNumbers and outputs information about them.
 */
public class Controller {

    private Input input;

    private Controller() {};

    public static Controller newController() {
        return new Controller();
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void print() {
        AmazingNumber number = new AmazingNumber(input.getStartingNumber());
        if (input.getPrintOption() == Input.PrintOptions.LIST) {
            number.printPropertiesList();
        } else {
            long hitCount = 0;
            while (hitCount < input.getCounter()) {
                if (number.hasProperties(input.getIncludedProperties(), input.getExcludedProperties())) {
                    number.printPropertiesOneLiner();
                    hitCount++;
                }
                number = new AmazingNumber(number.getValue() + 1);
            }
        }
    }
}
