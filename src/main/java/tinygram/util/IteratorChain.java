package tinygram.util;

import java.util.Collections;
import java.util.Iterator;

/**
 * An iterator chain, extracting values from a sequence of iterators.
 *
 * @param <T> The iterator value type.
 */
class IteratorChain<T> implements Iterator<T> {

    private Iterator<? extends T> iterator;
    private final Iterator<? extends Iterator<? extends T>> remainingIterators;

    /**
     * Creates an iterator chain.
     *
     * @param iterable An iterator sequence, to flatten.
     */
    public IteratorChain(Iterable<? extends Iterator<? extends T>> iterable) {
        this(iterable.iterator());
    }

    /**
     * Creates an iterator chain.
     *
     * @param iterators An iterator of iterators to flatten.
     */
    public IteratorChain(Iterator<? extends Iterator<? extends T>> iterators) {
        this(Collections.emptyIterator(), iterators);
    }

    /**
     * Creates an iterator chain.
     *
     * @param iterator           The first iterator.
     * @param remainingIterators The following iterators.
     */
    public IteratorChain(Iterator<? extends T> iterator,
                         Iterator<? extends Iterator<? extends T>> remainingIterators) {
        this.iterator = iterator;
        this.remainingIterators = remainingIterators;
        advance();
    }

    private void advance() {
        while (!iterator.hasNext() && remainingIterators.hasNext()) {
            iterator = remainingIterators.next();
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        final T value = iterator.next();
        advance();
        return value;
    }
}
