package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

public interface FeedRepository extends Repository<FeedNodeEntity> {

    default FeedNodeEntity register(UserEntity user, PostEntity post) {
        return register(user.getKey(), post);
    }

    FeedNodeEntity register(Key userKey, PostEntity post);

    Feed findAll(String page);

    default Feed findAll(UserEntity user, String page) {
        return findAll(user.getKey(), page);
    }

    Feed findAll(Key userKey, String page);

    default Feed findAllFrom(UserEntity user, String page) {
        return findAllFrom(user.getKey(), page);
    }

    Feed findAllFrom(Key userKey, String page);
}
