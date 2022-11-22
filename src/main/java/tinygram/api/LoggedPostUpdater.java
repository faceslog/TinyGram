package tinygram.api;

import tinygram.datastore.LikeEntityManager;
import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

public class LoggedPostUpdater extends PostUpdater {

    public Boolean liked;

    public void update(TransactionContext context, UserEntity currentUser, PostEntity post) {
        super.update(post);

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
