package tinygram.datastore;

import com.google.appengine.api.datastore.EntityNotFoundException;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

/**
 * An interface to manage {@link CounterEntity} entities within a specific transaction.
 */
public interface CounterManager extends TypedEntityManager<CounterEntity> {

    /**
     * Gets the counter entity interface for a specific counter type.
     *
     * @param kind The counter type identifier.
     *
     * @return The counter entity interface associated to <b>kind</b>.
     */
    static CounterManager getOf(String kind) {
        return new CounterManagerImpl(TransactionContext.getCurrent(), kind);
    }

    /**
     * Creates a new counter entity.
     *
     * @param name The counter name.
     *
     * @return The generated counter entity.
     */
    CounterEntity register(String name);

    /**
     * Fetches a counter from the datastore.
     *
     * @param name The counter name.
     *
     * @return The fetched counter entity.
     *
     * @throws EntityNotFoundException If the counter entity does not exist.
     */
    CounterEntity get(String name) throws EntityNotFoundException;
}
