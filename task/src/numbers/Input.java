package numbers;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Takes a String representing a user command, validates it, and assigns values extracted
 * from the String to Input fields based on specified conventions.
 * Is able to report on whether the input String is valid through isValid method.
 * Is able to print errors if raw input string or any input values are not valid through printErrors method.
 */
public class Input {

    enum PrintOptions {
        ONE_LINER, LIST
    }

    enum Errors {
        FIRST_NOT_NATURAL("The first parameter should be a natural number or zero."),
        SECOND_NOT_NATURAL("The second parameter should be a natural number."),
        INVALID_PROPERTIES("The propert%s %s %s wrong.\nAvailable properties: %s"),
        HAS_MUTUALLY_EXCLUSIVE_PROPERTIES("The request contains mutually exclusive properties: %s\nThere are no numbers with these properties.")
        ;

        private final String text;

        Errors(String text) {
            this.text = text;
        }

        public void print(String... strings) {
            System.out.println(String.format(text, (Object[]) strings));
        }
    }

    private final String rawInput;
    private long startingNumber;
    private long counter;
    private String[] receivedPropertyNames;
    private PrintOptions printOption;
    private final List<Properties> includedProperties = new ArrayList<>();
    private final List<Properties> excludedProperties = new ArrayList<>();
    private final List<String> invalidPropertyNames = new ArrayList<>();
    private final String EXCLUDE_OPERATOR = "-";
    private final int EXIT_COMMAND_VALUE = 0;

    public Input(String rawInput) {
        this.rawInput = rawInput;
        processInput(this.rawInput);
    }

    public long getStartingNumber() {
        return startingNumber;
    }

    public long getCounter() {
        return counter;
    }

    public PrintOptions getPrintOption() {
        return printOption;
    }

    public Properties[] getIncludedProperties() {
        return includedProperties.toArray(Properties[]::new);
    }

    public Properties[] getExcludedProperties() {
        return excludedProperties.toArray(Properties[]::new);
    }

    public boolean exitCommandReceived() {
        return startingNumber == EXIT_COMMAND_VALUE;
    }

    public boolean isValid() {
        return startingNumberIsNatural() && counterIsNatural() && !hasMutuallyExclusiveProperties() && invalidPropertyNames.isEmpty();
    }

    public void printErrors() {
        if (!startingNumberIsNatural()) {
            Errors.FIRST_NOT_NATURAL.print();
        }
        if (!counterIsNatural()) {
            Errors.SECOND_NOT_NATURAL.print();
        }
        if (hasMutuallyExclusiveProperties()) {
            for (String excludedProperties : getMutuallyExclusiveProperties()) {
                Errors.HAS_MUTUALLY_EXCLUSIVE_PROPERTIES.print(excludedProperties);
            }
        }
        if (!invalidPropertyNames.isEmpty()) {
            String pluralIs = invalidPropertyNames.size() == 1 ? "is" : "are";
            String pluralY = invalidPropertyNames.size() == 1 ? "y" : "ies";
            Errors.INVALID_PROPERTIES.print(pluralY, invalidPropertyNames.toString(), pluralIs, Arrays.toString(Properties.VALID_PROPERTIES));
        }
    }

    // private methods

    public void processInput(String input) {
        String[] inputValArray = input.toUpperCase().split(" ");
        startingNumber = Long.parseLong(inputValArray[0]);
        switch (inputValArray.length) {
            case 1:
                counter = 1;
                printOption = PrintOptions.LIST;
                receivedPropertyNames = new String[] {};
                break;
            case 2:
                counter = Long.parseLong(inputValArray[1]);
                printOption = PrintOptions.ONE_LINER;
                receivedPropertyNames = new String[] {};
                break;
            default:
                counter = Long.parseLong(inputValArray[1]);
                printOption = PrintOptions.ONE_LINER;
                receivedPropertyNames = Arrays.copyOfRange(inputValArray, 2, inputValArray.length);
        }
        assignProperties(receivedPropertyNames);
    }

    private void assignProperties(String[] propertyNames) {
        String basePropertyName;
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(EXCLUDE_OPERATOR)) {
                basePropertyName = propertyName.substring(1);
                if (Properties.nameIsValid(basePropertyName)) {
                    excludedProperties.add(Properties.valueOf(basePropertyName));
                } else {
                    invalidPropertyNames.add(basePropertyName);
                }
            } else {
                if (Properties.nameIsValid(propertyName)) {
                    includedProperties.add(Properties.valueOf(propertyName));
                } else {
                    invalidPropertyNames.add(propertyName);
                }
            }
        }
    }

    private List<String> getMutuallyExclusiveProperties() {
        List<String> mutuallyExclusiveProperties = new ArrayList<>();
        for (Properties[] exclusiveProperties : Properties.MUTUALLY_EXCLUSIVE_PROPERTIES) {
            if (includedProperties.containsAll(Arrays.asList(exclusiveProperties)) || excludedProperties.containsAll(Arrays.asList(exclusiveProperties))) {
                mutuallyExclusiveProperties.add(Arrays.toString(exclusiveProperties));
            }
        }
        for (Properties property : Properties.VALID_PROPERTIES) {
            if (includedProperties.contains(property) && excludedProperties.contains(property)) {
                mutuallyExclusiveProperties.add(String.format("[%s, -%s]", property.name(), property.name()));
            }
        }
        return mutuallyExclusiveProperties;
    }

    private boolean hasMutuallyExclusiveProperties() {
        return getMutuallyExclusiveProperties().size() > 0;
    }

    private boolean isNatural(long num) {
        return num > 0;
    }

    private boolean startingNumberIsNatural() {
        return isNatural(startingNumber);
    }

    private boolean counterIsNatural() {
        return isNatural(counter);
    }
}
