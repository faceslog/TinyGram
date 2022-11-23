package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import tinygram.datastore.util.TransactionContext;
import tinygram.util.IteratorUtils;

class CounterShardManagerImpl implements CounterShardManager {

    private final TransactionContext context;
    private final CounterEntity counter;
    private final String kind;

    public CounterShardManagerImpl(TransactionContext context, CounterEntity counter) {
        this.context = context;
        this.counter = counter;
        kind = CounterShardEntity.KIND(counter);
    }

    @Override
    public CounterShardEntity register(long number) {
        return new CounterShardEntityImpl(counter, number);
    }

    @Override
    public CounterShardEntity get(long number) throws EntityNotFoundException {
        return get(KeyFactory.createKey(kind, String.valueOf(number)));
    }

    @Override
    public CounterShardEntity get(Key key) throws EntityNotFoundException {
        return new CounterShardEntityImpl(counter, context.get(key));
    }

    @Override
    public Iterator<CounterShardEntity> findAll() {
        final Query query = new Query(kind);

        final Iterator<Entity> iterator = context.findAll(query);
        return IteratorUtils.map(iterator, raw -> new CounterShardEntityImpl(counter, raw));
    }
}
