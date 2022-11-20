package tinygram.api;

import tinygram.datastore.UserEntity;

public class UserTransformer implements ResourceTransformer<UserResource, UserResponse> {

    @Override
    public ResourceResponse<UserResponse> transformTo(UserResource resource) {
        final UserEntity user = resource.getUser();

        final UserResponse userResponse = new UserResponse(resource);
        final ResourceResponse<UserResponse> resourceResponse = new ResourceResponse<>(userResponse);

        resourceResponse.addLink("self", UserApiSchema.getPath(user));

        return resourceResponse;
    }
}
