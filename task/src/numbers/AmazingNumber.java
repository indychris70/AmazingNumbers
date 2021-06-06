package numbers;

import java.util.Arrays;

/**
 * An AmazingNumber is built around a long VALUE. It can report on whether VALUE has any of the defined Properties
 * through exposed hasProperty(Properties property) and hasProperties(Properties[] included, Properties[] excluded)
 * methods.
 */
public class AmazingNumber {

    private long value;

    public AmazingNumber(long value) {
        this.value = value;
    }

    // Public methods

    public long getValue() {
        return value;
    }

    public boolean hasProperties(Properties[] included, Properties[] excluded) {
        for (Properties property : included) {
            if (!hasProperty(property)) {
                return false;
            }
        }
        for (Properties property : excluded) {
            if (hasProperty(property)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasProperty(Properties property) {
        switch (property) {
            case EVEN:
                return isEven();
            case ODD:
                return isOdd();
            case BUZZ:
                return isBuzz();
            case DUCK:
                return isDuck();
            case PALINDROMIC:
                return isPalindromic();
            case GAPFUL:
                return isGapful();
            case SPY:
                return isSpy();
            case SQUARE:
                return isPerfectSquare();
            case SUNNY:
                return isSunny();
            case JUMPING:
                return isJumping();
            case HAPPY:
                return isHappy();
            case SAD:
                return isSad();
            default:
                throw new RuntimeException(String.format("PROPERTY NOT DEFINED: Property %s is not a defined Property.", property.name()));
        }
    }

    public void printPropertiesList() {
        System.out.println(String.format("Properties of %s", value));
        for (Properties property : Properties.VALID_PROPERTIES) {
            System.out.println(String.format("%s: %b", property.name().toLowerCase(), hasProperty(property)));
        }
    }

    public void printPropertiesOneLiner() {
        StringBuilder oneLiner = new StringBuilder()
                .append(value)
                .append(" is ");

        for (Properties property : Properties.VALID_PROPERTIES) {
            oneLiner.append(hasProperty(property) ? String.format("%s, ", property.name().toLowerCase()) : "");
        }
        // remove trailing ','
        oneLiner.delete(oneLiner.length() - 2, oneLiner.length());
        System.out.println(oneLiner.toString());
    }

    // Private methods

    // Property private methods

    private boolean isEven() {
        return value % 2 == 0;
    }

    private boolean isOdd() {
        return !isEven();
    }

    private boolean isBuzz() {
        return endsWithDigit(7) || divisibleBy(7);
    }

    private boolean isDuck() {
        long num = value;
        while(num > 9) {
            if (num % 10 == 0) {
                return true;
            } else {
                num = num / 10;
            }
        }
        return false;
    }

    private boolean isPalindromic() {
        return value == reversed();
    }

    private boolean isGapful() {
        if (value < 100) {
            return false;
        }
        long leadingDigit = value;
        long trailingDigit = value % 10;
        while (leadingDigit > 9) {
            leadingDigit /= 10;
        }
        long divisor = leadingDigit * 10 + trailingDigit;
        return value % divisor == 0;
    }

    private boolean isSpy() {
        return sumOfAllDigits() == productOfAllDigits();
    }

    private boolean isPerfectSquare() {
        return checkPerfectSquare(value);
    }

    private boolean isSunny() {
        return checkPerfectSquare(value + 1);
    }

    private boolean isJumping() {
        long num = value;
        long previousDigit = value % 10;
        long currentDigit;
        boolean isJumping = true;
        while (num > 9) {
            num /= 10;
            currentDigit = num % 10;
            if (Math.abs(previousDigit - currentDigit) == 1) {
                previousDigit = currentDigit;
            } else {
                isJumping = false;
                break;
            }
        }
        return isJumping;
    }

    private boolean isHappy() {
        long workingNum = value;
        long counter = 1L;
        long[] previousNumbers = new long[100];
        int index = 0;
        while (workingNum != 1L) {
            workingNum = sumSquareOfDigits(workingNum);
            if (counter > 500) {
                System.out.println("counter over 500, no end in sight...");
                return false;
            }
            if (find(previousNumbers, workingNum)) {
                return false;
            }
            previousNumbers[index++] = workingNum;
            counter++;
        }
        return true;
    }

    private boolean isSad() {
        return !isHappy();
    }

    // Other private methods
    
    private boolean checkPerfectSquare(long val) {
        double sq = Math.sqrt(val);
        return ((sq - Math.floor(sq)) == 0);
    }

    private boolean endsWithDigit(int number) {
        return value % 10 == number;
    }

    private boolean divisibleBy(int number) {
        return value % number == 0;
    }

    private long reversed() {
        long num = value;
        long reversedNum = 0;
        long trailingDigit;
        while(num > 0) {
            trailingDigit = num % 10;
            reversedNum = reversedNum * 10 + trailingDigit;
            num = num / 10;
        }
        return reversedNum;
    }

    private long sumOfAllDigits() {
        long sum = 0;
        long num = value;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    private long productOfAllDigits() {
        long product = 1;
        long num = value;
        while (num > 0) {
            product *= num % 10;
            num /= 10;
        }
        return product;
    }

    private static long sumSquareOfDigits(long number) {
        long sumOfSquareOfDigits = 0L;
        long currentNum;
        while (number >= 1) {
            currentNum = number % 10;
            sumOfSquareOfDigits += currentNum * currentNum;
            number /= 10;
        }
        return sumOfSquareOfDigits;
    }

    private static boolean find(long[] arr, long target) {
        for (long number : arr) {
            if (number == target) {
                return true;
            }
        }
        return false;
    }
}
