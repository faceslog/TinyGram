package tinygram.api;

import tinygram.api.util.Resource;
import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;

public class PostResource extends Resource {

    private final PostEntity post;

    public PostResource(PostEntity post) {
        this.post = post;
    }

    public PostResource(UserEntity currentUser, PostEntity post) {
        super(currentUser);

        this.post = post;
    }

    public PostEntity getPost() {
        return post;
    }
}
