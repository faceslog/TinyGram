package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

/**
 * A datastore entity, representing a follow from a user to another.
 */
public interface FollowEntity extends TypedEntity {

    /**
     * The kind of any follow entity.
     */
    static final String KIND = "Follow";

    /**
     * The follower key property.
     */
    static final Property<Key> PROPERTY_SOURCE = Property.indexedKey("source");
    /**
     * The followed user key property.
     */
    static final Property<Key> PROPERTY_TARGET = Property.indexedKey("target");

    @Override
    default String getKind() {
        return KIND;
    }

    /**
     * Gets the follower.
     *
     * @return The key of the follower entity.
     */
    Key getSource();

    /**
     * Gets the followed user.
     *
     * @return The key of the followed user entity.
     */
    Key getTarget();
}
