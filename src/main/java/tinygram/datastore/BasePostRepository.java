package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import tinygram.util.IteratorWrapper;

public class BasePostRepository implements PostRepository {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private final UserProvider userProvider;

    public BasePostRepository(UserRepository userRepository) {
        this(new BaseUserProvider(userRepository.getCurrentUser()));
    }

    public BasePostRepository(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public PostEntity register(UserEntity owner, String image, String description) {
        return new BasePostEntity(userProvider, owner, image, description);
    }

    @Override
    public PostEntity get(Key key) throws EntityNotFoundException {
        return new BasePostEntity(userProvider, datastore.get(key));
    }

    @Override
    public PostEntity findLatest(Key userKey) {
        final Query query = new Query(PostEntity.KIND)
                .setFilter(new FilterPredicate(PostEntity.FIELD_OWNER, FilterOperator.EQUAL, userKey))
                .addSort(PostEntity.FIELD_DATE, SortDirection.DESCENDING);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return iterator.hasNext() ? new BasePostEntity(userProvider, iterator.next()) : null;
    }

    @Override
    public Iterator<PostEntity> findAll(Key userKey) {
        final Query query = new Query(PostEntity.KIND)
                .setFilter(new FilterPredicate(PostEntity.FIELD_OWNER, FilterOperator.EQUAL, userKey))
                .addSort(PostEntity.FIELD_DATE, SortDirection.DESCENDING);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorWrapper<>(iterator, raw -> new BasePostEntity(userProvider, raw));
    }
}
