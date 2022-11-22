package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

public interface UserEntityManager extends TypedEntityManager<UserEntity> {

    static UserEntityManager get(TransactionContext context) {
        return new UserEntityManagerImpl(context);
    }

    UserEntity register(User user, String name, String image);

    UserEntity find(User user);

    UserEntity find(String userId);
}
