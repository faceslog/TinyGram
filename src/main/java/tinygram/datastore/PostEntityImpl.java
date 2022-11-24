package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityImpl;

/**
 * An implementation of the {@link PostEntity} interface.
 */
class PostEntityImpl extends TypedEntityImpl implements PostEntity {

    private final CounterManager likeCounterManager = CounterManager.getOf(COUNTER_LIKE);

    /**
     * Creates a post entity, not already added to the datastore.
     *
     * @param owner       The post owner entity.
     * @param image       The post image URL.
     * @param description The post image description.
     */
    public PostEntityImpl(UserEntity owner, String image, String description) {
        super(KIND, owner.getKey().getName() + new Date().getTime());

        setProperty(PROPERTY_OWNER, owner.getKey());
        setProperty(PROPERTY_DATE, new Date());
        setProperty(PROPERTY_IMAGE, image);
        setProperty(PROPERTY_DESCRIPTION, description);

        owner.incrementPostCount();
        addRelatedEntity(owner);

        final CounterEntity likeCounter = likeCounterManager.register(getKey().getName());
        addRelatedEntity(likeCounter);
    }

    /**
     * Encapsulates an already existing post entity.
     *
     * @param raw The raw entity.
     */
    public PostEntityImpl(Entity raw) {
        super(KIND, raw);
    }

    @Override
    public Key getOwner() {
        return getProperty(PROPERTY_OWNER);
    }

    @Override
    public Date getDate() {
        return getProperty(PROPERTY_DATE);
    }

    @Override
    public String getImage() {
        return getProperty(PROPERTY_IMAGE);
    }

    @Override
    public void setImage(String image) {
        setProperty(PROPERTY_IMAGE, image);
    }

    @Override
    public String getDescription() {
        return getProperty(PROPERTY_DESCRIPTION);
    }

    @Override
    public void setDescription(String description) {
        setProperty(PROPERTY_DESCRIPTION, description);
    }

    /**
     * Gets the counter storing the number of likes.
     *
     * @return The counter entity.
     */
    private CounterEntity getLikeCounter() {
        try {
            return likeCounterManager.get(getKey().getName());
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void incrementLikeCount() {
        final CounterEntity counter = getLikeCounter();
        counter.increment();
        addRelatedEntity(counter);
    }

    @Override
    public void decrementLikeCount() {
        final CounterEntity counter = getLikeCounter();
        counter.decrement();
        addRelatedEntity(counter);
    }

    @Override
    public long getLikeCount() {
        return getLikeCounter().getValue();
    }

    @Override
    public void forgetUsing(TransactionContext context) {
        final UserEntityManager userManager = UserEntityManager.get(context);
        final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get(context);

        try {
            final UserEntity owner = userManager.get(getOwner());
            owner.decrementPostCount();
            owner.persistUsing(context);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }

        getLikeCounter().forgetUsing(context);

        feedManager.findAllOfPost(this).forEachRemaining(feedNode -> feedNode.forgetUsing(context));

        super.forgetUsing(context);
    }
}
