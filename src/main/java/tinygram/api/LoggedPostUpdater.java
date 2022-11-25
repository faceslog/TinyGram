package tinygram.api;

import tinygram.datastore.LikeEntityManager;
import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

/**
 * An JSON-deserializable object, to use when updating a post entity with additional data about the
 * currently logged-in user.
 */
public class LoggedPostUpdater extends PostUpdater {

    /**
     * Whether the currently logged user likes the post.
     */
    public Boolean liked;

    /**
     * Updates a post entity according to the deserialized provided fields.
     *
     * @param context     The current transaction context.
     * @param currentUser The currently logged user entity.
     * @param post        The post entity to update.
     */
    public void update(TransactionContext context, UserEntity currentUser, PostEntity post) {
        update(post);

        if (liked != null) {
            final LikeEntityManager likeManager = LikeEntityManager.get(context);

            if (liked) {
                likeManager.register(currentUser, post).persistUsing(context);
            } else {
                likeManager.find(currentUser, post).forgetUsing(context);
            }
        }
    }
}
