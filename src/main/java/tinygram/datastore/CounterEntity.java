package tinygram.datastore;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;
import tinygram.util.IntegerMath;

public interface CounterEntity extends TypedEntity {

    static String KIND(String kind) {
        return "Counter_" + kind;
    }

    static final Property<Long> PROPERTY_SHARD_COUNT = Property.number("shardcount");

    static final long STEP_COUNT = 4l;
    static final long SKIP_COUNT = 0l;
    static final long SKIP_BASE = 0l;
    static final long STEP_BASE = 4l;

    static long getBaseShardCount(long value) {
        final long safeValue = Math.max(1l, value);
        final long truncatedLog = IntegerMath.log(safeValue, STEP_BASE);
        final long scale = IntegerMath.pow(2l, Math.max(SKIP_BASE, truncatedLog));
        return STEP_COUNT * (SKIP_COUNT + scale);
    }

    long getShardCount();

    void increment();

    void decrement();

    long getValue();
}
