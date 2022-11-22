package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;

import tinygram.datastore.util.TypedEntityImpl;

class CounterShardEntityImpl extends TypedEntityImpl implements CounterShardEntity {

    public CounterShardEntityImpl(CounterEntity counter, long number) {
        super(CounterShardEntity.KIND(counter), String.valueOf(number));

        setProperty(PROPERTY_VALUE, 0l);
    }

    public CounterShardEntityImpl(CounterEntity counter, Entity raw) {
        super(CounterShardEntity.KIND(counter), raw);
    }

    @Override
    public long getValue() {
        return getProperty(PROPERTY_VALUE);
    }

    @Override
    public void increment() {
        final long value = getProperty(PROPERTY_VALUE);
        setProperty(PROPERTY_VALUE, value + 1l);
    }

    @Override
    public void decrement() {
        final long value = getProperty(PROPERTY_VALUE);
        setProperty(PROPERTY_VALUE, value - 1l);
    }
}
