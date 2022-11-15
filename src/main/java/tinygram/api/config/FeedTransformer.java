package tinygram.api.config;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;

import tinygram.api.FeedApiSchema;
import tinygram.api.PostApiSchema;
import tinygram.datastore.Feed;

public class FeedTransformer implements ResourceTransformer<Feed, List<ResourceResponse<FeedNodeResponse>>> {

    @Override
    public ResourceResponse<List<ResourceResponse<FeedNodeResponse>>> transformTo(Feed feed) {
        final List<ResourceResponse<FeedNodeResponse>> listResponse = new ArrayList<>(feed.getPosts().size());
        final ResourceResponse<List<ResourceResponse<FeedNodeResponse>>> resourceResponse = new ResourceResponse<>(listResponse);

        for (final Key postKey : feed.getPosts()) {
            final FeedNodeResponse feedNodeResponse = new FeedNodeResponse(postKey);
            final ResourceResponse<FeedNodeResponse> feedNodeResourceResponse = new ResourceResponse<>(feedNodeResponse);

            feedNodeResourceResponse.addLink("self", PostApiSchema.getPath(postKey));

            listResponse.add(feedNodeResourceResponse);
        }

        resourceResponse.addLink("next",
                feed.isAboutFollowed() ? FeedApiSchema.getFollowedPath(feed.getUser(), feed.getNextPage()) :
                feed.getUser() != null ? FeedApiSchema.getFromPath(feed.getUser(), feed.getNextPage()) :
                FeedApiSchema.getGlobalPath(feed.getNextPage())
        );

        return resourceResponse;
    }
}
