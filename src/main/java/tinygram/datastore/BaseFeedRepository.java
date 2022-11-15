package tinygram.datastore;

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

public class BaseFeedRepository implements FeedRepository {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public FeedNodeEntity register(Key userKey, PostEntity post) {
        return new BaseFeedNodeEntity(userKey, post);
    }

    @Override
    public FeedNodeEntity get(Key key) throws EntityNotFoundException {
        return new BaseFeedNodeEntity(datastore.get(key));
    }

    private FetchOptions getFeedOptions(String page) {
        final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(Config.FEED_LIMIT);

        if (page != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(page));
        }

        return fetchOptions;
    }

    @Override
    public Feed findAll(String page) {
        final PreparedQuery query = datastore.prepare(new Query(PostEntity.KIND)
                .addSort(PostEntity.FIELD_DATE, SortDirection.DESCENDING)
                .setKeysOnly());

        final QueryResultList<Entity> result = query.asQueryResultList(getFeedOptions(page));

        return new BaseFeed(Entity::getKey, result);
    }

    @Override
    public Feed findAll(Key userKey, String page) {
        final PreparedQuery query = datastore.prepare(new Query(FeedNodeEntity.KIND)
                .setFilter(new FilterPredicate(FeedNodeEntity.FIELD_USER, FilterOperator.EQUAL, userKey))
                .addSort(FeedNodeEntity.FIELD_DATE, SortDirection.DESCENDING));

        final QueryResultList<Entity> result = query.asQueryResultList(getFeedOptions(page));

        return new BaseFeed(raw -> new BaseFeedNodeEntity(raw).getPost(), result, userKey, true);
    }

    @Override
    public Feed findAllFrom(Key userKey, String page) {
        final PreparedQuery query = datastore.prepare(new Query(PostEntity.KIND)
                .setFilter(new FilterPredicate(PostEntity.FIELD_OWNER, FilterOperator.EQUAL, userKey))
                .addSort(PostEntity.FIELD_DATE, SortDirection.DESCENDING)
                .setKeysOnly());

        final QueryResultList<Entity> result = query.asQueryResultList(getFeedOptions(page));

        return new BaseFeed(Entity::getKey, result, userKey);
    }
}