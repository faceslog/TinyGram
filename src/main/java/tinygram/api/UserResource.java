package tinygram.api;

import tinygram.datastore.UserEntity;

public class UserResource extends Resource {

    private final UserEntity user;

    public UserResource(UserEntity user) {
        this.user = user;
    }

    public UserResource(UserEntity currentUser, UserEntity user) {
        super(currentUser);

        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
