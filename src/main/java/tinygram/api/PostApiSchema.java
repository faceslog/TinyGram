package tinygram.api;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.PostEntity;

public final class PostApiSchema {

    public static final String RESOURCE_NAME = "post";

    public static final String KEY_ARGUMENT_NAME = "postkey";

    public static final String RELATIVE_PATH = RESOURCE_NAME;
    public static final String ABSOLUTE_PATH = InstApiSchema.PATH + "/" + RELATIVE_PATH;

    public static final String KEY_ARGUMENT_SUFFIX = "/{" + KEY_ARGUMENT_NAME + "}";

    public static ApiPath getPath(PostEntity entity) {
        return getPath(entity.getKey());
    }

    public static ApiPath getPath(Key key) {
        return new BaseApiPath(ABSOLUTE_PATH, key);
    }

    private PostApiSchema() {}
}
