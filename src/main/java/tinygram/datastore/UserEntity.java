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

    default boolean addFollow(UserEntity user) {
        return addFollow(user.getKey());
    }

    boolean addFollow(Key userKey);

    default boolean removeFollow(UserEntity user) {
        return removeFollow(user.getKey());
    }

    boolean removeFollow(Key userKey);

    long getFollowerCount();
}
