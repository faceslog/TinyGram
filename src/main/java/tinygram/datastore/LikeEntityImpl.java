package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityImpl;

class LikeEntityImpl extends TypedEntityImpl implements LikeEntity {

    public LikeEntityImpl(UserEntity user, PostEntity post) {
        super(KIND, user.getKey().getName() + post.getKey().getName());

        // Sur Instagram, un utilisateur peut like ses propres posts.
        // if (user.getKey().equals(post.getOwner())) {
        //     throw new IllegalArgumentException("Trying to like its own post.");
        // }

        setProperty(PROPERTY_USER, user.getKey());
        setProperty(PROPERTY_POST, post.getKey());

        post.incrementLikeCount();
        addRelatedEntity(post);
    }

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
