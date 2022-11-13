package tinygram.datastore;

import java.util.Collection;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

public interface PostEntity extends TypedEntity, UserAware {

    static final String KIND = "Post";

    static final String FIELD_OWNER = "owner";
    static final String FIELD_DATE = "date";
    static final String FIELD_IMAGE = "image";
    static final String FIELD_DESCRIPTION = "description";
    static final String FIELD_LIKES = "likes";
    static final String FIELD_LIKE_COUNT = "likecount";

    @Override
    default String getKind() {
        return KIND;
    }

    Key getOwner();

    Date getDate();

    String getImage();

    void setImage(String image);

    String getDescription();

    void setDescription(String description);

    Collection<Key> getLikes();

    default boolean likedBy(UserEntity user) {
        return likedBy(user.getKey());
    }

    default boolean likedBy(Key userKey) {
        return getLikes().contains(userKey);
    }

    default boolean addLike(UserEntity user) {
        return addLike(user.getKey());
    }

    boolean addLike(Key userKey);

    default boolean removeLike(UserEntity user) {
        return removeLike(user.getKey());
    }

    boolean removeLike(Key userKey);

    long getLikeCount();
}
