package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.EntityNotFoundException;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

/**
 * An interface to manage {@link CounterShardEntity} entities within a specific transaction.
 */
public interface CounterShardManager extends TypedEntityManager<CounterShardEntity> {

    /**
     * Gets the counter shard entity interface for a specific counter.
     *
     * @param counter The counter entity.
     *
     * @return The counter shard entity interface associated to <b>counter</b>.
     */
    static CounterShardManager getOf(CounterEntity counter) {
        return new CounterShardManagerImpl(TransactionContext.getCurrent(), counter);
    }

    /**
     * Creates a new counter shard entity.
     *
     * @param number The counter shard number.
     *
     * @return The generated counter shard entity.
     */
    CounterShardEntity register(long number);

    /**
     * Fetches a counter shard from the datastore.
     *
     * @param number The counter shard number.
     *
     * @return The fetched counter shard entity.
     *
     * @throws EntityNotFoundException If the counter shard entity does not exist.
     */
    CounterShardEntity get(long number) throws EntityNotFoundException;

    /**
     * Fetches all counter shards.
     *
     * @return An iterator of all counter shards of the associated counter entity.
     */
    Iterator<CounterShardEntity> findAll();
}
