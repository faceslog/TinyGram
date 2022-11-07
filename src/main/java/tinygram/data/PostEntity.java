package tinygram.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.api.server.spi.config.ApiTransformer;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

@ApiTransformer(PostTransformer.class)
public final class PostEntity extends AbstractTypedEntity {

    protected static final String KIND = "Post";
    protected static final String FIELD_OWNER = "owner";
    protected static final String FIELD_DATE = "date";
    protected static final String FIELD_IMAGE = "image";
    protected static final String FIELD_LIKES = "likes";
    protected static final String FIELD_LIKE_COUNT = "likecount";

    public PostEntity(UserEntity user, String image) {
        super();

        setProperty(FIELD_OWNER, user.getKey());
        setProperty(FIELD_DATE, new Date());
        setProperty(FIELD_IMAGE, image);
        setProperty(FIELD_LIKES, new HashSet<>());
        setProperty(FIELD_LIKE_COUNT, 0);
    }

    public PostEntity(Entity raw) {
        super(raw);
    }

    @Override
    public String getKind() {
        return KIND;
    }

    public UserEntity getOwner() {
        try {
            return UserRepository.get(getProperty(FIELD_OWNER));
        } catch (final EntityNotFoundException e) {
            throw new IllegalStateException("Owner of post not found.");
        }
    }

    public Date getDate() {
        return getProperty(FIELD_DATE);
    }

    public String getImage() {
        return getProperty(FIELD_IMAGE);
    }

    protected Set<Key> getLikes() {
        final Set<Key> likes = getProperty(FIELD_LIKES);
        return likes == null ? new HashSet<>() : likes;
    }

    public boolean likedBy(UserEntity user) {
        return likedBy(user.getKey());
    }

    public boolean likedBy(Key userKey) {
        return getLikes().contains(userKey);
    }

    public boolean like(UserEntity user) {
        return like(user.getKey());
    }

    public boolean like(Key userKey) {
        KindException.ensure(UserEntity.KIND, userKey);
        /* Sur insta un utilisateur peut like ses propres posts
            if (getOwner().getKey().equals(userKey)) {
                throw new IllegalArgumentException("Trying to like its own post.");
            }
        */
        return getLikes().add(userKey);
    }

    public boolean unlike(UserEntity user) {
        return unlike(user.getKey());
    }

    public boolean unlike(Key userKey) {
        KindException.ensure(UserEntity.KIND, userKey);
        return getLikes().remove(userKey);
    }

    public long getLikeCount() {
        return getProperty(FIELD_LIKE_COUNT);
    }
}
