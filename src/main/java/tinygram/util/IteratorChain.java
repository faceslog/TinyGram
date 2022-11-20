package tinygram.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class IteratorChain<T> implements Iterator<T> {

    private Iterator<? extends T> iterator;
    private final Iterator<? extends Iterator<? extends T>> remainingIterators;

    public static <T> Iterator<T> of(Iterator<? extends T> iterator1, Iterator<? extends T> iterator2) {
        return new IteratorChain<T>(iterator1, Collections.singleton(iterator2).iterator());
    }

    public static <T> Iterator<T> of(Iterator<? extends T> iterator1, Iterator<? extends T> iterator2, Iterator<? extends T> iterator3) {
        final Collection<Iterator<? extends T>> remainingIterators = new ArrayList<>(2);
        remainingIterators.add(iterator2);
        remainingIterators.add(iterator3);

        return new IteratorChain<T>(iterator1, remainingIterators.iterator());
    }

    public IteratorChain(Iterable<? extends Iterator<? extends T>> iterators) {
        this(iterators.iterator());
    }

    public IteratorChain(Iterator<? extends Iterator<? extends T>> iterators) {
        this(Collections.emptyIterator(), iterators);
    }

    private IteratorChain(Iterator<? extends T> iterator, Iterator<? extends Iterator<? extends T>> remainingIterators) {
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
