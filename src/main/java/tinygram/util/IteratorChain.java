package tinygram.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

/**
 * An iterator chain, extracting values from a sequence of iterators.
 *
 * @param <T> The iterator value type.
 */
class IteratorChain<T> implements Iterator<T> {

    /**
     * The currently read iterator.
     *
     * <p> <b>Invariant:</b> {@code !iterator.hasNext() ==> !remainingIterators.hasNext()}
     */
    private Iterator<? extends T> iterator;
    /**
     * The following iterators.
     *
     * <p> <b>Invariant:</b> {@code remainingIterators.hasNext() ==> iterator.hasNext()}
     */
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
        this.iterator = Objects.requireNonNull(iterator);
        this.remainingIterators = Objects.requireNonNull(remainingIterators);
        advance();
    }

    /**
     * Sets the current iterator to ensure the iterator property invariant.
     */
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
