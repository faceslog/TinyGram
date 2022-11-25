package tinygram.api.util;

import tinygram.datastore.UserEntity;

/**
 * A resource managed by an API.
 */
public abstract class Resource {

    /**
     * The currently logged user entity.
     */
    private final UserEntity currentUser;

    /**
     * Creates a resource.
     */
    public Resource() {
        this(null);
    }

    /**
     * Creates a resource, aware of the currently logged user.
     *
     * @param currentUser The currently logged user entity.
     */
    public Resource(UserEntity currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Indicates whether the resource is aware of the currently logged user.
     *
     * @return {@code true} if a logged user has been provided, {@code false} otherwise.
     */
    public boolean hasCurrentUser() {
        return currentUser != null;
    }

    /**
     * Gets the logged user this resource is aware of.
     *
     * @return The user entity provided to this resource, {@code null} if none has been provided.
     */
    public UserEntity getCurrentUser() {
        return currentUser;
    }
}
