package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;

@FunctionalInterface
public interface Persister {

    default void persistAll(Iterable<Entity> entities) {
        for (final Entity entity : entities) {
            persist(entity);
        }
    }

    void persist(Entity entity);
}
