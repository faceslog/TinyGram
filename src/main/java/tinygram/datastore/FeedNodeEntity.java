package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

/**
 * A datastore entity, representing a post within a user feed.
 */
public interface FeedNodeEntity extends TypedEntity {

    /**
     * The kind of any feed node entity.
     */
    static final String KIND = "FeedNode";

    /**
     * The feed user key property.
     */
    static final Property<Key> PROPERTY_USER = Property.indexedKey("user");
    /**
     * The feed post key property.
     */
    static final Property<Key> PROPERTY_POST = Property.indexedKey("post");
    /**
     * The post date property.
     */
    static final Property<Date> PROPERTY_DATE = Property.indexedDate("date");

    @Override
    default String getKind() {
        return KIND;
    }

    /**
     * Gets the feed user.
     *
     * @return The key of the feed user entity.
     */
    Key getUser();

    /**
     * Gets the feed post.
     *
     * @return The key of the feed post entity.
     */
    Key getPost();

    /**
     * Gets the post date.
     *
     * @return The post date.
     */
    Date getDate();
}
