package tinygram.api;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.UserEntity;

public final class UserApiSchema {

    public static final String RESOURCE_NAME = "user";

    public static final String KEY_ARGUMENT_NAME = "userkey";

    public static final String RELATIVE_PATH = RESOURCE_NAME;
    public static final String ABSOLUTE_PATH = InstApiSchema.PATH + "/" + RELATIVE_PATH;

    public static final String KEY_ARGUMENT_SUFFIX = "/{" + KEY_ARGUMENT_NAME + "}";

    public static ApiPath getPath(UserEntity entity) {
        return getPath(entity.getKey());
    }

    public static ApiPath getPath(Key key) {
        return new BaseApiPath(ABSOLUTE_PATH, key);
    }

    private UserApiSchema() {}
}
