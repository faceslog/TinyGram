package tinygram.data;

import java.util.HashSet;
import java.util.Set;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiTransformer;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

@ApiTransformer(UserTransformer.class)
public final class UserEntity extends AbstractTypedEntity {

    protected static final String KIND = "User";
    protected static final String FIELD_ID = "id";
    protected static final String FIELD_FOLLOWING = "following";

    public UserEntity(User user) {
        super();

        // setProperty(FIELD_ID, user.getId());
        setProperty(FIELD_ID, "test");
        setProperty(FIELD_FOLLOWING, new HashSet<>());
    }

    public UserEntity(Entity raw) {
        super(raw);
    }

    @Override
    public String getKind() {
        return KIND;
    }

    protected Set<Key> getFollowing() {
        final Set<Key> following = getProperty(FIELD_FOLLOWING);
        return following == null ? new HashSet<>() : following;
    }

    public boolean follows(UserEntity user) {
        return follows(user.getKey());
    }

    public boolean follows(Key userKey) {
        return getFollowing().contains(userKey);
    }

    public boolean follow(UserEntity user) {
        return follow(user.getKey());
    }

    public boolean follow(Key userKey) {
        KindException.ensure(KIND, userKey);
        if (getKey().equals(userKey)) {
            //throw new IllegalArgumentException("Trying to follow itself.");
        }
        return getFollowing().add(userKey);
    }

    public boolean unfollow(UserEntity user) {
        return unfollow(user.getKey());
    }

    public boolean unfollow(Key userKey) {
        KindException.ensure(KIND, userKey);
        return getFollowing().remove(userKey);
    }
}
