package tinygram.datastore;

import java.util.Iterator;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Key;

public interface UserRepository extends Repository<UserEntity> {

    UserEntity getCurrentUser();

    void setCurrentUser(UserEntity userEntity);

    UserEntity register(User user, String name, String image);

    UserEntity find(User user);

    UserEntity find(String userId);

    default Iterator<UserEntity> findAllFollowed(UserEntity user) {
        return findAllFollowed(user.getKey());
    }

    Iterator<UserEntity> findAllFollowed(Key userKey);
}
