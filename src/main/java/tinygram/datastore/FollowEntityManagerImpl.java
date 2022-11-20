package tinygram.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import tinygram.util.IteratorMapper;

class FollowEntityManagerImpl implements FollowEntityManager {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public FollowEntity register(UserEntity source, UserEntity target) {
        return new FollowEntityImpl(source, target);
    }

    @Override
    public FollowEntity get(Key key) throws EntityNotFoundException {
        return new FollowEntityImpl(datastore.get(key));
    }

    private FollowEntity unsafeGet(Key key) {
        try {
            return get(key);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public FollowEntity find(Key sourceKey, Key targetKey) {
        final Collection<Filter> subfilters = new ArrayList<>(2);
        subfilters.add(new FilterPredicate(FollowEntity.PROPERTY_SOURCE.getName(), FilterOperator.EQUAL, sourceKey));
        subfilters.add(new FilterPredicate(FollowEntity.PROPERTY_TARGET.getName(), FilterOperator.EQUAL, targetKey));

        final Filter filter = new CompositeFilter(CompositeFilterOperator.AND, subfilters);
        final Query query = new Query(FollowEntity.KIND).setFilter(filter);

        final Entity raw = datastore.prepare(query).asSingleEntity();
        return raw == null ? null : new FollowEntityImpl(raw);
    }

    @Override
    public Iterator<FollowEntity> findAllFrom(Key userKey) {
        final Filter filter = new FilterPredicate(FollowEntity.PROPERTY_SOURCE.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(FollowEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, raw -> unsafeGet(raw.getKey()));
    }

    @Override
    public Iterator<FollowEntity> findAllTo(Key userKey) {
        final Filter filter = new FilterPredicate(FollowEntity.PROPERTY_TARGET.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(FollowEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, raw -> unsafeGet(raw.getKey()));
    }
}
