package tinygram.api.config;

import tinygram.datastore.UndefinedUserProvider;
import tinygram.datastore.UserEntity;

public class UserTransformer implements TypedEntityTransformer<UserEntity> {

    @Override
    public Response<UserEntity> transformTo(UserEntity entity) {
        return entity.getUserProvider() instanceof UndefinedUserProvider ? new AuthUserResponse(entity)
                : new BaseUserResponse(entity);
    }
}
