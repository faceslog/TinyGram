package tinygram.datastore;

import java.util.Collection;

import com.google.appengine.api.datastore.Key;

public interface UserEntity extends TypedEntity, UserAware {

    static final String KIND = "User";

    static final String FIELD_ID = "id";
    static final String FIELD_FOLLOWING = "following";

    @Override
    default String getKind() {
        return KIND;
    }

    Collection<Key> getFollowing();

    default boolean follows(UserEntity user) {
        return follows(user.getKey());
    }

    default boolean follows(Key userKey) {
        return getFollowing().contains(userKey);
    }

    default boolean follow(UserEntity user) {
        return follow(user.getKey());
    }

    boolean follow(Key userKey);

    default boolean unfollow(UserEntity user) {
        return unfollow(user.getKey());
    }

    boolean unfollow(Key userKey);
}
