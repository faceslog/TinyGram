package tinygram.datastore;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

class PostEntityImpl extends TypedEntityImpl implements PostEntity {

    private static final UserEntityManager userManager = UserEntityManager.get();
    private static final FollowEntityManager followManager = FollowEntityManager.get();
    private static final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get();

    public PostEntityImpl(UserEntity owner, String image, String description) {
        setProperty(FIELD_OWNER, owner.getKey());
        setProperty(FIELD_DATE, new Date());
        setProperty(FIELD_IMAGE, image);
        setProperty(FIELD_DESCRIPTION, description);
        setProperty(FIELD_LIKES, new HashSet<>());
        setProperty(FIELD_LIKE_COUNT, 0l);

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
        return getProperty(FIELD_OWNER);
    }

    @Override
    public Date getDate() {
        return getProperty(FIELD_DATE);
    }

    @Override
    public String getImage() {
        return getProperty(FIELD_IMAGE);
    }

    @Override
    public void setImage(String image) {
        setProperty(FIELD_IMAGE, image);
    }

    @Override
    public String getDescription() {
        return getProperty(FIELD_DESCRIPTION);
    }

    @Override
    public void setDescription(String description) {
        setProperty(FIELD_DESCRIPTION, description);
    }

    @Override
    public Collection<Key> getLikes() {
        final Collection<Key> likes = getProperty(FIELD_LIKES);
        return likes == null ? new HashSet<>() : likes;
    }

    @Override
    public boolean addLike(Key userKey) {
        KindException.ensure(UserEntity.KIND, userKey);
        // Sur Instagram, un utilisateur peut like ses propres posts.
        // if (getOwner().getKey().equals(userKey)) {
        //     throw new IllegalArgumentException("Trying to like its own post.");
        // }

        final Collection<Key> likes = getLikes();
        if (likes.contains(userKey)) {
            return false;
        }

        likes.add(userKey);
        final long likeCount = getLikeCount() + 1l;

        setProperty(FIELD_LIKES, likes);
        setProperty(FIELD_LIKE_COUNT, likeCount);

        return true;
    }

    @Override
    public boolean removeLike(Key userKey) {
        KindException.ensure(UserEntity.KIND, userKey);

        final Collection<Key> likes = getLikes();
        if (!likes.contains(userKey)) {
            return false;
        }

        likes.remove(userKey);
        final long likeCount = getLikeCount() - 1l;

        setProperty(FIELD_LIKES, likes);
        setProperty(FIELD_LIKE_COUNT, likeCount);

        return true;
    }

    @Override
    public long getLikeCount() {
        final Long object = getProperty(FIELD_LIKE_COUNT);
        return object;
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
