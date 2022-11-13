package tinygram.api.config;

import tinygram.api.UserApiSchema;
import tinygram.datastore.UserEntity;

public class UserTransformer implements EntityTransformer<UserEntity> {

    @Override
    public ResourceResponse<UserEntity> transformTo(UserEntity entity) {
        final EntityResponse<UserEntity> entityResponse = entity.getUserProvider().exists() ?
                new BaseUserResponse(entity) : new AuthUserResponse(entity);

        final ResourceResponse<UserEntity> resourceResponse = new ResourceResponse<>(entityResponse);

        resourceResponse.addLink("self", UserApiSchema.getPath(entity));

        return resourceResponse;
    }
}
