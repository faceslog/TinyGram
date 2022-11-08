package tinygram.data;

import java.util.Collection;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

public interface PostEntity extends TypedEntity {

    static final String KIND = "Post";

    static final String FIELD_OWNER = "owner";
    static final String FIELD_DATE = "date";
    static final String FIELD_IMAGE = "image";
    static final String FIELD_LIKES = "likes";
    static final String FIELD_LIKE_COUNT = "likecount";

    @Override
    default String getKind() {
        return KIND;
    }

    Key getOwner();

    Date getDate();

    String getImage();

    Collection<Key> getLikes();

    default boolean likedBy(UserEntity user) {
        return likedBy(user.getKey());
    }

    default boolean likedBy(Key userKey) {
        return getLikes().contains(userKey);
    }

    default boolean like(UserEntity user) {
        return like(user.getKey());
    }

    boolean like(Key userKey);

    default boolean unlike(UserEntity user) {
        return unlike(user.getKey());
    }

    boolean unlike(Key userKey);

    long getLikeCount();
}
