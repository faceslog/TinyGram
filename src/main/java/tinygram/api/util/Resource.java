package tinygram.api.util;

import tinygram.datastore.UserEntity;

public abstract class Resource {

    private final UserEntity currentUser;

    public Resource() {
        this(null);
    }

    public Resource(UserEntity currentUser) {
        this.currentUser = currentUser;
    }

    public boolean hasCurrentUser() {
        return currentUser != null;
    }

    public UserEntity getCurrentUser() {
        return currentUser;
    }
}
