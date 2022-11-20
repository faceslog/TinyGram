package tinygram.api;

import tinygram.datastore.LikeEntityManager;
import tinygram.datastore.PostEntity;
import tinygram.datastore.TransactionManager;
import tinygram.datastore.UserEntity;

public class LoggedPostUpdater extends PostUpdater {

    private static final TransactionManager transactionManager = TransactionManager.get();
    private static final LikeEntityManager likeManager = LikeEntityManager.get();

    public Boolean liked;

    public void update(UserEntity currentUser, PostEntity post) {
        super.update(post);

        if (liked != null) {
            if (liked) {
                transactionManager.persist(likeManager.register(currentUser, post));
            } else {
                transactionManager.forget(likeManager.find(currentUser, post));
            }
        }
    }
}
