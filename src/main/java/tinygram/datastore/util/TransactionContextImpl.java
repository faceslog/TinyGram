package tinygram.datastore.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

import tinygram.util.IteratorUtils;

/**
 * An implementation of the {@link TransactionContext} interface.
 *
 * <p> Stores entities in a cache to prevent using multiple {@link Entity} objects within the same
 * transaction.
 *
 * <p> Only adds and removes entities from the datastore at the end of the transaction
 * to reduce resource locking.
 */
class TransactionContextImpl implements TransactionContextInternal {

    private static final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    /**
     * The entity cache, containing entities fetched from the datastore or added to it within the
     * transaction.
     */
    private final Map<Key, Entity> cache;
    /**
     * The set of entities to add to the datastore or update at the end of this transaction.
     */
    private final Collection<Entity> entitiesToPersist;
    /**
     * The set of entities to remove from the datastore at the end of this transaction.
     */
    private final Collection<Key> keysToForget;

    /**
     * Creates an empty transaction context.
     */
    public TransactionContextImpl() {
        cache = new HashMap<>();
        entitiesToPersist = new HashSet<>();
        keysToForget = new HashSet<>();
    }

    @Override
    public Entity get(Key key) throws EntityNotFoundException {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        final Entity entity = datastoreService.get(key);
        cache.put(key, entity);

        return entity;
    }

    /**
     * Puts an entity in the cache, and retrieves it if there already is one in the cache.
     *
     * @param entity The entity to add to the cache.
     *
     * @return The already cached entity, or <b>entity</b> if there was not any.
     */
    private Entity putIfMissing(Entity entity) {
        final Key key = entity.getKey();

        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            cache.put(key, entity);
            return entity;
        }
    }

    @Override
    public Entity find(Query query) {
        final Entity entity = datastoreService.prepare(query).asSingleEntity();
        if (entity == null) {
            return null;
        }

        return putIfMissing(entity);
    }

    @Override
    public Iterator<Entity> findAll(String kind) {
        final Query query = new Query(kind);

        final Iterator<Entity> iterator = datastoreService.prepare(query).asIterator();

        final Collection<Entity> additionalEntities = new HashSet<>();
        entitiesToPersist.forEach(entity -> {
            if (entity.getKey().getKind().equals(kind))
                additionalEntities.add(entity);
        });

        IteratorUtils.map(iterator, entity -> {
            entity = putIfMissing(entity);
            additionalEntities.remove(entity);
            return entity;
        });

        return IteratorUtils.of(iterator, additionalEntities.iterator());
    }

    @Override
    public Iterator<Entity> findAll(Query query) {
        final Iterator<Entity> iterator = datastoreService.prepare(query).asIterator();
        return IteratorUtils.map(iterator, this::putIfMissing);
    }

    @Override
    public Entity persist(Entity entity) {
        final Key key = entity.getKey();
        final Entity storedEntity = cache.get(key);

        if (storedEntity == null && cache.containsKey(key)) {
            throw new IllegalArgumentException("Trying to persist an entity, which has already " +
                    "been forgetted in the same transaction. If this is intentional, consider " +
                    "persiting it in another transaction, or prevent forgetting it earlier in " +
                    "this transaction.");
        }

        if (storedEntity != null && storedEntity != entity) {
            throw new IllegalArgumentException("Trying to persist an entity, while an unrelated " +
                    "one has already been persisted. Consider getting it from the transaction " +
                    "context instead of modifying another one.");
        }

        cache.putIfAbsent(key, entity);
        entitiesToPersist.add(entity);

        return entity;
    }

    @Override
    public Entity forget(Key key) {
        Entity storedEntity = cache.get(key);

        if (storedEntity == null && cache.containsKey(key)) {
            throw new IllegalArgumentException("Trying to forget an entity, which has already " +
                    "been forgetted in this transaction.");
        }

        if (storedEntity == null) {
            storedEntity = unsafeGet(key);
        }

        if (storedEntity == null) {
            throw new IllegalArgumentException("Trying to forget an entity, which has not already " +
                    "been persisted or has been forgetted in a previously commited transaction.");
        }

        cache.put(key, null);
        entitiesToPersist.remove(storedEntity);
        keysToForget.add(key);

        return storedEntity;
    }

    @Override
    public Iterable<Entity> getEntitiesToPersist() {
        return entitiesToPersist;
    }

    @Override
    public Iterable<Key> getKeysToForget() {
        return keysToForget;
    }

    @Override
    public void clear() {
        cache.clear();
        entitiesToPersist.clear();
        keysToForget.clear();
    }
}
