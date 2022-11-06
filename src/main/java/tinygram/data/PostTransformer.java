package tinygram.data;

import java.util.Date;

public class PostTransformer extends TypedEntityTransformer<PostEntity, PostTransformer.Response> {

    public static class Response extends TypedEntityTransformer.Response {
        public final UserEntity owner;
        public final Date date;
        public final String image;
        public final long likecount;

        public Response(PostEntity entity) {
            super(entity);
            owner = entity.getOwner();
            date = entity.getDate();
            image = entity.getImage();
            likecount = entity.getLikeCount();
        }
    }

    @Override
    public Response transformTo(PostEntity entity) {
        return new Response(entity);
    }
}
