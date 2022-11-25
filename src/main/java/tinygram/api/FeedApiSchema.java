package tinygram.api;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.UserEntity;
import tinygram.util.Path;
import tinygram.util.BasePath;

/**
 * The URL schema of the post feed API.
 */
public final class FeedApiSchema {

    /**
     * The post feed resource name.
     */
    public static final String RESOURCE_NAME = "feed";

    /**
     * The name of the URL page argument, representing a serialized key of a post feed node entity.
     */
    public static final String PAGE_ARGUMENT_NAME = "page";

    /**
     * The relative path of the post feed API, when fetching the global post feed.
     */
    public static final String RELATIVE_GLOBAL_PATH = RESOURCE_NAME + "/global";
    /**
     * The relative path of the post feed API, when fetching a user post feed.
     */
    public static final String RELATIVE_FOLLOWED_PATH = RESOURCE_NAME + "/followed";
    /**
     * The relative path of the post feed API, when fetching the post feed of posts written by a
     * user.
     */
    public static final String RELATIVE_FROM_PATH = RESOURCE_NAME + "/from";

    /**
     * The absolute path of the post feed API, when fetching the global post feed.
     */
    public static final String ABSOLUTE_GLOBAL_PATH = InstApiSchema.PATH + "/" + RELATIVE_GLOBAL_PATH;
    /**
     * The absolute path of the post feed API, when fetching a user post feed.
     */
    public static final String ABSOLUTE_FOLLOWED_PATH = InstApiSchema.PATH + "/" + RELATIVE_FOLLOWED_PATH;
    /**
     * The absolute path of the post feed API, when fetching the post feed of posts written by a
     * user.
     */
    public static final String ABSOLUTE_FROM_PATH = InstApiSchema.PATH + "/" + RELATIVE_FROM_PATH;

    /**
     * The post feed API path suffix to use when a page argument is needed.
     */
    public static final String PAGE_ARGUMENT_SUFFIX = "/{" + PAGE_ARGUMENT_NAME + "}";

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching the global post
     * feed.
     *
     * @return The URL path to manage the global post feed resource.
     */
    public static Path getGlobalPath() {
        return new BasePath(InstApiSchema.PATH, RELATIVE_GLOBAL_PATH);
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching the global post
     * feed.
     *
     * @param nextPage The next feed page token.
     *
     * @return The URL path to manage the global post feed resource, starting at <b>nextPage</b>.
     */
    public static Path getGlobalPath(String nextPage) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_GLOBAL_PATH + "/" + nextPage);
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching a user post
     * feed.
     *
     * @param entity The user entity the post feed is about.
     *
     * @return The URL path to manage the user post feed resource of <b>entity</b>.
     */
    public static Path getFollowedPath(UserEntity entity) {
        return getFollowedPath(entity.getKey());
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching a user post
     * feed.
     *
     * @param entity   The user entity the post feed is about.
     * @param nextPage The next feed page token.
     *
     * @return The URL path to manage the user feed resource of <b>entity</b>, starting at
     *         <b>nextPage</b>.
     */
    public static Path getFollowedPath(UserEntity entity, String nextPage) {
        return getFollowedPath(entity.getKey(), nextPage);
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching a user post
     * feed.
     *
     * @param key The key of the user entity the post feed is about.
     *
     * @return The URL path to manage the user post feed resource of the user entity associated to
     *         <b>key</b>.
     */
    public static Path getFollowedPath(Key key) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_FOLLOWED_PATH);
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching a user post
     * feed.
     *
     * @param key      The key of the user entity the post feed is about.
     * @param nextPage The next feed page token.
     *
     * @return The URL path to manage the user post feed resource of the user entity associated to
     *         <b>entity</b>, starting at <b>nextPage</b>.
     */
    public static Path getFollowedPath(Key key, String nextPage) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_FOLLOWED_PATH + "/" + nextPage);
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching the post feed
     * of posts written by a user.
     *
     * @param entity The user entity the post feed is about.
     *
     * @return The URL path to manage the post feed resource of posts written by <b>entity</b>.
     */
    public static Path getFromPath(UserEntity entity) {
        return getFromPath(entity.getKey());
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching the post feed
     * of posts written by a user.
     *
     * @param entity   The user entity the post feed is about.
     * @param nextPage The next feed page token.
     *
     * @return The URL path to manage the post feed resource of posts written by <b>entity</b>,
     *         starting at <b>nextPage</b>.
     */
    public static Path getFromPath(UserEntity entity, String nextPage) {
        return getFromPath(entity.getKey(), nextPage);
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching the post feed
     * of posts written by a user.
     *
     * @param key The key of the user entity the post feed is about.
     *
     * @return The URL path to manage the post feed resource of posts written by the user entity
     *         associated to <b>key</b>.
     */
    public static Path getFromPath(Key key) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_FROM_PATH + "/" +
                            KeyFactory.keyToString(key));
    }

    /**
     * Gets the path to a post feed resource within the post feed API, when fetching the post feed
     * of posts written by a user.
     *
     * @param key      The key of the user entity the post feed is about.
     * @param nextPage The next feed page token.
     *
     * @return The URL path to manage the post feed resource of posts written by the user entity
     *         associated to <b>key</b>, starting at <b>nextPage</b>.
     */
    public static Path getFromPath(Key key, String nextPage) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_FROM_PATH + "/" +
                            KeyFactory.keyToString(key) + "/" + nextPage);
    }

    private FeedApiSchema() {}
}
