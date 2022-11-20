package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

public interface PostEntityManager extends TypedEntityManager<PostEntity> {

    static PostEntityManager get() {
        return new PostEntityManagerImpl();
    }

    PostEntity register(UserEntity user, String image, String description);

    default PostEntity findLatest(UserEntity user) {
        return findLatest(user.getKey());
    }

    PostEntity findLatest(Key userKey);

    default Iterator<PostEntity> findAll(UserEntity user) {
        return findAll(user.getKey());
    }

    Iterator<PostEntity> findAll(Key userKey);
}
