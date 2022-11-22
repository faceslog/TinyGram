package tinygram.api;

import tinygram.api.util.Resource;
import tinygram.datastore.Feed;

public class FeedResource extends Resource {

    private final Feed feed;

    public FeedResource(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }
}
