package tinygram.datastore;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

class BasePostEntity extends AbstractUserAwareEntity implements PostEntity {

    public BasePostEntity(UserProvider userProvider, UserEntity owner, String image, String description) {
        super(userProvider);

        setProperty(FIELD_OWNER, owner.getKey());
        setProperty(FIELD_DATE, new Date());
        setProperty(FIELD_IMAGE, image);
        setProperty(FIELD_DESCRIPTION, description);
        setProperty(FIELD_LIKES, new HashSet<>());
        setProperty(FIELD_LIKE_COUNT, 0l);

        owner.incrementPostCount();
        addRelatedEntity(owner);

        final FeedRepository feedRepository = new BaseFeedRepository();

        for (final Key follower : owner.getFollowers()) {
            final FeedNodeEntity feedNode = feedRepository.register(follower, this);
            addRelatedEntity(feedNode);
        }
    }

    public BasePostEntity(UserProvider userProvider, Entity raw) {
        super(userProvider, raw);
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
        final long likeCount = getLikeCount() + 1;

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
        final long likeCount = getLikeCount() - 1;

        setProperty(FIELD_LIKES, likes);
        setProperty(FIELD_LIKE_COUNT, likeCount);

        return true;
    }

    @Override
    public long getLikeCount() {
        final Long object = getProperty(FIELD_LIKE_COUNT);
        return object;
    }
}
