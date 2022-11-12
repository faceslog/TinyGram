package tinygram.api.config;

import tinygram.datastore.UserEntity;

public class UserTransformer implements TypedEntityTransformer<UserEntity> {

    @Override
    public Response<UserEntity> transformTo(UserEntity entity) {
        try {
            entity.getUserProvider().get();
        } catch (final IllegalStateException e) {
            return new AuthUserResponse(entity);
        }
        return new BaseUserResponse(entity);
    }
}
