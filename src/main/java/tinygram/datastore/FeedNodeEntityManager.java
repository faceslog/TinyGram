package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

public interface FeedNodeEntityManager extends TypedEntityManager<FeedNodeEntity> {

    static FeedNodeEntityManager get(TransactionContext context) {
        return new FeedNodeEntityManagerImpl(context);
    }

    default FeedNodeEntity register(UserEntity user, PostEntity post) {
        return register(user.getKey(), post);
    }

    FeedNodeEntity register(Key userKey, PostEntity post);

    default Iterator<FeedNodeEntity> findAllOfUser(UserEntity user) {
        return findAllOfUser(user.getKey());
    }

    Iterator<FeedNodeEntity> findAllOfUser(Key userKey);

    default Iterator<FeedNodeEntity> findAllOfPost(PostEntity post) {
        return findAllOfPost(post.getKey());
    }

    Iterator<FeedNodeEntity> findAllOfPost(Key postKey);

    Feed findPaged(String page);

    default Feed findPaged(UserEntity user, String page) {
        return findPaged(user.getKey(), page);
    }

    Feed findPaged(Key userKey, String page);

    default Feed findPagedFrom(UserEntity user, String page) {
        return findPagedFrom(user.getKey(), page);
    }

    Feed findPagedFrom(Key userKey, String page);
}
