package tinygram.api;

import tinygram.datastore.PostEntity;

public class PostUpdater implements EntityUpdater<PostEntity> {

    public String image;
    public String description;
    public Boolean liked;

    @Override
    public PostEntity update(PostEntity postEntity) {
        if (image != null) {
            postEntity.setImage(image);
        }

        if (description != null) {
            postEntity.setDescription(description);
        }

        if (liked != null && postEntity.getUserProvider().exists()) {
            if (liked) {
                postEntity.addLike(postEntity.getUserProvider().get());
            } else {
                postEntity.removeLike(postEntity.getUserProvider().get());
            }
        }

        return postEntity;
    }
}
