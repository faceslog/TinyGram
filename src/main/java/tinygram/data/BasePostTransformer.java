package tinygram.data;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

public class BasePostTransformer implements TypedEntityTransformer<BasePostEntity, BasePostTransformer.Response> {

    public static class Response extends TypedEntityTransformer.Response {

        public final Key owner;
        public final Date date;
        public final String image;
        public final long likecount;
        public final boolean liked;

        public Response(BasePostEntity entity) {
            super(entity);

            owner = entity.getOwner();
            date = entity.getDate();
            image = entity.getImage();
            likecount = entity.getLikeCount();
            liked = entity.likedBy(entity.getUserProvider().getKey());
        }
    }

    @Override
    public Response transformTo(BasePostEntity entity) {
        return new Response(entity);
    }
}
