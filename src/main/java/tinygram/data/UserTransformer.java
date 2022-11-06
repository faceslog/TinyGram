package tinygram.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class UserTransformer extends TypedEntityTransformer<UserEntity, UserTransformer.Response> {

    public static class Response extends TypedEntityTransformer.Response {
        public final List<String> following;

        public Response(UserEntity entity) {
            super(entity);

            final Set<Key> followingKeys = entity.getFollowing();
            following = new ArrayList<>(followingKeys.size());
            followingKeys.forEach(key -> following.add(KeyFactory.keyToString(key)));
        }
    }

    @Override
    public Response transformTo(UserEntity entity) {
        return new Response(entity);
    }
}
