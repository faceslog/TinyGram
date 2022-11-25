package tinygram.api;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.PostEntity;
import tinygram.util.Path;
import tinygram.util.BasePath;

/**
 * The URL schema of the post API.
 */
public final class PostApiSchema {

    /**
     * The post resource name.
     */
    public static final String RESOURCE_NAME = "post";

    /**
     * The name of the URL resource argument, representing a serialized key of a post entity.
     */
    public static final String KEY_ARGUMENT_NAME = "postkey";

    /**
     * The relative path of the post API.
     */
    public static final String RELATIVE_PATH = RESOURCE_NAME;
    /**
     * The absolute path of the post API.
     */
    public static final String ABSOLUTE_PATH = InstApiSchema.PATH + "/" + RELATIVE_PATH;

    /**
     * The post API path suffix to use when a resource argument is needed.
     */
    public static final String KEY_ARGUMENT_SUFFIX = "/{" + KEY_ARGUMENT_NAME + "}";

    /**
     * Gets the path to a post resource within the post API.
     *
     * @param entity The post entity.
     *
     * @return The URL path to manage the resource representing <b>entity</b>.
     */
    public static Path getPath(PostEntity entity) {
        return getPath(entity.getKey());
    }

    /**
     * Gets the path to a post resource within the post API.
     *
     * @param key The key of the post entity.
     *
     * @return The URL path to manage the resource representing the post entity associated to
     *         <b>key</b>.
     */
    public static Path getPath(Key key) {
        return new BasePath(InstApiSchema.PATH, RELATIVE_PATH + "/" + KeyFactory.keyToString(key));
    }

    private PostApiSchema() {}
}
