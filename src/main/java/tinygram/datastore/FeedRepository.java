package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

public interface FeedRepository extends Repository<FeedNodeEntity> {

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
