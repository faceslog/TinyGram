package tinygram.api;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.UserEntity;
import tinygram.util.Path;
import tinygram.util.BasePath;

/**
 * The URL schema of the user API.
 */
public final class UserApiSchema {

    /**
     * The user resource name.
     */
    public static final String RESOURCE_NAME = "user";

    /**
     * The name of the URL resource argument, representing a serialized key of a user entity.
     */
    public static final String KEY_ARGUMENT_NAME = "userkey";

    /**
     * The relative path of the user API.
     */
    public static final String RELATIVE_PATH = RESOURCE_NAME;
    /**
     * The absolute path of the user API.
     */
    public static final String ABSOLUTE_PATH = InstApiSchema.PATH + "/" + RELATIVE_PATH;

    /**
     * The user API path suffix to use when a resource argument is needed.
     */
    public static final String KEY_ARGUMENT_SUFFIX = "/{" + KEY_ARGUMENT_NAME + "}";

    /**
     * Gets the path to a user resource within the user API.
     *
     * @param entity The user entity.
     *
     * @return The URL path to manage the resource representing <b>entity</b>.
     */
    public static Path getPath(UserEntity entity) {
        return getPath(entity.getKey());
    }

    /**
     * Gets the path to a user resource within the user API.
     *
     * @param key The key of the user entity.
     *
     * @return The URL path to manage the resource representing the user entity associated to
     *         <b>key</b>.
     */
    public static Path getPath(Key key) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_PATH + "/" + KeyFactory.keyToString(key));
    }

    private UserApiSchema() {}
}
