package tinygram.api;

import tinygram.api.util.EntityResponse;
import tinygram.datastore.FollowEntity;
import tinygram.datastore.FollowEntityManager;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

/**
 * An object to be serialized into a JSON value, encapsulating a user resource.
 *
 * <p> <b>Warning:</b> Should only be built from a {@link UserResource} by using a
 * {@link UserTransformer}.
 */
class UserResponse extends EntityResponse<UserEntity> {

    /**
     * The user name.
     */
    public final String name;
    /**
     * The user profile picture.
     */
    public final String image;
    /**
     * The number of followers.
     */
    public final long followercount;
    /**
     * The number of people the user is following.
     */
    public final long followingcount;
    /**
     * The number of posts written.
     */
    public final long postcount;
    /**
     * Whether the currently logged user is one of the users's follower.
     */
    public final Boolean followed;

    /**
     * Creates a user response object.
     *
     * @param resource The user resource.
     */
    public UserResponse(UserResource resource) {
        super(resource.getUser());

        final UserEntity user = resource.getUser();

        name = user.getName();
        image = user.getImage();
        followercount = user.getFollowerCount();
        followingcount = user.getFollowingCount();
        postcount = user.getPostCount();

        if (resource.hasCurrentUser()) {
            final FollowEntityManager followManager = FollowEntityManager.get(TransactionContext.getCurrent());

            final UserEntity currentUser = resource.getCurrentUser();
            final FollowEntity follow = followManager.find(currentUser, user);

            followed = follow != null;
        } else {
            followed = null;
        }
    }
}
