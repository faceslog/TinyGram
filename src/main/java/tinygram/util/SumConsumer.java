package tinygram.util;

import java.util.function.Consumer;
import java.util.function.ToLongFunction;

public class SumConsumer<T> implements Consumer<T> {

    private final ToLongFunction<T> function;
    private long sum;

    public SumConsumer(ToLongFunction<T> function) {
        this.function = function;
        sum = 0;
    }

    public long getValue() {
        return sum;
    }

    @Override
    public void accept(T value) {
        sum += function.applyAsLong(value);
    }
}
