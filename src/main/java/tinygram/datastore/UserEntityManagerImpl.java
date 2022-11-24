package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import tinygram.datastore.util.TransactionContext;

/**
 * An implementation of the {@link UserEntityManager} interface.
 */
class UserEntityManagerImpl implements UserEntityManager {

    /**
     * The current transaction context.
     */
    private final TransactionContext context;

    /**
     * Creates a user entity interface.
     *
     * @param context The transaction context.
     */
    public UserEntityManagerImpl(TransactionContext context) {
        this.context = context;
    }

    @Override
    public UserEntity register(User user, String name, String image) {
        return new UserEntityImpl(user, name, image);
    }

    @Override
    public UserEntity get(Key key) throws EntityNotFoundException {
        return new UserEntityImpl(context.get(key));
    }

    @Override
    public UserEntity find(User user) {
        return find(user.getId());
    }

    @Override
    public UserEntity find(String userId) {
        final Filter filter = new FilterPredicate(UserEntity.PROPERTY_ID.getName(), FilterOperator.EQUAL, userId);
        final Query query = new Query(UserEntity.KIND).setFilter(filter);

        final Entity raw = context.find(query);
        return raw == null ? null : new UserEntityImpl(raw);
    }
}
