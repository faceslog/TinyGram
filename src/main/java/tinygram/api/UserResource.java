package tinygram.api;

import tinygram.api.util.Resource;
import tinygram.datastore.UserEntity;

/**
 * A resource managed by the {@link UserApi}, encapsulating a user entity.
 */
public class UserResource extends Resource {

    /**
     * The user entity.
     */
    private final UserEntity user;

    /**
     * Creates a user resource.
     *
     * @param user The user entity.
     */
    public UserResource(UserEntity user) {
        this.user = user;
    }

    /**
     * Creates a user resource, aware of the currently logged user.
     *
     * @param currentUser The currently logged user entity.
     * @param user        The user entity.
     */
    public UserResource(UserEntity currentUser, UserEntity user) {
        super(currentUser);

        this.user = user;
    }

    /**
     * Gets the user the resource represents.
     *
     * @return The user entity.
     */
    public UserEntity getUser() {
        return user;
    }
}
