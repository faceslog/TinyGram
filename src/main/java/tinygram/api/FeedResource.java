package tinygram.api;

import tinygram.api.util.Resource;
import tinygram.datastore.Feed;

/**
 * A resource managed by the {@link FeedApi}, encapsulating a post feed.
 */
public class FeedResource extends Resource {

    /**
     * The post feed.
     */
    private final Feed feed;

    /**
     * Creates a post feed resource.
     *
     * @param feed The post feed.
     */
    public FeedResource(Feed feed) {
        this.feed = feed;
    }

    /**
     * Gets the post feed the resource represents.
     *
     * @return The post feed.
     */
    public Feed getFeed() {
        return feed;
    }
}
