package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TypedEntityImpl;

/**
 * An implementation of the {@link FeedNodeEntity} interface.
 */
class FeedNodeEntityImpl extends TypedEntityImpl implements FeedNodeEntity {

    /**
     * Creates a feed node entity, not already added to the datastore.
     *
     * @param userKey The key of the feed user entity.
     * @param post    The feed post entity.
     */
    public FeedNodeEntityImpl(Key userKey, PostEntity post) {
        super(KIND, post.getKey().getName() + userKey.getName());

        setProperty(PROPERTY_USER, userKey);
        setProperty(PROPERTY_POST, post.getKey());
        setProperty(PROPERTY_DATE, post.getDate());
    }

    /**
     * Encapsulates an already existing feed node entity.
     *
     * @param raw The raw entity.
     */
    public FeedNodeEntityImpl(Entity raw) {
        super(KIND, raw);
    }

    @Override
    public Key getUser() {
        return getProperty(PROPERTY_USER);
    }

    @Override
    public Key getPost() {
        return getProperty(PROPERTY_POST);
    }

    @Override
    public Date getDate() {
        return getProperty(PROPERTY_DATE);
    }
}
