package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import tinygram.util.IteratorMapper;

class PostEntityManagerImpl implements PostEntityManager {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public PostEntity register(UserEntity owner, String image, String description) {
        return new PostEntityImpl(owner, image, description);
    }

    @Override
    public PostEntity get(Key key) throws EntityNotFoundException {
        return new PostEntityImpl(datastore.get(key));
    }

    @Override
    public PostEntity findLatest(Key userKey) {
        final Filter filter = new FilterPredicate(PostEntity.PROPERTY_OWNER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(PostEntity.KIND).setFilter(filter)
                .addSort(PostEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return iterator.hasNext() ? new PostEntityImpl(iterator.next()) : null;
    }

    @Override
    public Iterator<PostEntity> findAll(Key userKey) {
        final Filter filter = new FilterPredicate(PostEntity.PROPERTY_OWNER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(PostEntity.KIND).setFilter(filter)
                .addSort(PostEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, PostEntityImpl::new);
    }
}
