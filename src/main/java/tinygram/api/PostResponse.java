package tinygram.api;

import java.util.Date;

import com.google.appengine.api.datastore.KeyFactory;

import tinygram.api.util.EntityResponse;
import tinygram.datastore.LikeEntity;
import tinygram.datastore.LikeEntityManager;
import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

class PostResponse extends EntityResponse<PostEntity> {

    public final String owner;
    public final Date date;
    public final String image;
    public final String description;
    public final long likecount;
    public final Boolean liked;

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
