package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Entity;

class UserEntityImpl extends TypedEntityImpl implements UserEntity {

    private static final FollowEntityManager followManager = FollowEntityManager.get();
    private static final PostEntityManager postManager = PostEntityManager.get();

    public UserEntityImpl(User user, String name, String image) {
        super(user.getId());

        setProperty(PROPERTY_ID, user.getId());
        setProperty(PROPERTY_NAME, name);
        setProperty(PROPERTY_IMAGE, image);
        setProperty(PROPERTY_FOLLOWER_COUNT, 0l);
        setProperty(PROPERTY_FOLLOWING_COUNT, 0l);
        setProperty(PROPERTY_POST_COUNT, 0l);
    }

    public UserEntityImpl(Entity raw) {
        super(raw);
    }

    @Override
    public String getId() {
        return getProperty(PROPERTY_ID);
    }

    @Override
    public String getName() {
        return getProperty(PROPERTY_NAME);
    }

    @Override
    public void setName(String name) {
        setProperty(PROPERTY_NAME, name);
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
    public void incrementFollowerCount() {
        final long followerCount = getProperty(PROPERTY_FOLLOWER_COUNT);
        setProperty(PROPERTY_FOLLOWER_COUNT, followerCount + 1l);
    }

    @Override
    public void decrementFollowerCount() {
        final long followerCount = getProperty(PROPERTY_FOLLOWER_COUNT);
        setProperty(PROPERTY_FOLLOWER_COUNT, followerCount - 1l);
    }

    @Override
    public long getFollowerCount() {
        return getProperty(PROPERTY_FOLLOWER_COUNT);
    }

    @Override
    public void incrementFollowingCount() {
        final long followingCount = getProperty(PROPERTY_FOLLOWING_COUNT);
        setProperty(PROPERTY_FOLLOWING_COUNT, followingCount + 1l);
    }

    @Override
    public void decrementFollowingCount() {
        final long followingCount = getProperty(PROPERTY_FOLLOWING_COUNT);
        setProperty(PROPERTY_FOLLOWING_COUNT, followingCount - 1l);
    }

    @Override
    public long getFollowingCount() {
        return getProperty(PROPERTY_FOLLOWING_COUNT);
    }

    @Override
    public void incrementPostCount() {
        final long postCount = getProperty(PROPERTY_POST_COUNT);
        setProperty(PROPERTY_POST_COUNT, postCount + 1l);
    }

    @Override
    public void decrementPostCount() {
        final long postCount = getProperty(PROPERTY_POST_COUNT);
        setProperty(PROPERTY_POST_COUNT, postCount - 1l);
    }

    @Override
    public long getPostCount() {
        return getProperty(PROPERTY_POST_COUNT);
    }

    @Override
    public void forgetUsing(Forgetter forgetter) {
        followManager.findAllTo(this).forEachRemaining(follow -> follow.forgetUsing(forgetter));
        followManager.findAllFrom(this).forEachRemaining(follow -> follow.forgetUsing(forgetter));
        postManager.findAll(this).forEachRemaining(post -> post.forgetUsing(forgetter));

        super.forgetUsing(forgetter);
    }
}
