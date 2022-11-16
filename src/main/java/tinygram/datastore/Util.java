package tinygram.datastore;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

public class Util {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static <T> void withinTransaction(Consumer<T> action, T arg) {
        final Transaction transaction = datastore.beginTransaction();
        action.accept(arg);
        transaction.commit();
    }

    public static <T, U> U withinTransaction(Function<T, U> action, T arg) {
        final Transaction transaction = datastore.beginTransaction();
        final U result = action.apply(arg);
        transaction.commit();
        return result;
    }

    public static <T> T withinTransaction(Supplier<T> action) {
        final Transaction transaction = datastore.beginTransaction();
        final T result = action.get();
        transaction.commit();
        return result;
    }

    public static void withinTransaction(Runnable action) {
        final Transaction transaction = datastore.beginTransaction();
        action.run();
        transaction.commit();
    }

    @SuppressWarnings("unchecked")
    public static <T> T extractProperty(Entity entity, String propertyName) {
        return (T) entity.getProperty(propertyName);
    }

    private Util() {}
}
