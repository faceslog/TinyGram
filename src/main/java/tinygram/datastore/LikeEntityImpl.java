package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityImpl;

/**
 * An implementation of the {@link LikeEntity} interface.
 */
class LikeEntityImpl extends TypedEntityImpl implements LikeEntity {

    /**
     * Creates a like entity, not already added to the datastore.
     *
     * @param user The like source user entity.
     * @param post The liked post entity.
     */
    public LikeEntityImpl(UserEntity user, PostEntity post) {
        super(KIND, user.getKey().getName() + post.getKey().getName());

        // On Instagram, a user can like their own posts.
        // if (user.getKey().equals(post.getOwner())) {
        //     throw new IllegalArgumentException("Trying to like its own post.");
        // }

        setProperty(PROPERTY_USER, user.getKey());
        setProperty(PROPERTY_POST, post.getKey());

        post.incrementLikeCount();
        addRelatedEntity(post);
    }

    /**
     * Encapsulates an already existing like entity.
     *
     * @param raw The raw entity.
     */
    public LikeEntityImpl(Entity raw) {
        super(KIND, raw);
    }

    @Override
    public Key getUser() {
        return getProperty(PROPERTY_USER);
    }

    @Override
    public Key getPost() {
        return getProperty(PROPERTY_POST);
    }

    @Override
    public void forgetUsing(TransactionContext context) {
        final PostEntityManager postManager = PostEntityManager.get(context);

        try {
            final PostEntity post = postManager.get(getPost());
            post.decrementLikeCount();
            post.persistUsing(context);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }

        super.forgetUsing(context);
    }
}
