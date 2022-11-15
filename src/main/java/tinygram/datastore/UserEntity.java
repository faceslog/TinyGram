package tinygram.datastore;

import java.util.Collection;

import com.google.appengine.api.datastore.Key;

public interface UserEntity extends TypedEntity, UserAware {

    static final String KIND = "User";

    static final String FIELD_ID = "id";
    static final String FIELD_NAME = "name";
    static final String FIELD_IMAGE = "image";
    static final String FIELD_FOLLOWERS = "followers";
    static final String FIELD_FOLLOWER_COUNT = "followercount";
    static final String FIELD_FOLLOWING_COUNT = "followingcount";

    @Override
    default String getKind() {
        return KIND;
    }

    String getId();

    String getName();

    void setName(String name);

    String getImage();

    void setImage(String image);

    Collection<Key> getFollowers();

    default boolean followedBy(UserEntity user) {
        return followedBy(user.getKey());
    }

    default boolean followedBy(Key userKey) {
        return getFollowers().contains(userKey);
    }

    boolean addFollow(UserEntity user);

    boolean removeFollow(UserEntity user);

    long getFollowerCount();

    void incrementFollowing();

    void decrementFollowing();

    long getFollowingCount();
}
