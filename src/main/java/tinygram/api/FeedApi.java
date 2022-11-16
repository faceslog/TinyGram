package tinygram.api;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;

import tinygram.datastore.BaseFeedRepository;
import tinygram.datastore.Feed;
import tinygram.datastore.FeedRepository;
import tinygram.datastore.UserRepository;

@ApiReference(InstApi.class)
public class FeedApi {

    private static final Logger log = Logger.getLogger(PostApi.class.getName());

    public static FeedRepository buildRepository() {
        return new BaseFeedRepository();
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.global",
        path       = FeedApiSchema.RELATIVE_GLOBAL_PATH,
        httpMethod = HttpMethod.GET)
    public Feed globalFeed(User user) {
        return globalPagedFeed(user, null);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.global",
        path       = FeedApiSchema.RELATIVE_GLOBAL_PATH + FeedApiSchema.PAGE_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public Feed globalPagedFeed(User user, @Named(FeedApiSchema.PAGE_ARGUMENT_NAME) String page) {
        final FeedRepository feedRepository = buildRepository();

        log.info("Retrieving feed...");
        final Feed feed = feedRepository.findPaged(page);
        log.info("Feed successfully retrieved.");

        return feed;
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.followed",
        path       = FeedApiSchema.RELATIVE_FOLLOWED_PATH,
        httpMethod = HttpMethod.GET)
    public Feed followedFeed(User user) throws UnauthorizedException {
        return followedPagedFeed(user, null);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.followed",
        path       = FeedApiSchema.RELATIVE_FOLLOWED_PATH + FeedApiSchema.PAGE_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public Feed followedPagedFeed(User user, @Named(FeedApiSchema.PAGE_ARGUMENT_NAME) String page) throws UnauthorizedException {
        final UserRepository userRepository = UserApi.buildRepository(user);
        final FeedRepository feedRepository = buildRepository();

        log.info("Retrieving user feed...");
        final Feed feed = feedRepository.findPaged(userRepository.getCurrentUser().getKey(), page);
        log.info("User feed successfully retrieved.");

        return feed;
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.from",
        path       = FeedApiSchema.RELATIVE_FROM_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public Feed fromFeed(@Named(UserApiSchema.KEY_ARGUMENT_NAME) String userKey) {
        return fromPagedFeed(userKey, null);
    }

    @ApiMethod(
        name       = FeedApiSchema.RESOURCE_NAME + ".get.from",
        path       = FeedApiSchema.RELATIVE_FROM_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX + FeedApiSchema.PAGE_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public Feed fromPagedFeed(@Named(UserApiSchema.KEY_ARGUMENT_NAME) String userKey, @Named(FeedApiSchema.PAGE_ARGUMENT_NAME) String page) {
        final FeedRepository feedRepository = buildRepository();

        log.info("Retrieving user posts...");
        final Feed feed = feedRepository.findPagedFrom(KeyFactory.stringToKey(userKey), page);
        log.info("User posts successfully retrieved.");

        return feed;
    }
}
