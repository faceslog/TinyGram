package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

public interface LikeEntity extends TypedEntity {

    static final String KIND = "Like";

    static final Property<Key> PROPERTY_USER = Property.indexedKey("user");
    static final Property<Key> PROPERTY_POST = Property.indexedKey("post");

    @Override
    default String getKind() {
        return KIND;
    }

    Key getUser();

    Key getPost();
}
