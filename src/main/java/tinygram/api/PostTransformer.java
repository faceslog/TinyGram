package tinygram.api;

import tinygram.api.util.ResourceResponse;
import tinygram.api.util.ResourceTransformer;
import tinygram.datastore.PostEntity;

/**
 * An interface to convert a {@link PostResource} to an encapsulated {@link PostResponse}.
 */
public class PostTransformer implements ResourceTransformer<PostResource, PostResponse> {

    @Override
    public ResourceResponse<PostResponse> transformTo(PostResource resource) {
        final PostEntity post = resource.getPost();

        final PostResponse postResponse = new PostResponse(resource);
        final ResourceResponse<PostResponse> resourceResponse = new ResourceResponse<>(postResponse);

        resourceResponse.addLink("self", PostApiSchema.getPath(post));
        resourceResponse.addLink("owner", UserApiSchema.getPath(post.getOwner()));

        return resourceResponse;
    }
}
