package tinygram.datastore;

import java.util.Collection;
import java.util.HashSet;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import tinygram.Config;

class BaseUserEntity extends AbstractUserAwareEntity implements UserEntity {

    public BaseUserEntity(User user, String name, String image) {
        super(new UndefinedUserProvider(), Config.DEBUG ? "test" : user.getId());

        setProperty(FIELD_ID, Config.DEBUG ? "test" : user.getId());
        setProperty(FIELD_NAME, name);
        setProperty(FIELD_IMAGE, image);
        setProperty(FIELD_FOLLOWERS, new HashSet<>());
        setProperty(FIELD_FOLLOWER_COUNT, 0);
        setProperty(FIELD_FOLLOWING_COUNT, 0);

        setUserProvider(new BaseUserProvider(this));
    }

    public BaseUserEntity(UserProvider userProvider, Entity raw) {
        super(userProvider, raw);
    }

    @Override
    public String getId() {
        return getProperty(FIELD_ID);
    }

    @Override
    public String getName() {
        return getProperty(FIELD_NAME);
    }

    @Override
    public void setName(String name) {
        setProperty(FIELD_NAME, name);
    }

    @Override
    public String getImage() {
        return getProperty(FIELD_IMAGE);
    }

    @Override
    public void setImage(String image) {
        setProperty(FIELD_IMAGE, image);
    }

    @Override
    public Collection<Key> getFollowers() {
        final Collection<Key> followers = getProperty(FIELD_FOLLOWERS);
        return followers == null ? new HashSet<>() : followers;
    }

    @Override
    public boolean addFollow(Key userKey) {
        KindException.ensure(KIND, userKey);

        if (getKey().equals(userKey) && !Config.DEBUG) {
            throw new IllegalArgumentException("Trying to follow itself.");
        }

        final Collection<Key> following = getFollowers();
        if (following.contains(userKey)) {
            return false;
        }

        following.add(userKey);
        final long followerCount = getFollowerCount() + 1;

        setProperty(FIELD_FOLLOWERS, following);
        setProperty(FIELD_FOLLOWER_COUNT, followerCount);

        // Don't scale but an user should not be able to spam follow hundreds of people
        try {

            final UserEntity user = new BaseUserRepository().get(userKey);
            user.incrementFollowing();
            user.persist();

        } catch(EntityNotFoundException e) {
            throw new IllegalStateException("Missing User", e);
        }
        // --------------

        return true;
    }

    @Override
    public boolean removeFollow(Key userKey) {
        KindException.ensure(KIND, userKey);

        final Collection<Key> following = getFollowers();
        if (!following.contains(userKey)) {
            return false;
        }

        following.remove(userKey);
        final long followerCount = getFollowerCount() - 1;

        setProperty(FIELD_FOLLOWERS, following);
        setProperty(FIELD_FOLLOWER_COUNT, followerCount);

        // Don't scale but an user should not be able to spam follow hundreds of people
        try {

            final UserEntity user = new BaseUserRepository().get(userKey);
            user.decrementFollowing();
            user.persist();

        } catch(EntityNotFoundException e) {
            throw new IllegalStateException("Missing User", e);
        }
        // --------------

        return true;
    }

    @Override
    public long getFollowerCount() {
        return getProperty(FIELD_FOLLOWER_COUNT);
    }

    @Override
    public long getFollowingCount() {
        return getProperty(FIELD_FOLLOWING_COUNT);
    }

    @Override
    public void incrementFollowing() {
        final Long followingCount = getProperty(FIELD_FOLLOWING_COUNT);
        setProperty(FIELD_FOLLOWING_COUNT, followingCount + 1);
    }

    @Override
    public void decrementFollowing() {
        final Long followingCount = getProperty(FIELD_FOLLOWING_COUNT);
        setProperty(FIELD_FOLLOWING_COUNT, followingCount - 1);
    }
}
