package tinygram.datastore;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.util.TransactionContext;

class CounterManagerImpl implements CounterManager {

    private final TransactionContext context;
    private final String kind;

    public CounterManagerImpl(TransactionContext context, String kind) {
        this.context = context;
        this.kind = kind;
    }

    @Override
    public CounterEntity register(String name) {
        return new CounterEntityImpl(kind, name);
    }

    @Override
    public CounterEntity get(String name) throws EntityNotFoundException {
        return get(KeyFactory.createKey(CounterEntity.KIND(kind), name));
    }

    @Override
    public CounterEntity get(Key key) throws EntityNotFoundException {
        return new CounterEntityImpl(kind, context.get(key));
    }
}
