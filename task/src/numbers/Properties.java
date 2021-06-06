package numbers;

import java.util.Arrays;

public enum Properties {
    EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD;

    public static final Properties[][] MUTUALLY_EXCLUSIVE_PROPERTIES = {
            {EVEN, ODD},
            {DUCK, SPY},
            {SUNNY, SQUARE},
            {HAPPY, SAD}
    };

    public static final Properties[] VALID_PROPERTIES = values();
    public static final String[] PROPERTY_NAMES = getNames();

    public static boolean nameIsValid(String name) {
        return Arrays.asList(PROPERTY_NAMES).contains(name);
    }

    private static String[] getNames() {
        String[] names = new String[VALID_PROPERTIES.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = VALID_PROPERTIES[i].name();
        }
        return names;
    }
}
