package tinygram.api.config;

import tinygram.api.UserApiSchema;
import tinygram.datastore.UserEntity;

public class UserTransformer implements EntityTransformer<UserEntity> {

    @Override
    public ResourceResponse<UserEntity> transformTo(UserEntity entity) {
        EntityResponse<UserEntity> entityResponse;
        try {
            entity.getUserProvider().get();
            entityResponse = new BaseUserResponse(entity);
        } catch (final IllegalStateException e) {
            entityResponse = new AuthUserResponse(entity);
        }

        final ResourceResponse<UserEntity> resourceResponse = new ResourceResponse<>(entityResponse);

        resourceResponse.addLink("self", UserApiSchema.getPath(entity));

        return resourceResponse;
    }
}
