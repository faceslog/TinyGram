package tinygram.data;

import java.util.function.Supplier;

import com.google.appengine.api.datastore.Key;

public interface UserProvider extends Supplier<UserEntity> {

    default Key getKey() {
        return get().getKey();
    }
}
