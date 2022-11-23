package tinygram.datastore.util;

import com.google.appengine.api.datastore.Transaction;

/**
 * A interface to manage a specific transaction. Provides additional optimizations and checks on top
 * of a base Google transaction.
 */
public interface TransactionManager extends Transaction {

    /**
     * Begins a read-write transaction against the datastore.
     *
     * @return An interface for managing the newly started transaction.
     */
    static TransactionManager begin() {
        TransactionManagerImpl.setCurrent(new TransactionManagerImpl(false));
        return TransactionManagerImpl.getCurrent();
    }

    /**
     * Begins a read-only transaction against the datastore.
     *
     * @return An interface for managing the newly started transaction.
     */
    static TransactionManager beginReadOnly() {
        TransactionManagerImpl.setCurrent(new TransactionManagerImpl(true));
        return TransactionManagerImpl.getCurrent();
    }

    /**
     * Gets the context associated to the transaction.
     *
     * @return The transaction context.
     */
    TransactionContext getContext();
}
