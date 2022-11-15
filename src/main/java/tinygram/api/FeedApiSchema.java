package tinygram.api;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.UserEntity;

public final class FeedApiSchema {

    public static final String RESOURCE_NAME = "feed";

    public static final String PAGE_ARGUMENT_NAME = "page";

    public static final String RELATIVE_GLOBAL_PATH = RESOURCE_NAME + "/global";
    public static final String RELATIVE_FOLLOWED_PATH = RESOURCE_NAME + "/followed";
    public static final String RELATIVE_FROM_PATH = RESOURCE_NAME + "/from";

    public static final String ABSOLUTE_GLOBAL_PATH = InstApiSchema.PATH + "/" + RELATIVE_GLOBAL_PATH;
    public static final String ABSOLUTE_FOLLOWED_PATH = InstApiSchema.PATH + "/" + RELATIVE_FOLLOWED_PATH;
    public static final String ABSOLUTE_FROM_PATH = InstApiSchema.PATH + "/" + RELATIVE_FROM_PATH;

    public static final String PAGE_ARGUMENT_SUFFIX = "/{" + PAGE_ARGUMENT_NAME + "}";

    public static ApiPath getGlobalPath() {
        return new BaseApiPath(InstApiSchema.PATH, RELATIVE_GLOBAL_PATH);
    }

    public static ApiPath getGlobalPath(String nextPage) {
        return new BaseApiPath(InstApiSchema.PATH, RELATIVE_GLOBAL_PATH + "/" + nextPage);
    }

    public static ApiPath getFollowedPath(UserEntity entity) {
        return getFollowedPath(entity.getKey());
    }

    public static ApiPath getFollowedPath(UserEntity entity, String nextPage) {
        return getFollowedPath(entity.getKey(), nextPage);
    }

    public static ApiPath getFollowedPath(Key key) {
        return new BaseApiPath(InstApiSchema.PATH, RELATIVE_FOLLOWED_PATH + "/" + KeyFactory.keyToString(key));
    }

    public static ApiPath getFollowedPath(Key key, String nextPage) {
        return new BaseApiPath(InstApiSchema.PATH, RELATIVE_FOLLOWED_PATH + "/" + KeyFactory.keyToString(key) + "/" + nextPage);
    }

    public static ApiPath getFromPath(UserEntity entity) {
        return getFromPath(entity.getKey());
    }

    public static ApiPath getFromPath(UserEntity entity, String nextPage) {
        return getFromPath(entity.getKey(), nextPage);
    }

    public static ApiPath getFromPath(Key key) {
        return new BaseApiPath(InstApiSchema.PATH, RELATIVE_FROM_PATH + "/" + KeyFactory.keyToString(key));
    }

    public static ApiPath getFromPath(Key key, String nextPage) {
        return new BaseApiPath(InstApiSchema.PATH, RELATIVE_FROM_PATH + "/" + KeyFactory.keyToString(key) + "/" + nextPage);
    }

    private FeedApiSchema() {}
}
