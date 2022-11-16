package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class BaseUserRepository implements UserRepository {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private UserProvider userProvider;

    public BaseUserRepository() {
        userProvider = new UndefinedUserProvider();
    }

    public BaseUserRepository(UserEntity userEntity) {
        setCurrentUser(userEntity);
    }

    @Override
    public UserEntity getCurrentUser() {
        return userProvider.exists() ? userProvider.get() : null;
    }

    @Override
    public void setCurrentUser(UserEntity userEntity) {
        userProvider = new BaseUserProvider(userEntity);
    }

    @Override
    public UserEntity register(User user, String name, String image) {
        return new BaseUserEntity(user, name, image);
    }

    @Override
    public UserEntity get(Key key) throws EntityNotFoundException {
        return new BaseUserEntity(userProvider, datastore.get(key));
    }

    @Override
    public UserEntity find(User user) {
        return find(user.getId());
    }

    @Override
    public UserEntity find(String userId) {
        final Query query = new Query(UserEntity.KIND)
                .setFilter(new FilterPredicate(UserEntity.FIELD_ID, FilterOperator.EQUAL, userId));

        final Entity raw = datastore.prepare(query).asSingleEntity();
        return raw == null ? null : new BaseUserEntity(userProvider, raw);
    }
}
