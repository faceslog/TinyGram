package tinygram.api;

import tinygram.datastore.FollowEntityManager;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

/**
 * An JSON-deserializable object, to use when updating a user entity with additional data about the
 * currently logged-in user.
 */
public class LoggedUserUpdater extends UserUpdater {

    /**
     * Whether the currently logged user follows the user.
     */
    public Boolean followed;

    /**
     * Updates a user entity according to the deserialized provided fields.
     *
     * @param context     The current transaction context.
     * @param currentUser The currently logged user entity.
     * @param user        The user entity to update.
     */
    public void update(TransactionContext context, UserEntity currentUser, UserEntity user) {
        update(user);

        if (followed != null) {
            final FollowEntityManager followManager = FollowEntityManager.get(context);

            if (followed) {
                followManager.register(currentUser, user).persistUsing(context);
            } else {
                followManager.find(currentUser, user).forgetUsing(context);
            }
        }
    }
}
