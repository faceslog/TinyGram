package tinygram.datastore;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public class BasePostRepository implements PostRepository {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private final UserProvider userProvider;

    public BasePostRepository(UserRepository userRepository) {
        this.userProvider = new BaseUserProvider(userRepository.getCurrentUser());
    }

    @Override
    public PostEntity register(UserEntity owner, String image, String description) {
        return new BasePostEntity(userProvider, owner, image, description);
    }

    @Override
    public PostEntity get(Key key) throws EntityNotFoundException {
        return new BasePostEntity(userProvider, datastore.get(key));
    }

    /*private FetchOptions getFeedOptions(String page) {
        final FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10);

        if (page != null) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(page));
        }

        return fetchOptions;
    }

    @Override
    public FeedResponse<PostEntity> findFrom(UserEntity user, String page) {
        final PreparedQuery query = datastore.prepare(new Query("Post")
                .setFilter(new FilterPredicate("owner", FilterOperator.EQUAL, user.getKey())));

        return new FeedResponse<>(entity -> new BasePostEntity(userProvider, entity), query.asQueryResultList(getFeedOptions(page)));
    }

    @Override
    public FeedResponse<PostEntity> findFromFollowed(UserEntity user, String page) {
        final PreparedQuery query = datastore.prepare(new Query("Post")
                .setFilter(new FilterPredicate("owner", FilterOperator.IN, user.getFollowing())));

        return new FeedResponse<>(entity -> new BasePostEntity(userProvider, entity), query.asQueryResultList(getFeedOptions(page)));
    }*/
}
