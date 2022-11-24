package tinygram.datastore;

import java.util.Arrays;
import java.util.Iterator;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import tinygram.Config;
import tinygram.datastore.util.TransactionContext;
import tinygram.util.IteratorUtils;

/**
 * An implementation of the {@link FeedNodeEntityManager} interface.
 */
class FeedNodeEntityManagerImpl implements FeedNodeEntityManager {

    private static final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    /**
     * The current transaction context.
     */
    private final TransactionContext context;

    /**
     * Creates a feed node entity interface.
     *
     * @param context The transaction context.
     */
    public FeedNodeEntityManagerImpl(TransactionContext context) {
        this.context = context;
    }

    @Override
    public FeedNodeEntity register(Key userKey, PostEntity post) {
        return new FeedNodeEntityImpl(userKey, post);
    }

    @Override
    public FeedNodeEntity get(Key key) throws EntityNotFoundException {
        return new FeedNodeEntityImpl(context.get(key));
    }

    @Override
    public Iterator<FeedNodeEntity> findAllOfUser(Key userKey) {
        final Filter filter = new FilterPredicate(FeedNodeEntity.PROPERTY_USER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(FeedNodeEntity.KIND).setFilter(filter)
                .addSort(FeedNodeEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final Iterator<Entity> iterator = context.findAll(query);
        return IteratorUtils.map(iterator, FeedNodeEntityImpl::new);
    }

    @Override
    public Iterator<FeedNodeEntity> findAllOfPost(Key postKey) {
        final Filter filter = new FilterPredicate(FeedNodeEntity.PROPERTY_POST.getName(), FilterOperator.EQUAL, postKey);
        final Query query = new Query(FeedNodeEntity.KIND).setFilter(filter);

        final Iterator<Entity> iterator = context.findAll(query);
        return IteratorUtils.map(iterator, FeedNodeEntityImpl::new);
    }

    /**
     * Gets the default fetching options to use with a feed request.
     *
     * @param page The feed page token.
     *
     * @return The default fetching options starting at <b>page</b>.
     */
    private FetchOptions getFeedOptions(String page) {
        final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(Config.FEED_LIMIT);

        if (page != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(page));
        }

        return fetchOptions;
    }

    @Override
    public Feed findPaged(String page) {
        final Query query = new Query(PostEntity.KIND).setKeysOnly()
                .addSort(PostEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final QueryResultList<Entity> result = datastoreService.prepare(query).asQueryResultList(getFeedOptions(page));

        return new FeedImpl(Entity::getKey, result);
    }

    @Override
    public Feed findPaged(Key userKey, String page) {
        final Filter filter = new FilterPredicate(FeedNodeEntity.PROPERTY_USER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(FeedNodeEntity.KIND).setFilter(filter)
                .addSort(FeedNodeEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final QueryResultList<Entity> result = datastoreService.prepare(query).asQueryResultList(getFeedOptions(page));

        return new FeedImpl(raw -> new FeedNodeEntityImpl(raw).getPost(), result, userKey, true);
    }

    @Override
    public Feed findPagedFrom(Key userKey, String page) {
        final Filter filter = new FilterPredicate(PostEntity.PROPERTY_OWNER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(PostEntity.KIND).setKeysOnly().setFilter(filter)
                .addSort(PostEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final QueryResultList<Entity> result = datastoreService.prepare(query).asQueryResultList(getFeedOptions(page));

        return new FeedImpl(Entity::getKey, result, userKey);
    }
}
