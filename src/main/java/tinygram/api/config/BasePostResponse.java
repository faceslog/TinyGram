package tinygram.api.config;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.PostEntity;

public class BasePostResponse extends EntityResponse<PostEntity> {

    public final Key owner;
    public final Date date;
    public final String image;
    public final String description;
    public final long likecount;
    public final boolean liked;

    public BasePostResponse(PostEntity entity) {
        super(entity);

        owner = entity.getOwner();
        date = entity.getDate();
        image = entity.getImage();
        description = entity.getDescription();
        likecount = entity.getLikeCount();
        liked = entity.likedBy(entity.getUserProvider().get());
    }
}
