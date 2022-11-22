package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityImpl;

class FollowEntityImpl extends TypedEntityImpl implements FollowEntity {

    public FollowEntityImpl(UserEntity source, UserEntity target) {
        super(KIND, source.getKey().getName() + target.getKey().getName());

        if (source.equals(target)) {
            throw new IllegalArgumentException("Trying to follow themselves.");
        }

        setProperty(PROPERTY_SOURCE, source.getKey());
        setProperty(PROPERTY_TARGET, target.getKey());

        source.incrementFollowingCount();
        addRelatedEntity(source);

        target.incrementFollowerCount();
        addRelatedEntity(target);
    }

    public FollowEntityImpl(Entity raw) {
        super(KIND, raw);
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
    public void forgetUsing(TransactionContext context) {
        final UserEntityManager userManager = UserEntityManager.get(context);

        try {
            final UserEntity source = userManager.get(getSource());
            source.decrementFollowingCount();
            source.persistUsing(context);

            final UserEntity target = userManager.get(getTarget());
            target.decrementFollowerCount();
            target.persistUsing(context);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }

        super.forgetUsing(context);
    }
}
