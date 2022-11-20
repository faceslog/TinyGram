package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import tinygram.Config;
import tinygram.util.IteratorMapper;

class FeedNodeEntityManagerImpl implements FeedNodeEntityManager {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public FeedNodeEntity register(Key userKey, PostEntity post) {
        return new FeedNodeEntityImpl(userKey, post);
    }

    @Override
    public FeedNodeEntity get(Key key) throws EntityNotFoundException {
        return new FeedNodeEntityImpl(datastore.get(key));
    }

    @Override
    public Iterator<FeedNodeEntity> findAllOfUser(Key userKey) {
        final Query query = new Query(FeedNodeEntity.KIND)
                .setFilter(new FilterPredicate(FeedNodeEntity.FIELD_USER, FilterOperator.EQUAL, userKey))
                .addSort(FeedNodeEntity.FIELD_DATE, SortDirection.DESCENDING);

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, FeedNodeEntityImpl::new);
    }

    @Override
    public Iterator<FeedNodeEntity> findAllOfPost(Key postKey) {
        final Query query = new Query(FeedNodeEntity.KIND)
                .setFilter(new FilterPredicate(FeedNodeEntity.FIELD_POST, FilterOperator.EQUAL, postKey));

        final Iterator<Entity> iterator = datastore.prepare(query).asIterator();
        return new IteratorMapper<>(iterator, FeedNodeEntityImpl::new);
    }

    private FetchOptions getFeedOptions(String page) {
        final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(Config.FEED_LIMIT);

        if (page != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(page));
        }

        return fetchOptions;
    }

    @Override
    public Feed findPaged(String page) {
        final PreparedQuery query = datastore.prepare(new Query(PostEntity.KIND)
                .addSort(PostEntity.FIELD_DATE, SortDirection.DESCENDING)
                .setKeysOnly());

        final QueryResultList<Entity> result = query.asQueryResultList(getFeedOptions(page));

        return new FeedImpl(Entity::getKey, result);
    }

    @Override
    public Feed findPaged(Key userKey, String page) {
        final PreparedQuery query = datastore.prepare(new Query(FeedNodeEntity.KIND)
                .setFilter(new FilterPredicate(FeedNodeEntity.FIELD_USER, FilterOperator.EQUAL, userKey))
                .addSort(FeedNodeEntity.FIELD_DATE, SortDirection.DESCENDING));

        final QueryResultList<Entity> result = query.asQueryResultList(getFeedOptions(page));

        return new FeedImpl(raw -> new FeedNodeEntityImpl(raw).getPost(), result, userKey, true);
    }

    @Override
    public Feed findPagedFrom(Key userKey, String page) {
        final PreparedQuery query = datastore.prepare(new Query(PostEntity.KIND)
                .setFilter(new FilterPredicate(PostEntity.FIELD_OWNER, FilterOperator.EQUAL, userKey))
                .addSort(PostEntity.FIELD_DATE, SortDirection.DESCENDING)
                .setKeysOnly());

        final QueryResultList<Entity> result = query.asQueryResultList(getFeedOptions(page));

        return new FeedImpl(Entity::getKey, result, userKey);
    }
}
