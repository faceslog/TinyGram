package tinygram.datastore;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;
import tinygram.util.IntegerMath;

/**
 * A datastore entity family, representing a sharded counter.
 */
public interface CounterEntity extends TypedEntity {

    /**
     * Gets the kind of all counters of a specific type.
     *
     * @param kind A unique counter type identifier.
     *
     * @return The counter kind associated to <b>kind</b>.
     */
    static String KIND(String kind) {
        return "Counter_" + kind;
    }

    /**
     * The property storing the number of shards the counter has.
     */
    static final Property<Long> PROPERTY_SHARD_COUNT = Property.number("shardcount");

    /**
     * The multiplier used with the minimal number of counter shards, when reaching a new threshold.
     */
    static final long STEP_FACTOR = 2l;
    /**
     * The minimal constant number of counter shards. Lowers or highers the total minimal number,
     * whithout any impact on the threshold placement or step increase.
     */
    static final long STEP_BALANCE = -1l;
    /**
     * The initial threshold number. Highers up the minimal number of counter shards to be at least
     * at the level of this threshold when computing it as usual.
     *
     * <p> Set it to a value > 0 to prevent the initial number of counter shards from being too low
     * at counter initialization.
     */
    static final long THRESHOLD_SKIP = 1l;
    /**
     * The logarithmic base used to compute the various thresholds, when computing the minimal
     * number of counter shards.
     */
    static final long THRESHOLD_BASE = 5l;

    /**
     * Computes the minimal number of shards a counter should have.
     *
     * @param value The current counter value.
     *
     * @return The minimal number of counter shards.
     */
    static long getBaseShardCount(long value) {
        final long safeValue = Math.max(1l, value);
        final long threshold = IntegerMath.log(safeValue, THRESHOLD_BASE);
        final long scale = IntegerMath.pow(2l, Math.max(THRESHOLD_SKIP, threshold));

        return STEP_FACTOR * (STEP_BALANCE + scale);
    }

    /**
     * Gets the number of shards the counter has.
     *
     * <p> <b>Invariant:</b> {@code getShardCount() >= getBaseShardCount(getValue())}
     *
     * @return The number of counter shards.
     */
    long getShardCount();

    /**
     * Increments the counter value, from a randomly selected counter shard.
     */
    void increment();

    /**
     * Decrements the counter value, from a randomly selected counter shard.
     */
    void decrement();

    /**
     * Gets the counter value, summing all counter shard values.
     *
     * @return The total counter value.
     */
    long getValue();
}
