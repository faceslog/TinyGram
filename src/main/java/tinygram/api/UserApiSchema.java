package tinygram.api;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.UserEntity;
import tinygram.util.Path;
import tinygram.util.BasePath;

public final class UserApiSchema {

    public static final String RESOURCE_NAME = "user";

    public static final String KEY_ARGUMENT_NAME = "userkey";

    public static final String RELATIVE_PATH = RESOURCE_NAME;
    public static final String ABSOLUTE_PATH = InstApiSchema.PATH + "/" + RELATIVE_PATH;

    public static final String KEY_ARGUMENT_SUFFIX = "/{" + KEY_ARGUMENT_NAME + "}";

    public static Path getPath(UserEntity entity) {
        return getPath(entity.getKey());
    }

    public static Path getPath(Key key) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_PATH + "/" + KeyFactory.keyToString(key));
    }

    private UserApiSchema() {}
}
