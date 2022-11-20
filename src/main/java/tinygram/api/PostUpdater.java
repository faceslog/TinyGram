package tinygram.api;

import tinygram.datastore.PostEntity;

public class PostUpdater {

    public String image;
    public String description;
    public Boolean liked;

    public void update(PostEntity post) {
        if (image != null) {
            post.setImage(image);
        }

        if (description != null) {
            post.setDescription(description);
        }
    }
}
