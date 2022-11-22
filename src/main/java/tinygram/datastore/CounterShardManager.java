package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.EntityNotFoundException;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

public interface CounterShardManager extends TypedEntityManager<CounterShardEntity> {

    static CounterShardManager getOf(CounterEntity counter) {
        return new CounterShardManagerImpl(TransactionContext.getCurrent(), counter);
    }

    CounterShardEntity register(long number);

    CounterShardEntity get(long number) throws EntityNotFoundException;

    Iterator<CounterShardEntity> findAll();
}
