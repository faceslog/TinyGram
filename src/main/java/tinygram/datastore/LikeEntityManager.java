package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

public interface LikeEntityManager extends TypedEntityManager<LikeEntity> {

    static LikeEntityManager get() {
        return new LikeEntityManagerImpl();
    }

    LikeEntity register(UserEntity user, PostEntity post);

    default LikeEntity find(UserEntity user, PostEntity post) {
        return find(user, post.getKey());
    }

    default LikeEntity find(Key userKey, PostEntity post) {
        return find(userKey, post.getKey());
    }

    default LikeEntity find(UserEntity user, Key postKey) {
        return find(user.getKey(), postKey);
    }

    LikeEntity find(Key userKey, Key postKey);

    default Iterator<LikeEntity> findAllFrom(UserEntity user) {
        return findAllFrom(user.getKey());
    }

    Iterator<LikeEntity> findAllFrom(Key userKey);

    default Iterator<LikeEntity> findAllTo(PostEntity post) {
        return findAllTo(post.getKey());
    }

    Iterator<LikeEntity> findAllTo(Key postKey);
}
