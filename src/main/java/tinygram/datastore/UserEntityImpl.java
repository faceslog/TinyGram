package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityImpl;

class UserEntityImpl extends TypedEntityImpl implements UserEntity {

    private static final CounterManager followerCounterManager = CounterManager.getOf(COUNTER_FOLLOWER);

    public UserEntityImpl(User user, String name, String image) {
        super(KIND, user.getId());

        setProperty(PROPERTY_ID, user.getId());
        setProperty(PROPERTY_NAME, name);
        setProperty(PROPERTY_IMAGE, image);
        setProperty(PROPERTY_FOLLOWING_COUNT, 0l);
        setProperty(PROPERTY_POST_COUNT, 0l);

        final CounterEntity followerCounter = followerCounterManager.register(getKey().getName());
        addRelatedEntity(followerCounter);
    }

    public UserEntityImpl(Entity raw) {
        super(KIND, raw);
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

    private CounterEntity getFollowerCounter() {
        try {
            return followerCounterManager.get(getKey().getName());
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void incrementFollowerCount() {
        final CounterEntity counter = getFollowerCounter();
        counter.increment();
        addRelatedEntity(counter);
    }

    @Override
    public void decrementFollowerCount() {
        final CounterEntity counter = getFollowerCounter();
        counter.decrement();
        addRelatedEntity(counter);
    }

    @Override
    public long getFollowerCount() {
        return getFollowerCounter().getValue();
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
    public void forgetUsing(TransactionContext context) {
        final FollowEntityManager followManager = FollowEntityManager.get(context);
        final PostEntityManager postManager = PostEntityManager.get(context);
        final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get(context);

        getFollowerCounter().forgetUsing(context);

        followManager.findAllTo(this).forEachRemaining(follow -> follow.forgetUsing(context));
        followManager.findAllFrom(this).forEachRemaining(follow -> follow.forgetUsing(context));
        postManager.findAll(this).forEachRemaining(post -> post.forgetUsing(context));
        feedManager.findAllOfUser(this).forEachRemaining(node -> node.forgetUsing(context));

        super.forgetUsing(context);
    }
}
