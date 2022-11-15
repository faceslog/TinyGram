package tinygram.api;

import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;
import tinygram.datastore.UserProvider;

public class PostUpdater implements EntityUpdater<PostEntity> {

    public String image;
    public String description;
    public Boolean liked;

    @Override
    public PostEntity update(PostEntity entity) {
        if (image != null) {
            entity.setImage(image);
        }

        if (description != null) {
            entity.setDescription(description);
        }

        final UserProvider userProvider = entity.getUserProvider();
        if (liked != null && userProvider.exists()) {
            final UserEntity currentUser = userProvider.get();

            if (liked) {
                entity.addLike(currentUser);
            } else {
                entity.removeLike(currentUser);
            }
        }

        return entity;
    }
}
