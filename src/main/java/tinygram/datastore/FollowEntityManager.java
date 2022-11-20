package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

public interface FollowEntityManager extends TypedEntityManager<FollowEntity> {

    static FollowEntityManager get() {
        return new FollowEntityManagerImpl();
    }

    FollowEntity register(UserEntity source, UserEntity target);

    FollowEntity find(Key source, Key target);

    default Iterator<FollowEntity> findAllFrom(UserEntity user) {
        return findAllFrom(user.getKey());
    }

    Iterator<FollowEntity> findAllFrom(Key userKey);

    default Iterator<FollowEntity> findAllTo(UserEntity user) {
        return findAllTo(user.getKey());
    }

    Iterator<FollowEntity> findAllTo(Key userKey);
}
