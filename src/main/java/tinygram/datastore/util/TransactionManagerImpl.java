package tinygram.datastore.util;

import java.util.concurrent.Future;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.google.appengine.api.datastore.TransactionOptions.Mode;

/**
 * An implementation of the {@TransactionManager} interface.
 */
class TransactionManagerImpl implements TransactionManager {

    private static final Logger logger = Logger.getLogger(TransactionContextImpl.class.getName());
    private static final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    /**
     * The currently active transaction.
     */
    private static TransactionManager current;

    /**
     * Gets the currently active transaction.
     *
     * @return The transaction interface if a transaction has already been started, {@code null}
     *         otherwise.
     */
    public static TransactionManager getCurrent() {
        return TransactionManagerImpl.current;
    }

    /**
     * Sets the currently active transaction.
     *
     * @param transactionManager The transaction interface.
     */
    public static void setCurrent(TransactionManager transactionManager) {
        TransactionManagerImpl.current = transactionManager;
    }

    /**
     * The encapsulated Google transaction interface.
     */
    private final Transaction transaction;
    /**
     * The transaction context.
     */
    private final TransactionContextInternal context;

    /**
     * Creates a transaction interface, starting a new transaction.
     *
     * @param readOnly Whether the transaction is read-only or not.
     */
    public TransactionManagerImpl(boolean readOnly) {
        final TransactionOptions transactionOptions = TransactionOptions.Builder.withDefaults();
        if (readOnly) {
            transactionOptions.setTransactionMode(Mode.READ_ONLY);
        }

        transaction = datastoreService.beginTransaction(transactionOptions);
        context = new TransactionContextImpl();
    }

    @Override
    public TransactionContext getContext() {
        return context;
    }

    @Override
    public void commit() {
        try {
            logger.info("Persisting entities: " + context.getEntitiesToPersist());
            datastoreService.put(context.getEntitiesToPersist());
            logger.info("Entities successfully persisted.");

            logger.info("Forgetting entities with keys: " + context.getKeysToForget());
            datastoreService.delete(context.getKeysToForget());
            logger.info("Entities successfully forgetted.");

            transaction.commit();
            logger.info("Changes successfully committed.");
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            context.clear();
            logger.info("Context cleared.");
        }
    }

    @Override
    public void rollback() {
        transaction.rollback();
        context.clear();
    }

    @Override
    public Future<Void> commitAsync() {
        throw new UnsupportedOperationException("Asynchronous commits disabled.");
    }

    @Override
    public Future<Void> rollbackAsync() {
        throw new UnsupportedOperationException("Asynchronous rollbacks disabled.");
    }

    @Override
    public String getId() {
        return transaction.getId();
    }

    @Override
    public String getApp() {
        return transaction.getApp();
    }

    @Override
    public boolean isActive() {
        return transaction.isActive();
    }
}
