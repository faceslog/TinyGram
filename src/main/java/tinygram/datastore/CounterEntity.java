package tinygram.datastore;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

public interface CounterEntity extends TypedEntity {

    static String KIND(String kind) {
        return "Counter_" + kind;
    }

    static final Property<Long> PROPERTY_SHARD_COUNT = Property.number("shardcount");

    static final long STEP_COUNT = 4;
    static final long SKIP_COUNT = 0;
    static final long SKIP_BASE = 0;
    static final long STEP_BASE = 4;

    static long getBaseShardCount(long value) {
        final long safeValue = Math.max(1l, value);
        final long truncatedLog = (long) (Math.log(safeValue) / Math.log(STEP_BASE));
        final long scale = 2l ^ Math.max(SKIP_BASE, truncatedLog);
        return STEP_COUNT * (SKIP_COUNT + scale);
    }

    long getShardCount();

    void increment();

    void decrement();

    long getValue();
}
