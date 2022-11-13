package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public interface UserRepository {

    UserEntity getCurrentUser();

    void setCurrentUser(UserEntity userEntity);

    UserEntity register(User user, String name, String image);

    UserEntity get(Key key) throws EntityNotFoundException;

    UserEntity find(User user);

    UserEntity find(String userId);
}
