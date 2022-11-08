package tinygram.data;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public interface PostRepository {

    PostEntity register(UserEntity user, String image);

    PostEntity get(Key key) throws EntityNotFoundException;

    FeedResponse<PostEntity> findFrom(UserEntity user, String page);

    FeedResponse<PostEntity> findFromFollowed(UserEntity user, String page);
}
