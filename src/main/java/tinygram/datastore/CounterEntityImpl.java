package tinygram.datastore;

import java.util.concurrent.ThreadLocalRandom;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityImpl;
import tinygram.util.SumConsumer;

class CounterEntityImpl extends TypedEntityImpl implements CounterEntity {

    private final CounterShardManager shardManager;

    public CounterEntityImpl(String kind, String name) {
        super(CounterEntity.KIND(kind), name);

        setProperty(PROPERTY_SHARD_COUNT, 0l);

        shardManager = CounterShardManager.getOf(this);

        checkShardCount();
    }

    public CounterEntityImpl(String kind, Entity raw) {
        super(CounterEntity.KIND(kind), raw);

        shardManager = CounterShardManager.getOf(this);
    }

    @Override
    public long getShardCount() {
        return getProperty(PROPERTY_SHARD_COUNT);
    }

    private void checkShardCount() {
        final long baseShardCount = CounterEntity.getBaseShardCount(getValue());
        final long shardCount = getShardCount();

        for (long shardNumber = shardCount + 1l; shardNumber <= baseShardCount; ++shardNumber) {
            final CounterShardEntity shard = shardManager.register(shardNumber);
            addRelatedEntity(shard);
        }

        setProperty(PROPERTY_SHARD_COUNT, baseShardCount);
    }

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
        final SumConsumer<CounterShardEntity> sum = new SumConsumer<>(CounterShardEntity::getValue);

        shardManager.findAll().forEachRemaining(sum);
        return sum.getValue();
    }

    @Override
    public void forgetUsing(TransactionContext context) {
        shardManager.findAll().forEachRemaining(shard -> shard.forgetUsing(context));

        super.forgetUsing(context);
    }
}
