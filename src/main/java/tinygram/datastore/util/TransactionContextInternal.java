package tinygram.datastore.util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * Context operations only available to the {@link TransactionManager}.
 */
interface TransactionContextInternal extends TransactionContext {

    /**
     * Gets all entities to add to the datastore at the end of the transaction.
     *
     * @return The set of entities to add or update.
     */
    Iterable<Entity> getEntitiesToPersist();

    /**
     * Gets all entities to remove from the datastore at the end of the transaction.
     *
     * @return The set of entities to remove.
     */
    Iterable<Key> getKeysToForget();

    /**
     * Clears the context cache and the lists of datastore entity updates.
     */
    void clear();
}
