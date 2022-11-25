package tinygram.api;

import tinygram.api.util.Resource;
import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;

/**
 * A resource managed by the {@link PostApi}, encapsulating a post entity.
 */
public class PostResource extends Resource {

    /**
     * The post entity.
     */
    private final PostEntity post;

    /**
     * Creates a post resource.
     *
     * @param post The post entity.
     */
    public PostResource(PostEntity post) {
        this.post = post;
    }

    /**
     * Creates a post resource, aware of the currently logged user.
     *
     * @param currentUser The currently logged user entity.
     * @param post        The post entity.
     */
    public PostResource(UserEntity currentUser, PostEntity post) {
        super(currentUser);

        this.post = post;
    }

    /**
     * Gets the post the resource represents.
     *
     * @return The post entity.
     */
    public PostEntity getPost() {
        return post;
    }
}
