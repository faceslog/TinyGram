package tinygram.datastore.util;

import com.google.appengine.api.datastore.Transaction;

public interface TransactionManager extends Transaction {

    static TransactionManager begin() {
        TransactionManagerImpl.setCurrent(new TransactionManagerImpl(false));
        return TransactionManagerImpl.getCurrent();
    }

    static TransactionManager beginReadOnly() {
        TransactionManagerImpl.setCurrent(new TransactionManagerImpl(true));
        return TransactionManagerImpl.getCurrent();
    }

    TransactionContext getContext();
}
