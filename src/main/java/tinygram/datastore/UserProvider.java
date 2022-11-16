package tinygram.datastore;

import java.util.function.Supplier;

import com.google.appengine.api.datastore.Key;

@FunctionalInterface
public interface UserProvider extends Supplier<UserEntity> {

    default Key getKey() {
        return get().getKey();
    }

    default boolean exists() {
        try {
            return get() != null;
        } catch (final IllegalStateException e) {
            return false;
        }
    }
}
