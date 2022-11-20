package tinygram.datastore;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

class TransactionManagerImpl implements TransactionManager {

    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());

    private static final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    @Override
    public <T extends TypedEntity> void persist(Iterable<T> entities) {
        boolean ok = false;

        do {
            final PersisterImpl persister = new PersisterImpl();

            for (final T entity : entities) {
                entity.persistUsing(persister);
            }

            final Transaction transaction = datastoreService.beginTransaction();

            try {
                datastoreService.put(persister.toPersist);
                transaction.commit();
                ok = true;
            } catch (final ConcurrentModificationException e) {
                logger.log(Level.INFO, "Concurrent modification while persisting entities, retrying...", e);
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
        } while (!ok);
    }

    @Override
    public <T extends TypedEntity> void forget(Iterable<T> entities) {
        boolean ok = false;

        do {
            final ForgetterImpl forgetter = new ForgetterImpl();

            for (final T entity : entities) {
                entity.forgetUsing(forgetter);
            }
            forgetter.normalize();

            final Transaction transaction = datastoreService.beginTransaction();

            try {
                datastoreService.put(forgetter.toPersist);
                datastoreService.delete(forgetter.toForget);
                transaction.commit();
                ok = true;
            } catch (final ConcurrentModificationException e) {
                logger.log(Level.INFO, "Concurrent modification while forgetting entities, retrying...", e);
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
        } while (!ok);
    }

    private static class PersisterImpl implements Persister {

        protected final Collection<Entity> toPersist = new LinkedList<>();

        @Override
        public void persist(Entity entity) {
            toPersist.add(entity);
        }
    }

    private static class ForgetterImpl extends PersisterImpl implements Forgetter {

        private final Collection<Key> toForget = new LinkedList<>();

        @Override
        public void forget(Key key) {
            toForget.add(key);
        }

        public void normalize() {
            toPersist.removeIf(entity -> toForget.contains(entity.getKey()));
        }
    }
}
