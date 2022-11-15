package tinygram.api.config;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class FeedNodeResponse {

    public final String post;

    public FeedNodeResponse(Key postKey) {
        post = KeyFactory.keyToString(postKey);
    }
}
