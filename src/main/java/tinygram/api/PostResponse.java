package tinygram.api;

import java.util.Date;

import com.google.appengine.api.datastore.KeyFactory;

import tinygram.api.util.EntityResponse;
import tinygram.datastore.LikeEntity;
import tinygram.datastore.LikeEntityManager;
import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

/**
 * An object to be serialized into a JSON value, encapsulating a post resource.
 *
 * <p> <b>Warning:</b> Should only be built from a {@link PostResource} by using a
 * {@link PostTransformer}.
 */
class PostResponse extends EntityResponse<PostEntity> {

    /**
     * The serialized key of the post owner entity.
     */
    public final String owner;
    /**
     * The post date.
     */
    public final Date date;
    /**
     * The post image.
     */
    public final String image;
    /**
     * The post image description.
     */
    public final String description;
    /**
     * The number of likes.
     */
    public final long likecount;
    /**
     * Whether the currently logged user has liked the post.
     */
    public final Boolean liked;

    /**
     * Creates a post response object.
     *
     * @param resource The post resource.
     */
    public PostResponse(PostResource resource) {
        super(resource.getPost());

        final PostEntity post = resource.getPost();

        owner = KeyFactory.keyToString(post.getOwner());
        date = post.getDate();
        image = post.getImage();
        description = post.getDescription();
        likecount = post.getLikeCount();

        if (resource.hasCurrentUser()) {
            final LikeEntityManager likeManager = LikeEntityManager.get(TransactionContext.getCurrent());

            final UserEntity currentUser = resource.getCurrentUser();
            final LikeEntity like = likeManager.find(currentUser, post);

            liked = like != null;
        } else {
            liked = null;
        }
    }
}
