package tinygram.api.config;

import tinygram.api.UserApiSchema;
import tinygram.datastore.UserEntity;

public class UserTransformer implements EntityTransformer<UserEntity> {

    @Override
    public ResourceResponse<EntityResponse<UserEntity>> transformTo(UserEntity entity) {
        final EntityResponse<UserEntity> entityResponse = entity.getUserProvider().exists() ?
                new BaseUserResponse(entity) : new AuthUserResponse(entity);

        final ResourceResponse<EntityResponse<UserEntity>> resourceResponse = new ResourceResponse<>(entityResponse);

        resourceResponse.addLink("self", UserApiSchema.getPath(entity));

        return resourceResponse;
    }
}
