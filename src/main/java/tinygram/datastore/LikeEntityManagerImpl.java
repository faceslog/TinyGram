package tinygram.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import tinygram.datastore.util.TransactionContext;
import tinygram.util.IteratorUtils;

/**
 * An implementation of the {@link LikeEntityManager} interface.
 */
class LikeEntityManagerImpl implements LikeEntityManager {

    /**
     * The current transaction context.
     */
    private final TransactionContext context;

    /**
     * Creates a like entity interface.
     *
     * @param context The transaction context.
     */
    public LikeEntityManagerImpl(TransactionContext context) {
        this.context = context;
    }

    @Override
    public LikeEntity register(UserEntity user, PostEntity post) {
        return new LikeEntityImpl(user, post);
    }

    @Override
    public LikeEntity get(Key key) throws EntityNotFoundException {
        return new LikeEntityImpl(context.get(key));
    }

    @Override
    public LikeEntity find(Key userKey, Key postKey) {
        final Collection<Filter> subfilters = new ArrayList<>(2);
        subfilters.add(new FilterPredicate(LikeEntity.PROPERTY_USER.getName(), FilterOperator.EQUAL, userKey));
        subfilters.add(new FilterPredicate(LikeEntity.PROPERTY_POST.getName(), FilterOperator.EQUAL, postKey));

        final Filter filter = new CompositeFilter(CompositeFilterOperator.AND, subfilters);
        final Query query = new Query(LikeEntity.KIND).setFilter(filter);

        final Entity raw = context.find(query);
        return raw == null ? null : new LikeEntityImpl(raw);
    }

    @Override
    public Iterator<LikeEntity> findAllFrom(Key userKey) {
        final Filter filter = new FilterPredicate(LikeEntity.PROPERTY_USER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(LikeEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = context.findAll(query);
        return IteratorUtils.map(iterator, LikeEntityImpl::new);
    }

    @Override
    public Iterator<LikeEntity> findAllTo(Key postKey) {
        final Filter filter = new FilterPredicate(LikeEntity.PROPERTY_POST.getName(), FilterOperator.EQUAL, postKey);
        final Query query = new Query(LikeEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = context.findAll(query);
        return IteratorUtils.map(iterator, LikeEntityImpl::new);
    }
}
