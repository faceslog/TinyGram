package tinygram.datastore.util;

import java.util.Iterator;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

public interface TransactionContext {

    static TransactionContext getCurrent() {
        // TODO: remove this design abomination
        return TransactionManagerImpl.getCurrent().getContext();
    }

    default Entity get(Entity entity) throws EntityNotFoundException {
        return get(entity.getKey());
    }

    Entity get(Key key) throws EntityNotFoundException;

    default Entity unsafeGet(Entity entity) {
        return unsafeGet(entity.getKey());
    }

    default Entity unsafeGet(Key key) {
        try {
            return get(key);
        } catch (final EntityNotFoundException e) {
            return null;
        }
    }

    default boolean contains(Entity entity) {
        return contains(entity.getKey());
    }

    default boolean contains(Key key) {
        return unsafeGet(key) != null;
    }

    Entity find(Query query);

    Iterator<Entity> findAll(Query query);

    default void persistAll(Iterable<Entity> entities) {
        entities.forEach(this::persist);
    }

    default void persistAll(Iterator<Entity> iterator) {
        iterator.forEachRemaining(this::persist);
    }

    Entity persist(Entity entity);

    default void forgetAll(Iterable<Key> entities) {
        entities.forEach(this::forget);
    }

    default void forgetAll(Iterator<Key> iterator) {
        iterator.forEachRemaining(this::forget);
    }

    default Entity forget(Entity entity) {
        return forget(entity.getKey());
    }

    Entity forget(Key key);
}
