package tinygram.util;

/**
 * Provides additional mathematical functions to compute more precise approximations with integers.
 */
public final class IntegerMath {

    /**
     * Computes the power of an integer to the other.
     *
     * @param a The base value.
     * @param b The power.
     *
     * @return <b>a</b> to the power of <b>b</b>.
     */
    public static int pow(int a, int b) {
        int result = 1;
        for (; b > 0; --b) {
            result *= a;
        }
        return result;
    }

    /**
     * Computes the power of an integer to the other.
     *
     * @param a The base value.
     * @param b The power.
     *
     * @return <b>a</b> to the power of <b>b</b>.
     */
    public static long pow(long a, long b) {
        long result = 1l;
        for (; b > 0l; --b) {
            result *= a;
        }
        return result;
    }

    /**
     * Computes the logarithm of an integer.
     *
     * @param a The base value.
     * @param b The logarithm base.
     *
     * @return The logarithm of <b>a</b> with base <b>b</b>.
     */
    public static int log(int a, int b) {
        int result = 0;
        for (; a > 1; a /= b) {
            ++result;
        }
        return result;
    }

    /**
     * Computes the logarithm of an integer.
     *
     * @param a The base value.
     * @param b The logarithm base.
     *
     * @return The logarithm of <b>a</b> with base <b>b</b>.
     */
    public static long log(long a, long b) {
        long result = 0l;
        for (; a > 1l; a /= b) {
            ++result;
        }
        return result;
    }

    private IntegerMath() {}
}
