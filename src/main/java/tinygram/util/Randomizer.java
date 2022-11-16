package tinygram.util;

import java.util.Random;

public class Randomizer {

    private static final String ALLOWED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    private static final Random random = new Random();

    public static String alphanum(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
        }

        return sb.toString();
    }

    private Randomizer() {}
}
