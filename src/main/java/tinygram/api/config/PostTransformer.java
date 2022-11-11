package tinygram.api.config;

import tinygram.datastore.PostEntity;

public class PostTransformer implements TypedEntityTransformer<PostEntity> {

    @Override
    public Response<PostEntity> transformTo(PostEntity entity) {
        return new BasePostResponse(entity);
    }
}
