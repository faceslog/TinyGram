package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

public interface PostEntityManager extends TypedEntityManager<PostEntity> {

    static PostEntityManager get(TransactionContext context) {
        return new PostEntityManagerImpl(context);
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
