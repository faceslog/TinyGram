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

import tinygram.datastore.PostEntity;
import tinygram.datastore.PostEntityManager;
import tinygram.datastore.UserEntity;
import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TransactionManager;

/**
 * The Tinygram post API.
 */
@ApiReference(InstApi.class)
public class PostApi {

    private static final Logger logger = Logger.getLogger(PostApi.class.getName());

    @ApiMethod(
        name       = PostApiSchema.RESOURCE_NAME + ".post",
        path       = PostApiSchema.RELATIVE_PATH,
        httpMethod = HttpMethod.POST)
    public PostResource postPost(User user, PostUpdater postUpdater) throws UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.begin();
        final TransactionContext context = transactionManager.getContext();
        final PostEntityManager postManager = PostEntityManager.get(context);

        final UserEntity currentUser = UserApi.getCurrentUser(context, user);

        logger.info("Registering post...");
        final PostEntity postEntity = postManager.register(currentUser, "", "");
        postUpdater.update(postEntity);
        postEntity.persistUsing(context);
        logger.info("Post successfully registered.");

        transactionManager.commit();

        return new PostResource(currentUser, postEntity);
    }

    @ApiMethod(
        name       = PostApiSchema.RESOURCE_NAME + ".get",
        path       = PostApiSchema.RELATIVE_PATH + PostApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public PostResource getPost(User user, @Named(PostApiSchema.KEY_ARGUMENT_NAME) String postKey)
                throws EntityNotFoundException, UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.beginReadOnly();
        final TransactionContext context = transactionManager.getContext();
        final PostEntityManager postManager = PostEntityManager.get(context);

        final UserEntity currentUser = UserApi.getCurrentUser(context, user);

        logger.info("Retrieving post data...");
        final PostEntity postEntity = postManager.get(KeyFactory.stringToKey(postKey));
        logger.info("Post data successfully retrieved.");

        transactionManager.commit();

        return new PostResource(currentUser, postEntity);
    }

    @ApiMethod(
        name       = PostApiSchema.RESOURCE_NAME + ".put",
        path       = PostApiSchema.RELATIVE_PATH + PostApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.PUT)
    public PostResource putPost(User user, @Named(PostApiSchema.KEY_ARGUMENT_NAME) String postKey,
                                LoggedPostUpdater postUpdater)
                throws EntityNotFoundException, UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.begin();
        final TransactionContext context = transactionManager.getContext();
        final PostEntityManager postManager = PostEntityManager.get(context);

        final UserEntity currentUser = UserApi.getCurrentUser(context, user);

        logger.info("Retrieving post data...");
        final PostEntity postEntity = postManager.get(KeyFactory.stringToKey(postKey));
        logger.info("Post data successfully retrieved.");

        logger.info("Updating post data...");
        postUpdater.update(context, currentUser, postEntity);
        postEntity.persistUsing(context);
        logger.info("Post data successfully updated.");

        transactionManager.commit();

        return new PostResource(currentUser, postEntity);
    }
}
