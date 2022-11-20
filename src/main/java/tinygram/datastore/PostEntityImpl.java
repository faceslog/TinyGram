package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

class PostEntityImpl extends TypedEntityImpl implements PostEntity {

    private static final UserEntityManager userManager = UserEntityManager.get();
    private static final FollowEntityManager followManager = FollowEntityManager.get();
    private static final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get();

    public PostEntityImpl(UserEntity owner, String image, String description) {
        setProperty(PROPERTY_OWNER, owner.getKey());
        setProperty(PROPERTY_DATE, new Date());
        setProperty(PROPERTY_IMAGE, image);
        setProperty(PROPERTY_DESCRIPTION, description);
        setProperty(PROPERTY_LIKE_COUNT, 0l);

        owner.incrementPostCount();
        addRelatedEntity(owner);

        followManager.findAllTo(owner).forEachRemaining(follow -> {
            try {
                final UserEntity follower = userManager.get(follow.getSource());
                final FeedNodeEntity feedNode = feedManager.register(follower, this);
                addRelatedEntity(feedNode);
            } catch (EntityNotFoundException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public PostEntityImpl(Entity raw) {
        super(raw);
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

    @Override
    public void incrementLikeCount() {
        final long likeCount = getProperty(PROPERTY_LIKE_COUNT);
        setProperty(PROPERTY_LIKE_COUNT, likeCount + 1l);
    }

    @Override
    public void decrementLikeCount() {
        final long likeCount = getProperty(PROPERTY_LIKE_COUNT);
        setProperty(PROPERTY_LIKE_COUNT, likeCount - 1l);
    }

    @Override
    public long getLikeCount() {
        return getProperty(PROPERTY_LIKE_COUNT);
    }

    @Override
    public void forgetUsing(Forgetter forgetter) {
        try {
            final UserEntity owner = userManager.get(getOwner());
            owner.decrementPostCount();
            owner.persistUsing(forgetter);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }

        feedManager.findAllOfPost(this).forEachRemaining(feedNode -> feedNode.forgetUsing(forgetter));

        super.forgetUsing(forgetter);
    }
}
