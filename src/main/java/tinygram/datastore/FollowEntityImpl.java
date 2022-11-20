package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

class FollowEntityImpl extends TypedEntityImpl implements FollowEntity {

    public FollowEntityImpl(UserEntity source, UserEntity target) {
        super(source.getId() + target.getId());

        if (source.equals(target)) {
            throw new IllegalArgumentException("Trying to follow themselves.");
        }

        setProperty(FIELD_SOURCE, source.getKey());
        setProperty(FIELD_TARGET, target.getKey());

        source.incrementFollowingCount();
        addRelatedEntity(source);

        target.incrementFollowerCount();
        addRelatedEntity(target);

        final PostEntityManager postManager = PostEntityManager.get();
        final PostEntity latestPost = postManager.findLatest(target);

        if (latestPost != null) {
            final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get();
            final FeedNodeEntity feedNode = feedManager.register(source, latestPost);
            addRelatedEntity(feedNode);
        }
    }

    public FollowEntityImpl(Entity raw) {
        super(raw);
    }

    @Override
    public Key getSource() {
        return getProperty(FIELD_SOURCE);
    }

    @Override
    public Key getTarget() {
        return getProperty(FIELD_TARGET);
    }

    @Override
    public void forgetUsing(Forgetter forgetter) {
        final UserEntityManager userManager = UserEntityManager.get();

        try {
            final UserEntity source = userManager.get(getSource());
            source.decrementFollowingCount();
            addRelatedEntity(source);

            final UserEntity target = userManager.get(getTarget());
            target.decrementFollowerCount();
            addRelatedEntity(target);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }

        super.forgetUsing(forgetter);
    }
}
