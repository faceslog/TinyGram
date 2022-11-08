package tinygram.data;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import tinygram.Util;

public class BaseUserRepository implements UserRepository {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private UserProvider userProvider;

    public BaseUserRepository(User user) {
        if (user == null && !Util.DEBUG) {
            throw new IllegalArgumentException("Missing user credentials.");
        }

        userProvider = new UndefinedUserProvider();

        UserEntity entity = find(user);

        if (entity == null) {
            entity = register(user);
        }

        userProvider = new BaseUserProvider(entity);
    }

    @Override
    public UserEntity getCurrentUser() {
        return userProvider.get();
    }

    @Override
    public UserEntity register(User user) {
        final UserEntity entity = new BaseUserEntity(user);

        Util.withinTransaction(entity::persist);

        return entity;
    }

    @Override
    public UserEntity get(Key key) throws EntityNotFoundException {
        return new BaseUserEntity(userProvider, datastore.get(key));
    }

    @Override
    public UserEntity find(User user) {
        return find(Util.DEBUG ? "test" : user.getId());
    }

    @Override
    public UserEntity find(String userId) {
        final Query query = new Query(UserEntity.KIND)
                .setFilter(new FilterPredicate(UserEntity.FIELD_ID, FilterOperator.EQUAL, userId));

        final Entity raw = datastore.prepare(query).asSingleEntity();
        return raw == null ? null : new BaseUserEntity(userProvider, raw);
    }
}
