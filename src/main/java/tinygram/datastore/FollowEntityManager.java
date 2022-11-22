package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

public interface FollowEntityManager extends TypedEntityManager<FollowEntity> {

    static FollowEntityManager get(TransactionContext context) {
        return new FollowEntityManagerImpl(context);
    }

    FollowEntity register(UserEntity source, UserEntity target);

    default FollowEntity find(UserEntity source, UserEntity target) {
        return find(source, target.getKey());
    }

    default FollowEntity find(Key sourceKey, UserEntity target) {
        return find(sourceKey, target.getKey());
    }

    default FollowEntity find(UserEntity source, Key targetKey) {
        return find(source.getKey(), targetKey);
    }

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
