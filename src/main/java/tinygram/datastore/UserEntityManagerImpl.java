package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

class UserEntityManagerImpl implements UserEntityManager {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public UserEntity register(User user, String name, String image) {
        return new UserEntityImpl(user, name, image);
    }

    @Override
    public UserEntity get(Key key) throws EntityNotFoundException {
        return new UserEntityImpl(datastore.get(key));
    }

    @Override
    public UserEntity find(User user) {
        return find(user.getId());
    }

    @Override
    public UserEntity find(String userId) {
        final Filter filter = new FilterPredicate(UserEntity.PROPERTY_ID.getName(), FilterOperator.EQUAL, userId);
        final Query query = new Query(UserEntity.KIND).setFilter(filter);

        final Entity raw = datastore.prepare(query).asSingleEntity();
        return raw == null ? null : new UserEntityImpl(raw);
    }
}
