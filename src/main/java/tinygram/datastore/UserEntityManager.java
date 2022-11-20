package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;

public interface UserEntityManager extends TypedEntityManager<UserEntity> {

    static UserEntityManager get() {
        return new UserEntityManagerImpl();
    }

    UserEntity register(User user, String name, String image);

    UserEntity find(User user);

    UserEntity find(String userId);
}
