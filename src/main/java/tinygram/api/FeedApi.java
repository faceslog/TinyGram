package tinygram.api;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;

import tinygram.datastore.Feed;
import tinygram.datastore.FeedNodeEntityManager;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TransactionManager;

/**
 * The Tinygram feed API.
 */
@ApiReference(InstApi.class)
public class FeedApi {

    private static final Logger logger = Logger.getLogger(PostApi.class.getName());

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.global",
        path       = FeedApiSchema.RELATIVE_GLOBAL_PATH,
        httpMethod = HttpMethod.GET)
    public FeedResource globalFeed() {
        return globalPagedFeed(null);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.global",
        path       = FeedApiSchema.RELATIVE_GLOBAL_PATH + FeedApiSchema.PAGE_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public FeedResource globalPagedFeed(@Named(FeedApiSchema.PAGE_ARGUMENT_NAME) String page) {
        final TransactionManager transactionManager = TransactionManager.beginReadOnly();
        final TransactionContext context = transactionManager.getContext();
        final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get(context);

        logger.info("Retrieving feed...");
        final Feed feed = feedManager.findPaged(page);
        logger.info("Feed successfully retrieved.");

        transactionManager.commit();

        return new FeedResource(feed);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.followed",
        path       = FeedApiSchema.RELATIVE_FOLLOWED_PATH,
        httpMethod = HttpMethod.GET)
    public FeedResource followedFeed(User user) throws UnauthorizedException {
        return followedPagedFeed(user, null);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.followed",
        path       = FeedApiSchema.RELATIVE_FOLLOWED_PATH + FeedApiSchema.PAGE_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public FeedResource followedPagedFeed(User user,
                                          @Named(FeedApiSchema.PAGE_ARGUMENT_NAME) String page)
                throws UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.beginReadOnly();
        final TransactionContext context = transactionManager.getContext();
        final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get(context);

        final UserEntity currentUser = UserApi.getCurrentUser(context, user);

        logger.info("Retrieving user feed...");
        final Feed feed = feedManager.findPaged(currentUser.getKey(), page);
        logger.info("User feed successfully retrieved.");

        transactionManager.commit();

        return new FeedResource(feed);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.from",
        path       = FeedApiSchema.RELATIVE_FROM_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public FeedResource fromFeed(@Named(UserApiSchema.KEY_ARGUMENT_NAME) String userKey) {
        return fromPagedFeed(userKey, null);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.from",
        path       = FeedApiSchema.RELATIVE_FROM_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX +
                     FeedApiSchema.PAGE_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public FeedResource fromPagedFeed(@Named(UserApiSchema.KEY_ARGUMENT_NAME) String userKey,
                                      @Named(FeedApiSchema.PAGE_ARGUMENT_NAME) String page) {
        final TransactionManager transactionManager = TransactionManager.beginReadOnly();
        final TransactionContext context = transactionManager.getContext();
        final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get(context);

        logger.info("Retrieving user posts...");
        final Feed feed = feedManager.findPagedFrom(KeyFactory.stringToKey(userKey), page);
        logger.info("User posts successfully retrieved.");

        transactionManager.commit();

        return new FeedResource(feed);
    }
}
