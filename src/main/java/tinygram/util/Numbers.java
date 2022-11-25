package tinygram.util;

import com.google.appengine.api.memcache.InvalidValueException;

/**
 * Provides utility functions for manipulating numbers.
 */
public final class Numbers {

    /**
     * Ensures a value is positive or null.
     *
     * @param value The value to check.
     *
     * @return {@code true} if <b>value</b> is positive or null, {@code false} otherwise.
     */
    public static float requireNonNegative(float value) {
        if (value < 0f) {
            throw new InvalidValueException("Positive or null value expected, got " + value + ".");
        }

        return value;
    }

    /**
     * Ensures a value is positive or null.
     *
     * @param value The value to check.
     *
     * @return {@code true} if <b>value</b> is positive or null, {@code false} otherwise.
     */
    public static double requireNonNegative(double value) {
        if (value < 0d) {
            throw new InvalidValueException("Positive or null value expected, got " + value + ".");
        }

        return value;
    }

    /**
     * Ensures a value is positive or null.
     *
     * @param value The value to check.
     *
     * @return {@code true} if <b>value</b> is positive or null, {@code false} otherwise.
     */
    public static int requireNonNegative(int value) {
        if (value < 0) {
            throw new InvalidValueException("Positive or null value expected, got " + value + ".");
        }

        return value;
    }

    /**
     * Ensures a value is positive or null.
     *
     * @param value The value to check.
     *
     * @return {@code true} if <b>value</b> is positive or null, {@code false} otherwise.
     */
    public static long requireNonNegative(long value) {
        if (value < 0l) {
            throw new InvalidValueException("Positive or null value expected, got " + value + ".");
        }

        return value;
    }

    private Numbers() {}
}
