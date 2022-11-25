package tinygram.datastore;

import java.util.concurrent.ThreadLocalRandom;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityImpl;
import tinygram.util.FoldSum;

/**
 * An implementation of the {@link CounterEntity} interface.
 */
class CounterEntityImpl extends TypedEntityImpl implements CounterEntity {

    /**
     * The interface to manage counter shards for this specific counter.
     */
    private final CounterShardManager shardManager;

    /**
     * Creates a counter entity, not already added to the datastore.
     *
     * @param kind The counter type identifier.
     * @param name The counter name.
     */
    public CounterEntityImpl(String kind, String name) {
        super(CounterEntity.KIND(kind), name);

        setProperty(PROPERTY_SHARD_COUNT, 0l);

        shardManager = CounterShardManager.getOf(this);

        checkShardCount();
    }

    /**
     * Encapsulates an already existing counter entity.
     *
     * @param raw The raw entity.
     */
    public CounterEntityImpl(String kind, Entity raw) {
        super(CounterEntity.KIND(kind), raw);

        shardManager = CounterShardManager.getOf(this);
    }

    @Override
    public long getShardCount() {
        return getProperty(PROPERTY_SHARD_COUNT);
    }

    /**
     * Ensures the number of counter shards does not fall below the minimal associated value.
     */
    private void checkShardCount() {
        final long baseShardCount = CounterEntity.getBaseShardCount(getValue());
        final long shardCount = getShardCount();

        if (shardCount >= baseShardCount) {
            return;
        }

        for (long shardNumber = shardCount + 1l; shardNumber <= baseShardCount; ++shardNumber) {
            final CounterShardEntity shard = shardManager.register(shardNumber);
            addRelatedEntity(shard);
        }

        setProperty(PROPERTY_SHARD_COUNT, baseShardCount);
    }

    /**
     * Fetches a random shard of the counter from the datastore.
     *
     * @return A random counter shard, assuming the minimal number of counter shards cannot be lower
     *         than 1.
     */
    private CounterShardEntity getRandomShard() {
        final long number = ThreadLocalRandom.current().nextLong(getShardCount()) + 1l;
        try {
            return shardManager.get(number);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException("Missing shard " + number + " of kind " + getKind(), e);
        }
    }

    @Override
    public void increment() {
        final CounterShardEntity shard = getRandomShard();

        shard.increment();
        addRelatedEntity(shard);

        checkShardCount();
    }

    @Override
    public void decrement() {
        final CounterShardEntity shard = getRandomShard();

        shard.decrement();
        addRelatedEntity(shard);
    }

    @Override
    public long getValue() {
        final FoldSum<CounterShardEntity> sum = new FoldSum<>(CounterShardEntity::getValue);

        shardManager.findAll().forEachRemaining(sum);
        return sum.getValue();
    }

    @Override
    public void forgetUsing(TransactionContext context) {
        shardManager.findAll().forEachRemaining(shard -> shard.forgetUsing(context));

        super.forgetUsing(context);
    }
}
