package tinygram.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Provides utility iterator builders.
 */
public final class IteratorUtils {

    /**
     * Creates an iterator chain from two iterators.
     *
     * @param <T>       The iterator value type.
     *
     * @param iterator1 The first iterator to extract values from.
     * @param iterator2 The second iterator to extract values from.
     *
     * @return An iterator extracting values from <b>iterator1</b>, then <b>iterator2</b>.
     */
    public static <T> Iterator<T> of(Iterator<? extends T> iterator1, Iterator<? extends T> iterator2) {
        return new IteratorChain<>(iterator1, Collections.singleton(iterator2).iterator());
    }

    /**
     * Creates an iterator chain from three iterators.
     *
     * @param <T>       The iterator value type.
     *
     * @param iterator1 The first iterator to extract values from.
     * @param iterator2 The second iterator to extract values from.
     * @param iterator2 The third iterator to extract values from.
     *
     * @return An iterator extracting values from <b>iterator1</b>, then <b>iterator2</b>, then
     *         <b>iterator3</b>.
     */
    public static <T> Iterator<T> of(Iterator<? extends T> iterator1, Iterator<? extends T> iterator2,
                                     Iterator<? extends T> iterator3) {
        final Collection<Iterator<? extends T>> remainingIterators = new ArrayList<>(2);
        remainingIterators.add(iterator2);
        remainingIterators.add(iterator3);

        return new IteratorChain<>(iterator1, remainingIterators.iterator());
    }

    /**
     * Creates an iterator mapped on top of another one.
     *
     * @param <T>      The input iterator value type.
     * @param <U>      The output iterator value type.
     *
     * @param iterator The iterator to extract values from.
     * @param mapper   The function to convert extracted values.
     *
     * @return An iterator extracting values from <b>iterator</b>, then converting these using
     *         <b>mapper</b>.
     */
    public static <T, U> Iterator<U> map(Iterator<T> iterator, Function<T, U> mapper) {
        return new IteratorMapper<>(iterator, mapper);
    }

    private IteratorUtils() {}
}
