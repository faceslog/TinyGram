package tinygram.datastore.util;

import com.google.appengine.api.datastore.Key;

/**
 * A datastore entity, providing type-safe operations related to its kind.
 */
public interface TypedEntity {

    /**
     * Gets the entity key.
     *
     * @return The entity key, whether the entity has already been added to the datastore or not.
     */
    Key getKey();

    /**
     * Gets the entity kind.
     *
     * @return The string entity kind.
     */
    String getKind();

    /**
     * Adds the entity to the datastore or updates it within a given transaction.
     *
     * @param context The transaction context.
     */
    void persistUsing(TransactionContext context);

    /**
     * Removes the entity from the datastore within a given transaction.
     *
     * @param context The transaction context.
     */
    void forgetUsing(TransactionContext context);
}
