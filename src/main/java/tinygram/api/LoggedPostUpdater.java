package tinygram.api;

import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;

public class LoggedPostUpdater extends PostUpdater {

    public Boolean liked;

    public void update(UserEntity currentUser, PostEntity post) {
        super.update(post);

        if (liked != null) {
            if (liked) {
                post.addLike(currentUser);
            } else {
                post.removeLike(currentUser);
            }
        }
    }
}
