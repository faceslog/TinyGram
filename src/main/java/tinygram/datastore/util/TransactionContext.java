package tinygram.datastore.util;

import java.util.Iterator;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/**
 * A context associated to a specific transaction.
 */
public interface TransactionContext {

    /**
     * Gets the context of the currently running transaction.
     *
     * @return The current transaction context.
     *
     * @deprecated Get it from its {@link TransactionManager} instead.
     */
    static TransactionContext getCurrent() {
        return TransactionManagerImpl.getCurrent().getContext();
    }

    /**
     * Fetches an entity from the context, either from its cache or the datatore if it hasn't
     * already been retrieved.
     *
     * @param entity The entity to fetch.
     *
     * @return The fetched entity.
     *
     * @throws EntityNotFoundException If the entity does not exist or has already been removed from
     *                                 the datastore in the current transaction.
     */
    default Entity get(Entity entity) throws EntityNotFoundException {
        return get(entity.getKey());
    }

    /**
     * Fetches an entity from the context, either from its cache or the datatore if it hasn't
     * already been retrieved.
     *
     * @param key The key of the entity to fetch.
     *
     * @return The fetched entity associated to <b>key</b>.
     *
     * @throws EntityNotFoundException If the entity does not exist or has already been removed from
     *                                 the datastore in the current transaction.
     */
    Entity get(Key key) throws EntityNotFoundException;

    /**
     * Fetches an entity from the context, either from its cache or the datatore if it hasn't
     * already been retrieved.
     *
     * @param entity The entity to fetch.
     *
     * @return The fetched entity, or {@code null} if it does not exist or has already been removed
     *         from the datastore in the current transaction.
     */
    default Entity unsafeGet(Entity entity) {
        return unsafeGet(entity.getKey());
    }

    /**
     * Fetches an entity from the context, either from its cache or the datatore if it hasn't
     * already been retrieved.
     *
     * @param key The key of the entity to fetch.
     *
     * @return The fetched entity associated to <b>key</b>, or {@code null} if it does not exist or
     *         has already been removed from the datastore in the current transaction.
     */
    default Entity unsafeGet(Key key) {
        try {
            return get(key);
        } catch (final EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * Checks if an entity exists in the current transaction context.
     *
     * @param entity The entity to check.
     *
     * @return {@code true} if <b>entity</b> exists or has already been added to the datastore in
     * the current transaction, {@code false} otherwise.
     */
    default boolean contains(Entity entity) {
        return contains(entity.getKey());
    }

    /**
     * Checks if an entity exists in the current transaction context.
     *
     * @param key The key of the entity to check.
     *
     * @return {@code true} if an entity with <b>key</b> exists or has already been added to the
     * datastore in the current transaction, {@code false} otherwise.
     */
    default boolean contains(Key key) {
        return unsafeGet(key) != null;
    }

    /**
     * Finds an entity in the datastore matching a specific query.
     *
     * <b>Warning:</b> Does not check entities added to the datastore in the current transaction.
     *
     * @param query The entity query.
     *
     * @return An entity matching <b>query</b> if it exists in the datastore, {@code null} otherwise.
     */
    Entity find(Query query);

    /**
     * Fins all entities in the datastore matching a specific query.
     *
     * <b>Warning:</b> Does not check entities added to the datastore in the current transaction.
     *
     * @param query The entity query.
     *
     * @return An iterator of all entities matching <b>query</b>.
     */
    Iterator<Entity> findAll(Query query);

    /**
     * Adds a batch of entities to the datastore if they do not exist, updating these otherwise.
     *
     * <b>Warning:</b> Does not allow readding entities already removed from the datastore in the
     * current transaction.
     *
     * @param entities The entities to add or update.
     */
    default void persistAll(Iterable<Entity> entities) {
        entities.forEach(this::persist);
    }

    /**
     * Adds a batch of entities to the datastore if they do not exist, updating these otherwise.
     *
     * <b>Warning:</b> Does not allow readding entities already removed from the datastore in the
     * current transaction.
     *
     * @param iterator An iterator of entities to add or update.
     */
    default void persistAll(Iterator<Entity> iterator) {
        iterator.forEachRemaining(this::persist);
    }

    /**
     * Adds an entity to the datastore if it does not exist, updating it otherwise.
     *
     * <b>Warning:</b> Does not allow readding entities already removed from the datastore in the
     * current transaction.
     *
     * @param entity The entity to add or update.
     *
     * @return The now added entity.
     */
    Entity persist(Entity entity);

    /**
     * Removes a batch of entities from the datastore.
     *
     * @param entities The entities to remove.
     */
    default void forgetAll(Iterable<Key> entities) {
        entities.forEach(this::forget);
    }

    /**
     * Removes a batch of entities from the datastore.
     *
     * @param iterator An iterator of entities to remove.
     */
    default void forgetAll(Iterator<Key> iterator) {
        iterator.forEachRemaining(this::forget);
    }

    /**
     * Removes an entity from the datastore.
     *
     * @param entity The entity to remove.
     *
     * @return The now removed entity.
     */
    default Entity forget(Entity entity) {
        return forget(entity.getKey());
    }

    /**
     * Removes an entity from the datastore.
     *
     * @param key The key of the entity to remove.
     *
     * @return The now removed entity.
     */
    Entity forget(Key key);
}
