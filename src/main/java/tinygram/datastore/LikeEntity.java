package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

/**
 * A datastore entity, representing a like from a user to a post.
 */
public interface LikeEntity extends TypedEntity {

    /**
     * The kind of any like entity.
     */
    static final String KIND = "Like";

    /**
     * The like source user key property.
     */
    static final Property<Key> PROPERTY_USER = Property.indexedKey("user");
    /**
     * The liked post key property.
     */
    static final Property<Key> PROPERTY_POST = Property.indexedKey("post");

    @Override
    default String getKind() {
        return KIND;
    }

    /**
     * Gets the like source user.
     *
     * @return The key of the like source user entity.
     */
    Key getUser();

    /**
     * Gets the liked post.
     *
     * @return The key of the liked post entity.
     */
    Key getPost();
}
