package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

public interface PostRepository extends Repository<PostEntity> {

    PostEntity register(UserEntity user, String image, String description);

    default PostEntity findLatest(UserEntity user) {
        return findLatest(user.getKey());
    }

    PostEntity findLatest(Key userKey);
}
