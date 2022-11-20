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

class LikeEntityManagerImpl implements LikeEntityManager {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public LikeEntity register(UserEntity user, PostEntity post) {
        return new LikeEntityImpl(user, post);
    }

    @Override
    public LikeEntity get(Key key) throws EntityNotFoundException {
        return new LikeEntityImpl(datastore.get(key));
    }

    private LikeEntity unsafeGet(Key key) {
        try {
            return get(key);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public LikeEntity find(Key userKey, Key postKey) {
        final Collection<Filter> subfilters = new ArrayList<>(2);
        subfilters.add(new FilterPredicate(LikeEntity.PROPERTY_USER.getName(), FilterOperator.EQUAL, userKey));
        subfilters.add(new FilterPredicate(LikeEntity.PROPERTY_POST.getName(), FilterOperator.EQUAL, postKey));

        final Filter filter = new CompositeFilter(CompositeFilterOperator.AND, subfilters);
        final Query query = new Query(LikeEntity.KIND).setFilter(filter);

        final Entity raw = datastore.prepare(query).asSingleEntity();
        return raw == null ? null : new LikeEntityImpl(raw);
    }

    @Override
    public Iterator<LikeEntity> findAllFrom(Key userKey) {
        final Filter filter = new FilterPredicate(LikeEntity.PROPERTY_USER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(LikeEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, raw -> unsafeGet(raw.getKey()));
    }

    @Override
    public Iterator<LikeEntity> findAllTo(Key postKey) {
        final Filter filter = new FilterPredicate(LikeEntity.PROPERTY_POST.getName(), FilterOperator.EQUAL, postKey);
        final Query query = new Query(LikeEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, raw -> unsafeGet(raw.getKey()));
    }
}
