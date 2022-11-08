package tinygram.data;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public interface UserRepository {

    UserEntity getCurrentUser();

    UserEntity register(User user);

    UserEntity get(Key key) throws EntityNotFoundException;

    UserEntity find(User user);

    UserEntity find(String userId);
}
