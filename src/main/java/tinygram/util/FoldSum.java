package tinygram.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;

/**
 * A functional consumer, acting as a sum fold operation.
 *
 * <p> Given values are mapped into numbers and accumulated within a global sum, which can be retrieved
 * at any time.
 */
public class FoldSum<T> implements Consumer<T> {

    /**
     * The function to convert incoming objects into longs.
     */
    private final ToLongFunction<T> function;
    /**
     * The current accumulated sum.
     */
    private long sum;

    /**
     * Creates a sum fold consumer.
     *
     * @param function The function to convert incoming objects into longs.
     */
    public FoldSum(ToLongFunction<T> function) {
        this.function = Objects.requireNonNull(function);
        sum = 0;
    }

    /**
     * Gets the current accumulated sum.
     *
     * @return The sum value.
     */
    public long getValue() {
        return sum;
    }

    /**
     * Converts a value into a number and accumulates it in its sum.
     *
     * @param value The value to convert and accumulate.
     */
    @Override
    public void accept(T value) {
        sum += function.applyAsLong(value);
    }
}
