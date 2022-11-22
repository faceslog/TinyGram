package tinygram.api;

import tinygram.api.util.EntityResponse;
import tinygram.datastore.FollowEntity;
import tinygram.datastore.FollowEntityManager;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;

class UserResponse extends EntityResponse<UserEntity> {

    public final String name;
    public final String image;
    public final long followercount;
    public final long followingcount;
    public final long postcount;
    public final Boolean followed;

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
