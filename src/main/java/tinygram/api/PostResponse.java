package tinygram.api;

import java.util.Date;

import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.PostEntity;
import tinygram.datastore.UserEntity;

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
            final UserEntity currentUser = resource.getCurrentUser();

            liked = post.likedBy(currentUser);
        } else {
            liked = null;
        }
    }
}
