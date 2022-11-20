package tinygram.datastore;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

class LikeEntityImpl extends TypedEntityImpl implements LikeEntity {

    private static final PostEntityManager postManager = PostEntityManager.get();

    public LikeEntityImpl(UserEntity user, PostEntity post) {
        super(user.getKey().getName() + post.getKey().getName());

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
        super(raw);
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
    public void forgetUsing(Forgetter forgetter) {
        try {
            final PostEntity post = postManager.get(getPost());
            post.decrementLikeCount();
            post.persistUsing(forgetter);
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException(e);
        }

        super.forgetUsing(forgetter);
    }
}
