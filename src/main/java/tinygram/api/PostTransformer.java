package tinygram.api;

import tinygram.datastore.PostEntity;

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
