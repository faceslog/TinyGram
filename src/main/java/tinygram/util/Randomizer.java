package tinygram.util;

import java.util.Random;

public class Randomizer {

    public static final String NUMERIC = "0123456789";
    public static final String UPPER_ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_ALPHABETIC = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHABETIC = UPPER_ALPHABETIC + LOWER_ALPHABETIC;
    public static final String ALPHANUMERIC = ALPHABETIC + NUMERIC;

    private static final Random random = new Random();

    public static String randomize(String allowedChars, int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return sb.toString();
    }

    private Randomizer() {}
}
