package tinygram.data;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.google.api.server.spi.config.ApiTransformer;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

@ApiTransformer(BasePostTransformer.class)
public class BasePostEntity extends UserAwareEntity implements PostEntity {

    protected static final String KIND = "Post";

    protected static final String FIELD_OWNER = "owner";
    protected static final String FIELD_DATE = "date";
    protected static final String FIELD_IMAGE = "image";
    protected static final String FIELD_LIKES = "likes";
    protected static final String FIELD_LIKE_COUNT = "likecount";

    public BasePostEntity(UserProvider userProvider, UserEntity owner, String image) {
        super(userProvider);

        setProperty(FIELD_OWNER, owner.getKey());
        setProperty(FIELD_DATE, new Date());
        setProperty(FIELD_IMAGE, image);
        setProperty(FIELD_LIKES, new HashSet<>());
        setProperty(FIELD_LIKE_COUNT, 0);
    }

    public BasePostEntity(UserProvider userProvider, Entity raw) {
        super(userProvider, raw);
    }

    @Override
    public Key getOwner() {
        return getProperty(FIELD_OWNER);
    }

    @Override
    public Date getDate() {
        return getProperty(FIELD_DATE);
    }

    @Override
    public String getImage() {
        return getProperty(FIELD_IMAGE);
    }

    @Override
    public Collection<Key> getLikes() {
        final Collection<Key> likes = getProperty(FIELD_LIKES);
        return likes == null ? new HashSet<>() : likes;
    }

    @Override
    public boolean like(Key userKey) {
        KindException.ensure(UserEntity.KIND, userKey);
        /* Sur insta un utilisateur peut like ses propres posts
            if (getOwner().getKey().equals(userKey)) {
                throw new IllegalArgumentException("Trying to like its own post.");
            }
        */

        final Collection<Key> likes = getLikes();
        if (likes.contains(userKey)) {
            return false;
        }

        likes.add(userKey);
        final long likeCount = getLikeCount() + 1;

        setProperty(FIELD_LIKES, likes);
        setProperty(FIELD_LIKE_COUNT, likeCount);

        return true;
    }

    @Override
    public boolean unlike(Key userKey) {
        KindException.ensure(UserEntity.KIND, userKey);

        final Collection<Key> likes = getLikes();
        if (!likes.contains(userKey)) {
            return false;
        }

        likes.remove(userKey);
        final long likeCount = getLikeCount() - 1;

        setProperty(FIELD_LIKES, likes);
        setProperty(FIELD_LIKE_COUNT, likeCount);

        return true;
    }

    @Override
    public long getLikeCount() {
        return getProperty(FIELD_LIKE_COUNT);
    }
}
