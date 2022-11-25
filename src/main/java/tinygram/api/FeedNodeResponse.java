package tinygram.api;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * An object to be serialized into a JSON value, encapsulating a feed node resource.
 *
 * <p> <b>Warning:</b> Should only be built from a {@link FeedResource} by using a
 * {@link FeedTransformer}.
 */
public class FeedNodeResponse {

    /**
     * The serialized key of the feed post entity.
     */
    public final String post;

    /**
     * Creates a feed node response object.
     *
     * @param postKey The key of the feed post entity.
     */
    public FeedNodeResponse(Key postKey) {
        post = KeyFactory.keyToString(postKey);
    }
}
