package tinygram.datastore;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public final class TransactionManager {

    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());

    private static final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private static final TransactionManager instance = new TransactionManager();

    public static TransactionManager get() {
        return instance;
    }

    public void persist(Supplier<TypedEntity> supplier) {
        persist(supplier.get());
    }

    public void persist(TypedEntity entity) {
        persist(Collections.singleton(entity));
    }

    public <T extends TypedEntity> void persist(Iterable<T> entities) {
        boolean retry = false;

        do {
            final Transaction transaction = datastoreService.beginTransaction();
            final Persister persister = new PersisterImpl(transaction);

            try {
                for (final T entity : entities) {
                    entity.persistUsing(persister);
                }

                transaction.commit();
            } catch (final ConcurrentModificationException e) {
                logger.log(Level.INFO, "Concurrent modification while persisting entities, retrying...", e);
                retry = true;
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
        } while (retry);
    }

    public <T extends TypedEntity> void forget(Iterable<T> entities) {
        boolean retry = false;

        do {
            final Transaction transaction = datastoreService.beginTransaction();
            final Forgetter forgetter = new ForgetterImpl(transaction);

            try {
                for (final T entity : entities) {
                    entity.forgetUsing(forgetter);
                }

                transaction.commit();
            } catch (final ConcurrentModificationException e) {
                logger.log(Level.INFO, "Concurrent modification while forgetting entities, retrying...", e);
                retry = true;
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
        } while (retry);
    }

    private TransactionManager() {}

    private static final class PersisterImpl implements Persister {

        private final Transaction transaction;

        public PersisterImpl(Transaction transaction) {
            this.transaction = transaction;
        }

        @Override
        public void persistAll(Iterable<Entity> entities) {
            datastoreService.put(transaction, entities);
        }

        @Override
        public void persist(Entity entity) {
            datastoreService.put(transaction, entity);
        }
    }

    private static final class ForgetterImpl implements Forgetter {

        private final Transaction transaction;

        public ForgetterImpl(Transaction transaction) {
            this.transaction = transaction;
        }

        @Override
        public void forgetAll(Iterable<Key> keys) {
            datastoreService.delete(transaction, keys);
        }

        @Override
        public void forget(Key key) {
            datastoreService.delete(transaction, key);
        }
    }
}
