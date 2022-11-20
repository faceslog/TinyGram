package tinygram.datastore;

import java.util.HashSet;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Entity;

class UserEntityImpl extends TypedEntityImpl implements UserEntity {

    private static final FollowEntityManager followManager = FollowEntityManager.get();
    private static final PostEntityManager postManager = PostEntityManager.get();

    public UserEntityImpl(User user, String name, String image) {
        super(user.getId());

        setProperty(FIELD_ID, user.getId());
        setProperty(FIELD_NAME, name);
        setProperty(FIELD_IMAGE, image);
        setProperty(FIELD_FOLLOWERS, new HashSet<>());
        setProperty(FIELD_FOLLOWER_COUNT, 0l);
        setProperty(FIELD_FOLLOWING_COUNT, 0l);
        setProperty(FIELD_POST_COUNT, 0l);
    }

    public UserEntityImpl(Entity raw) {
        super(raw);
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
    public void incrementFollowerCount() {
        final Long followerCount = getProperty(FIELD_FOLLOWER_COUNT);
        setProperty(FIELD_FOLLOWER_COUNT, followerCount + 1l);
    }

    @Override
    public void decrementFollowerCount() {
        final Long followerCount = getProperty(FIELD_FOLLOWER_COUNT);
        setProperty(FIELD_FOLLOWER_COUNT, followerCount - 1l);
    }

    @Override
    public long getFollowerCount() {
        final Long object = getProperty(FIELD_FOLLOWER_COUNT);
        return object;
    }

    @Override
    public void incrementFollowingCount() {
        final Long followingCount = getProperty(FIELD_FOLLOWING_COUNT);
        setProperty(FIELD_FOLLOWING_COUNT, followingCount + 1l);
    }

    @Override
    public void decrementFollowingCount() {
        final Long followingCount = getProperty(FIELD_FOLLOWING_COUNT);
        setProperty(FIELD_FOLLOWING_COUNT, followingCount - 1l);
    }

    @Override
    public long getFollowingCount() {
        final Long object = getProperty(FIELD_FOLLOWING_COUNT);
        return object;
    }

    @Override
    public void incrementPostCount() {
        final Long postCount = getProperty(FIELD_POST_COUNT);
        setProperty(FIELD_POST_COUNT, postCount + 1l);
    }

    @Override
    public void decrementPostCount() {
        final Long postCount = getProperty(FIELD_POST_COUNT);
        setProperty(FIELD_POST_COUNT, postCount - 1l);
    }

    @Override
    public long getPostCount() {
        final Long object = getProperty(FIELD_POST_COUNT);
        return object;
    }

    @Override
    public void forgetUsing(Forgetter forgetter) {
        followManager.findAllTo(this).forEachRemaining(follow -> follow.forgetUsing(forgetter));
        followManager.findAllFrom(this).forEachRemaining(follow -> follow.forgetUsing(forgetter));
        postManager.findAll(this).forEachRemaining(post -> post.forgetUsing(forgetter));

        super.forgetUsing(forgetter);
    }
}
