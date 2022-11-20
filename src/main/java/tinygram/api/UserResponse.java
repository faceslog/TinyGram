package tinygram.api;

import tinygram.datastore.FollowEntity;
import tinygram.datastore.FollowEntityManager;
import tinygram.datastore.UserEntity;

class UserResponse extends EntityResponse<UserEntity> {

    private static final FollowEntityManager followManager = FollowEntityManager.get();

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
            final UserEntity currentUser = resource.getCurrentUser();
            final FollowEntity follow = followManager.find(currentUser.getKey(), user.getKey());
    
            followed = follow != null;
        } else {
            followed = null;
        }
    }
}
