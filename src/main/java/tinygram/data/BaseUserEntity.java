package tinygram.data;

import java.util.Collection;
import java.util.HashSet;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiTransformer;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import tinygram.Util;

@ApiTransformer(BaseUserTransformer.class)
public class BaseUserEntity extends UserAwareEntity implements UserEntity {

    public BaseUserEntity(User user) {
        super(new UndefinedUserProvider(), Util.DEBUG ? "test" : user.getId());

        setProperty(FIELD_ID, Util.DEBUG ? "test" : user.getId());
        setProperty(FIELD_FOLLOWING, new HashSet<>());

        setUserProvider(new BaseUserProvider(this));
    }

    public BaseUserEntity(UserProvider userProvider, Entity raw) {
        super(userProvider, raw);
    }

    @Override
    public Collection<Key> getFollowing() {
        final Collection<Key> following = getProperty(FIELD_FOLLOWING);
        return following == null ? new HashSet<>() : following;
    }

    @Override
    public boolean follow(Key userKey) {
        KindException.ensure(KIND, userKey);
        if (getKey().equals(userKey) && !Util.DEBUG) {
            throw new IllegalArgumentException("Trying to follow itself.");
        }

        final Collection<Key> following = getFollowing();
        if (following.contains(userKey)) {
            return false;
        }

        following.add(userKey);
        setProperty(FIELD_FOLLOWING, following);
        return true;
    }

    @Override
    public boolean unfollow(Key userKey) {
        KindException.ensure(KIND, userKey);

        final Collection<Key> following = getFollowing();
        if (!following.contains(userKey)) {
            return false;
        }

        following.remove(userKey);
        setProperty(FIELD_FOLLOWING, following);
        return true;
    }
}
