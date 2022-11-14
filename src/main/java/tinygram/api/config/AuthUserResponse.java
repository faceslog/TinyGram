package tinygram.api.config;

import tinygram.datastore.UserEntity;

public class AuthUserResponse extends EntityResponse<UserEntity> {

    public final String name;
    public final String image;
    public final long followercount;
    public final long followingcount;

    public AuthUserResponse(UserEntity entity) {
        super(entity);

        name = entity.getName();
        image = entity.getImage();
        followercount = entity.getFollowerCount();
        followingcount = entity.getFollowingCount();
    }
}
