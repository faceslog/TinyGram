package tinygram.api;

import tinygram.api.util.ResourceResponse;
import tinygram.api.util.ResourceTransformer;
import tinygram.datastore.UserEntity;

/**
 * An interface to convert a {@link UserResource} to an encapsulated {@link UserResponse}.
 */
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
