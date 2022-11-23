package tinygram.util;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An iterator functor. Extracts values from another iterator before converting these.
 *
 * @param <T> The input iterator value type.
 * @param <U> The output iterator value type.
 */
class IteratorMapper<T, U> implements Iterator<U> {

    private final Iterator<T> iterator;
    private final Function<? super T, ? extends U> mapper;

    /**
     * Creates an iterator functor.
     *
     * @param iterator The iterator to extract values from.
     * @param mapper   The function to convert extracted values.
     */
    public IteratorMapper(Iterator<T> iterator, Function<? super T, ? extends U> mapper) {
        this.iterator = iterator;
        this.mapper = mapper;
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
