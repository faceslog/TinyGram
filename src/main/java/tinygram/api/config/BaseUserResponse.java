package tinygram.api.config;

import tinygram.datastore.UserEntity;

public class BaseUserResponse extends AuthUserResponse {

    public final boolean followed;

    public BaseUserResponse(UserEntity entity) {
        super(entity);

        final UserEntity baseUser = entity.getUserProvider().get();
        followed = baseUser.follows(entity);
    }
}
