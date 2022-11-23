package tinygram.util;

import java.util.Random;

/**
 * Provides functions to generate random identifiers.
 */
public class Randomizer {

    /**
     * The set of all numeric characters.
     */
    public static final String NUMERIC = "0123456789";
    /**
     * The set of all uppercase alphabetic characters.
     */
    public static final String UPPER_ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * The set of all lowercase alphabetic characters.
     */
    public static final String LOWER_ALPHABETIC = "abcdefghijklmnopqrstuvwxyz";
    /**
     * The set of all alphabetic characters, either uppercase or lowercase.
     */
    public static final String ALPHABETIC = UPPER_ALPHABETIC + LOWER_ALPHABETIC;
    /**
     * The set of all numeric and alphabetic characters, either uppercase or lowercase.
     */
    public static final String ALPHANUMERIC = ALPHABETIC + NUMERIC;

    private static final Random random = new Random();

    /**
     * Generates a random string of fixed length.
     * 
     * @param allowedChars The set of characters to generate the string from. The number of
     *                     occurrences of each one increases its weight.
     * @param length       The generated string length.
     * 
     * @return A string composed of characters from <b>allowedChars</b>.
     */
    public static String randomize(String allowedChars, int length) {
        final StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            stringBuilder.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return stringBuilder.toString();
    }

    private Randomizer() {}
}
