package tinygram.datastore.util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

interface TransactionContextInternal extends TransactionContext {
    
    Iterable<Entity> getEntitiesToPersist();

    Iterable<Key> getKeysToForget();

    void clear();
}
