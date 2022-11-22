package tinygram.api;

import tinygram.datastore.FollowEntityManager;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

public class LoggedUserUpdater extends UserUpdater {

    public Boolean followed;

    public void update(TransactionContext context, UserEntity currentUser, UserEntity user) {
        super.update(user);

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
