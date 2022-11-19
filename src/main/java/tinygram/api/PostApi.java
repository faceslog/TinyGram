package tinygram.api;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.BasePostRepository;
import tinygram.datastore.PostEntity;
import tinygram.datastore.PostRepository;
import tinygram.datastore.UserRepository;
import tinygram.datastore.TransactionManager;

@ApiReference(InstApi.class)
public class PostApi {

    private static final Logger log = Logger.getLogger(PostApi.class.getName());
    private static final TransactionManager transactionManager = TransactionManager.get();

    public static PostRepository buildRepository(UserRepository userRepository) {
        return new BasePostRepository(userRepository);
    }

    @ApiMethod(
        name       = PostApiSchema.RESOURCE_NAME + ".post",
        path       = PostApiSchema.RELATIVE_PATH,
        httpMethod = HttpMethod.POST)
    public PostEntity postPost(User user, PostUpdater postUpdater) throws UnauthorizedException {
        final UserRepository userRepository = UserApi.buildRepository(user);
        final PostRepository postRepository = buildRepository(userRepository);

        log.info("Registering post...");
        final PostEntity postEntity = postRepository.register(userRepository.getCurrentUser(), "", "");
        postUpdater.update(postEntity);
        transactionManager.persist(postEntity);
        log.info("Post successfully registered.");

        return postEntity;
    }

    @ApiMethod(
        name       = PostApiSchema.RESOURCE_NAME + ".get",
        path       = PostApiSchema.RELATIVE_PATH + PostApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public PostEntity getPost(User user, @Named(PostApiSchema.KEY_ARGUMENT_NAME) String postKey) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = UserApi.buildRepository(user);
        final PostRepository postRepository = buildRepository(userRepository);

        log.info("Retrieving post data...");
        final PostEntity postEntity = postRepository.get(KeyFactory.stringToKey(postKey));
        log.info("Post data successfully retrieved.");

        return postEntity;
    }

    @ApiMethod(
        name       = PostApiSchema.RESOURCE_NAME + ".put",
        path       = PostApiSchema.RELATIVE_PATH + PostApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.PUT)
    public PostEntity putPost(User user, @Named(PostApiSchema.KEY_ARGUMENT_NAME) String postKey, PostUpdater postUpdater) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = UserApi.buildRepository(user);
        final PostRepository postRepository = buildRepository(userRepository);

        log.info("Retrieving post data...");
        final PostEntity postEntity = postRepository.get(KeyFactory.stringToKey(postKey));
        log.info("Post data successfully retrieved.");

        log.info("Updating post data...");
        postUpdater.update(postEntity);
        transactionManager.persist(postEntity);
        log.info("Post data successfully updated.");

        return postEntity;
    }
}
