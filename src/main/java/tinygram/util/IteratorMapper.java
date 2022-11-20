package tinygram.util;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorMapper<T, U> implements Iterator<U> {

    private final Iterator<T> iterator;
    private final Function<T, U> mapper;

    public IteratorMapper(Iterator<T> iterator, Function<T, U> mapper) {
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
