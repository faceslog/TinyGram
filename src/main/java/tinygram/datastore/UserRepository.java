package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;

public interface UserRepository extends Repository<UserEntity> {

    UserEntity getCurrentUser();

    void setCurrentUser(UserEntity userEntity);

    UserEntity register(User user, String name, String image);

    UserEntity find(User user);

    UserEntity find(String userId);
}
