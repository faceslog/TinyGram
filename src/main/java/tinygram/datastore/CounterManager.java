package tinygram.datastore;

import com.google.appengine.api.datastore.EntityNotFoundException;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

public interface CounterManager extends TypedEntityManager<CounterEntity> {

    static CounterManager getOf(String kind) {
        return new CounterManagerImpl(TransactionContext.getCurrent(), kind);
    }

    CounterEntity register(String name);

    CounterEntity get(String name) throws EntityNotFoundException;
}
