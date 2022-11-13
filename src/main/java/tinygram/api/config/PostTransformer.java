package tinygram.api.config;

import tinygram.api.PostApiSchema;
import tinygram.api.UserApiSchema;
import tinygram.datastore.PostEntity;

public class PostTransformer implements EntityTransformer<PostEntity> {

    @Override
    public ResourceResponse<PostEntity> transformTo(PostEntity entity) {
        final EntityResponse<PostEntity> entityResponse = new BasePostResponse(entity);
        final ResourceResponse<PostEntity> resourceResponse = new ResourceResponse<>(entityResponse);

        resourceResponse.addLink("self", PostApiSchema.getPath(entity));
        resourceResponse.addLink("owner", UserApiSchema.getPath(entity.getOwner()));

        return resourceResponse;
    }
}
