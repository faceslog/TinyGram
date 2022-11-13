package tinygram.api.config;

import tinygram.datastore.UserEntity;

public class BaseUserResponse extends AuthUserResponse {

    public final boolean followed;

    public BaseUserResponse(UserEntity entity) {
        super(entity);

        followed = entity.followedBy(entity.getUserProvider().get());
    }
}
