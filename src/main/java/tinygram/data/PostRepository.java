package tinygram.data;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public final class PostRepository {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static PostEntity get(Key key) throws EntityNotFoundException {
        return new PostEntity(datastore.get(key));
    }

    private static FetchOptions getFeedOptions(String page) {
        final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10);

        if (page != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(page));
        }

        return fetchOptions;
    }

    public static FeedResponse<PostEntity> findFrom(UserEntity user, String page) {
        final PreparedQuery query = datastore.prepare(new Query("Post")
                .setFilter(new FilterPredicate("owner", FilterOperator.EQUAL, user.getKey())));

        return new FeedResponse<>(PostEntity::new, query.asQueryResultList(getFeedOptions(page)));
    }

    public static FeedResponse<PostEntity> findFromFollowed(UserEntity user, String page) {
        final PreparedQuery query = datastore.prepare(new Query("Post")
                .setFilter(new FilterPredicate("owner", FilterOperator.IN, user.getFollowing())));

        return new FeedResponse<>(PostEntity::new, query.asQueryResultList(getFeedOptions(page)));
    }
}
