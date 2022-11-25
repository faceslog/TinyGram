package tinygram.datastore;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

/**
 * A datastore entity family, representing a counter shard.
 */
public interface CounterShardEntity extends TypedEntity {

    /**
     * Gets the kind of all counter shards from a specific counter.
     *
     * @param counter The counter entity.
     *
     * @return The counter shard kind associated to <b>counter</b>.
     */
    static String KIND(CounterEntity counter) {
        return counter.getKind() + '_' + counter.getKey().getName();
    }

    /**
     * The property storing the counter shard value.
     */
    static final Property<Long> PROPERTY_VALUE = Property.number("value");

    /**
     * Increments the counter shard value.
     */
    void increment();

    /**
     * Decrements the counter shard value.
     */
    void decrement();

    /**
     * Gets the counter shard value.
     *
     * @return The counter shard value.
     */
    long getValue();
}
