package tinygram.datastore;

import java.util.Collections;
import java.util.function.Supplier;

public interface TransactionManager {

    public static TransactionManager get() {
        return new TransactionManagerImpl();
    }

    default void persist(Supplier<TypedEntity> supplier) {
        persist(supplier.get());
    }

    default void persist(TypedEntity entity) {
        persist(Collections.singleton(entity));
    }

    <T extends TypedEntity> void persist(Iterable<T> entities);

    default void forget(Supplier<TypedEntity> supplier) {
        forget(supplier.get());
    }

    default void forget(TypedEntity entity) {
        forget(Collections.singleton(entity));
    }

    <T extends TypedEntity> void forget(Iterable<T> entities);
}
