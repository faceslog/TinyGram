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
        final Collection<Filter> filters = new ArrayList<>(2);
        filters.add(new FilterPredicate(FollowEntity.FIELD_SOURCE, FilterOperator.EQUAL, sourceKey));
        filters.add(new FilterPredicate(FollowEntity.FIELD_TARGET, FilterOperator.EQUAL, targetKey));

        final Query query = new Query(FollowEntity.KIND)
                .setFilter(new CompositeFilter(CompositeFilterOperator.AND, filters));

        final Entity raw = datastore.prepare(query).asSingleEntity();
        return raw == null ? null : new FollowEntityImpl(raw);
    }

    @Override
    public Iterator<FollowEntity> findAllFrom(Key userKey) {
        final Query query = new Query(FollowEntity.KIND)
                .setFilter(new FilterPredicate(FollowEntity.FIELD_SOURCE, FilterOperator.EQUAL, userKey));

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, raw -> unsafeGet(raw.getKey()));
    }

    @Override
    public Iterator<FollowEntity> findAllTo(Key userKey) {
        final Query query = new Query(FollowEntity.KIND)
                .setFilter(new FilterPredicate(FollowEntity.FIELD_TARGET, FilterOperator.EQUAL, userKey));

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, raw -> unsafeGet(raw.getKey()));
    }
}
