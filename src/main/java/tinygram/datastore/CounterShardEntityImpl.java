package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;

import tinygram.datastore.util.TypedEntityImpl;

/**
 * An implementation of the {@link CounterShardEntity} interface.
 */
class CounterShardEntityImpl extends TypedEntityImpl implements CounterShardEntity {

    /**
     * Creates a counter shard entity, not already added to the datastore.
     *
     * @param counter The counter entity.
     * @param number  The counter shard number.
     */
    public CounterShardEntityImpl(CounterEntity counter, long number) {
        super(CounterShardEntity.KIND(counter), String.valueOf(number));

        setProperty(PROPERTY_VALUE, 0l);
    }

    /**
     * Encapsulates an already existing counter entity.
     * 
     * @param counter The counter entity.
     * @param raw     The raw entity.
     */
    public CounterShardEntityImpl(CounterEntity counter, Entity raw) {
        super(CounterShardEntity.KIND(counter), raw);
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

    @Override
    public long getValue() {
        return getProperty(PROPERTY_VALUE);
    }
}
