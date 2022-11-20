package tinygram.api;

import tinygram.datastore.FollowEntityManager;
import tinygram.datastore.TransactionManager;
import tinygram.datastore.UserEntity;

public class LoggedUserUpdater extends UserUpdater {

    private static final TransactionManager transactionManager = TransactionManager.get();
    private static final FollowEntityManager followManager = FollowEntityManager.get();

    public Boolean followed;

    public void update(UserEntity currentUser, UserEntity user) {
        super.update(user);

        if (followed != null) {
            if (followed) {
                transactionManager.persist(followManager.register(currentUser, user));
            } else {
                transactionManager.forget(followManager.find(currentUser.getKey(), user.getKey()));
            }
        }
    }
}
