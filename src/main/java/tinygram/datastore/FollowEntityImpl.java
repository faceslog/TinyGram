package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

class FollowEntityImpl extends TypedEntityImpl implements FollowEntity {

    private static final UserEntityManager userManager = UserEntityManager.get();
    private static final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get();
    private static final PostEntityManager postManager = PostEntityManager.get();

    public FollowEntityImpl(UserEntity source, UserEntity target) {
        super(source.getId() + target.getId());

        if (source.equals(target)) {
            throw new IllegalArgumentException("Trying to follow themselves.");
        }

        setProperty(PROPERTY_SOURCE, source.getKey());
        setProperty(PROPERTY_TARGET, target.getKey());

        source.incrementFollowingCount();
        addRelatedEntity(source);

        target.incrementFollowerCount();
        addRelatedEntity(target);

        final PostEntity latestPost = postManager.findLatest(target);

        if (latestPost != null) {
            final FeedNodeEntity feedNode = feedManager.register(source, latestPost);
            addRelatedEntity(feedNode);
        }
    }

    public FollowEntityImpl(Entity raw) {
        super(raw);
    }

    @Override
    public Key getSource() {
        return getProperty(PROPERTY_SOURCE);
    }

    @Override
    public Key getTarget() {
        return getProperty(PROPERTY_TARGET);
    }

    @Override
    public void forgetUsing(Forgetter forgetter) {
        try {
            final UserEntity source = userManager.get(getSource());
            source.decrementFollowingCount();
            source.persistUsing(forgetter);

            final UserEntity target = userManager.get(getTarget());
            target.decrementFollowerCount();
            target.persistUsing(forgetter);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }

        super.forgetUsing(forgetter);
    }
}
