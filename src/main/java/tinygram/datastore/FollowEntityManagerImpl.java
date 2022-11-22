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
import tinygram.util.IteratorMapper;

class FollowEntityManagerImpl implements FollowEntityManager {

    private final TransactionContext context;

    public FollowEntityManagerImpl(TransactionContext context) {
        this.context = context;
    }

    @Override
    public FollowEntity register(UserEntity source, UserEntity target) {
        final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get(context);
        final PostEntityManager postManager = PostEntityManager.get(context);

        final FollowEntityImpl follow = new FollowEntityImpl(source, target);

        // Add the last target user post to the source user feed.
        final PostEntity latestPost = postManager.findLatest(target);
        if (latestPost != null) {
            final FeedNodeEntity feedNode = feedManager.register(source, latestPost);
            follow.addRelatedEntity(feedNode);
        }

        return follow;
    }

    @Override
    public FollowEntity get(Key key) throws EntityNotFoundException {
        return new FollowEntityImpl(context.get(key));
    }

    @Override
    public FollowEntity find(Key sourceKey, Key targetKey) {
        final Collection<Filter> subfilters = new ArrayList<>(2);
        subfilters.add(new FilterPredicate(FollowEntity.PROPERTY_SOURCE.getName(), FilterOperator.EQUAL, sourceKey));
        subfilters.add(new FilterPredicate(FollowEntity.PROPERTY_TARGET.getName(), FilterOperator.EQUAL, targetKey));

        final Filter filter = new CompositeFilter(CompositeFilterOperator.AND, subfilters);
        final Query query = new Query(FollowEntity.KIND).setFilter(filter);

        final Entity raw = context.find(query);
        return raw == null ? null : new FollowEntityImpl(raw);
    }

    @Override
    public Iterator<FollowEntity> findAllFrom(Key userKey) {
        final Filter filter = new FilterPredicate(FollowEntity.PROPERTY_SOURCE.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(FollowEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = context.findAll(query);
        return new IteratorMapper<>(iterator, FollowEntityImpl::new);
    }

    @Override
    public Iterator<FollowEntity> findAllTo(Key userKey) {
        final Filter filter = new FilterPredicate(FollowEntity.PROPERTY_TARGET.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(FollowEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = context.findAll(query);
        return new IteratorMapper<>(iterator, FollowEntityImpl::new);
    }
}
