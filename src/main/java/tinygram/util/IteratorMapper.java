package tinygram.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * An iterator functor.
 *
 * Extracts values from another iterator before converting these.
 *
 * @param <T> The input iterator value type.
 * @param <U> The output iterator value type.
 */
class IteratorMapper<T, U> implements Iterator<U> {

    /**
     * The iterator to extract values from.
     */
    private final Iterator<T> iterator;
    /**
     * The function to convert extracted iterator values.
     */
    private final Function<T, U> mapper;

    /**
     * Creates an iterator functor.
     *
     * @param iterator The iterator to extract values from.
     * @param mapper   The function to convert extracted values.
     */
    public IteratorMapper(Iterator<T> iterator, Function<T, U> mapper) {
        this.iterator = Objects.requireNonNull(iterator);
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public U next() {
        return mapper.apply(iterator.next());
    }
}
