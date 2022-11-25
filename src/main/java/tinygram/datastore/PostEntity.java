package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

/**
 * A datastore entity, representing a post.
 */
public interface PostEntity extends TypedEntity {

    /**
     * The kind of any post entity.
     */
    static final String KIND = "Post";

    /**
     * The post owner key property.
     */
    static final Property<Key> PROPERTY_OWNER = Property.indexedKey("owner");
    /**
     * The post date property.
     */
    static final Property<Date> PROPERTY_DATE = Property.indexedDate("date");
    /**
     * The post image property.
     */
    static final Property<String> PROPERTY_IMAGE = Property.string("image");
    /**
     * The post image description property.
     */
    static final Property<String> PROPERTY_DESCRIPTION = Property.string("description");

    /**
     * The kind of any counter storing the number of likes.
     */
    static final String COUNTER_LIKE = KIND + "_like";

    @Override
    default String getKind() {
        return KIND;
    }

    /**
     * Gets the post owner.
     *
     * @return The key of the post owner entity.
     */
    Key getOwner();

    /**
     * Gets the post date.
     *
     * @return The post date.
     */
    Date getDate();

    /**
     * Gets the post image.
     *
     * @return The post image URL.
     */
    String getImage();

    /**
     * Sets the post image.
     *
     * @param image The new post image URL.
     */
    void setImage(String image);

    /**
     * Gets the post image description.
     *
     * @return The string post image description.
     */
    String getDescription();

    /**
     * Sets the post image description.
     *
     * @param description The new post image description.
     */
    void setDescription(String description);

    /**
     * Increments the number of likes, from a randomly selected counter shard.
     */
    void incrementLikeCount();

    /**
     * Decrements the number of likes, from a randomly selected counter shard.
     */
    void decrementLikeCount();

    /**
     * Gets the number of likes, summing all counter shard values.
     *
     * @return The total number of likes.
     */
    long getLikeCount();
}
