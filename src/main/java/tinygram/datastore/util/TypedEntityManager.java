package tinygram.datastore.util;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

/**
 * An interface to manage entities of a specific kind within a specific transaction.
 *
 * @param <T> The managed entity type.
 */
public interface TypedEntityManager<T extends TypedEntity> {

    /**
     * Fetches an entity from the datastore.
     *
     * @param key The entity key.
     *
     * @return The fetched entity.
     *
     * @throws EntityNotFoundException If the entity does not exist.
     */
    T get(Key key) throws EntityNotFoundException;
}
