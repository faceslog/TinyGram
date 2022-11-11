package tinygram.api.config;

import tinygram.datastore.UserEntity;

public class AuthUserResponse extends TypedEntityTransformer.Response<UserEntity> {

    public AuthUserResponse(UserEntity entity) {
        super(entity);
    }
}
