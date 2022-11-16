package tinygram.datastore;

import java.util.Collection;
import java.util.HashSet;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

class BaseUserEntity extends AbstractUserAwareEntity implements UserEntity {

    public BaseUserEntity(User user, String name, String image) {
        super(new UndefinedUserProvider(), user.getId());

        setProperty(FIELD_ID, user.getId());
        setProperty(FIELD_NAME, name);
        setProperty(FIELD_IMAGE, image);
        setProperty(FIELD_FOLLOWERS, new HashSet<>());
        setProperty(FIELD_FOLLOWER_COUNT, 0l);
        setProperty(FIELD_FOLLOWING_COUNT, 0l);
        setProperty(FIELD_POST_COUNT, 0l);

        setUserProvider(new BaseUserProvider(this));
    }

    public BaseUserEntity(UserProvider userProvider, Entity raw) {
        super(userProvider, raw);
    }

    @Override
    public String getId() {
        return getProperty(FIELD_ID);
    }

    @Override
    public String getName() {
        return getProperty(FIELD_NAME);
    }

    @Override
    public void setName(String name) {
        setProperty(FIELD_NAME, name);
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
    public Collection<Key> getFollowers() {
        final Collection<Key> followers = getProperty(FIELD_FOLLOWERS);
        return followers == null ? new HashSet<>() : followers;
    }

    @Override
    public boolean addFollow(UserEntity user) {
        KindException.ensure(KIND, user);

        if (equals(user)) {
            throw new IllegalArgumentException("Trying to follow itself.");
        }

        final Collection<Key> following = getFollowers();
        final Key userKey = user.getKey();
        if (following.contains(userKey)) {
            return false;
        }

        following.add(userKey);
        final long followerCount = getFollowerCount() + 1;

        setProperty(FIELD_FOLLOWERS, following);
        setProperty(FIELD_FOLLOWER_COUNT, followerCount);

        user.incrementFollowing();
        addRelatedEntity(user);

        final PostRepository postRepository = new BasePostRepository(getUserProvider());
        final PostEntity latestPost = postRepository.findLatest(this);

        if (latestPost != null) {
            final FeedRepository feedRepository = new BaseFeedRepository();
            final FeedNodeEntity feedNode = feedRepository.register(user, latestPost);
            addRelatedEntity(feedNode);
        }

        return true;
    }

    @Override
    public boolean removeFollow(UserEntity user) {
        KindException.ensure(KIND, user);

        final Collection<Key> following = getFollowers();
        final Key userKey = user.getKey();
        if (!following.contains(userKey)) {
            return false;
        }

        following.remove(userKey);
        final long followerCount = getFollowerCount() - 1;

        setProperty(FIELD_FOLLOWERS, following);
        setProperty(FIELD_FOLLOWER_COUNT, followerCount);

        user.decrementFollowing();
        addRelatedEntity(user);

        return true;
    }

    @Override
    public long getFollowerCount() {
        final Long object = getProperty(FIELD_FOLLOWER_COUNT);
        return object;
    }

    @Override
    public void incrementFollowing() {
        final Long followingCount = getProperty(FIELD_FOLLOWING_COUNT);
        setProperty(FIELD_FOLLOWING_COUNT, followingCount + 1);
    }

    @Override
    public void decrementFollowing() {
        final Long followingCount = getProperty(FIELD_FOLLOWING_COUNT);
        setProperty(FIELD_FOLLOWING_COUNT, followingCount - 1);
    }

    @Override
    public long getFollowingCount() {
        final Long object = getProperty(FIELD_FOLLOWING_COUNT);
        return object;
    }

    @Override
    public void incrementPostCount() {
        final Long postCount = getProperty(FIELD_POST_COUNT);
        setProperty(FIELD_POST_COUNT, postCount + 1);
    }

    @Override
    public long getPostCount() {
        final Long object = getProperty(FIELD_POST_COUNT);
        return object;
    }
}
