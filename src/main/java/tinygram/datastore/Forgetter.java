package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

@FunctionalInterface
public interface Forgetter {

    default void forget(Entity entity) {
        forget(entity.getKey());
    }

    default void forgetAll(Iterable<Key> keys) {
        for (final Key key : keys) {
            forget(key);
        }
    }

    void forget(Key key);
}
