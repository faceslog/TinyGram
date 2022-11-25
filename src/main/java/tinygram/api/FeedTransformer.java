package tinygram.api;

import java.util.ArrayList;
import java.util.Collection;

import com.google.appengine.api.datastore.Key;

import tinygram.api.util.ResourceResponse;
import tinygram.api.util.ResourceTransformer;
import tinygram.datastore.Feed;

/**
 * An interface to convert a {@link FeedResource} to a encapsulated collection of encapsulated
 * {@link FeedNodeResponse}.
 */
public class FeedTransformer implements ResourceTransformer<FeedResource,
        Collection<ResourceResponse<FeedNodeResponse>>> {

    @Override
    public ResourceResponse<Collection<ResourceResponse<FeedNodeResponse>>> transformTo(FeedResource resource) {
        final Feed feed = resource.getFeed();

        final Collection<ResourceResponse<FeedNodeResponse>> listResponse =
                new ArrayList<>(feed.getPosts().size());
        final ResourceResponse<Collection<ResourceResponse<FeedNodeResponse>>> resourceResponse =
                new ResourceResponse<>(listResponse);

        for (final Key postKey : feed.getPosts()) {
            final FeedNodeResponse feedNodeResponse = new FeedNodeResponse(postKey);
            final ResourceResponse<FeedNodeResponse> feedNodeResourceResponse =
                    new ResourceResponse<>(feedNodeResponse);

            feedNodeResourceResponse.addLink("self", PostApiSchema.getPath(postKey));

            listResponse.add(feedNodeResourceResponse);
        }

        final String nextPage = feed.getNextPage();
        if (nextPage != null) {
            resourceResponse.addLink("next",
                    feed.isAboutFollowed() ? FeedApiSchema.getFollowedPath(feed.getUser(), feed.getNextPage()) :
                    feed.getUser() != null ? FeedApiSchema.getFromPath(feed.getUser(), feed.getNextPage()) :
                    FeedApiSchema.getGlobalPath(feed.getNextPage())
            );
        }

        return resourceResponse;
    }
}
