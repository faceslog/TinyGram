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

import tinygram.datastore.UserEntity;
import tinygram.datastore.UserEntityManager;
import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TransactionManager;

/**
 * The Tinygram user API.
 */
@ApiReference(InstApi.class)
public class UserApi {

    private static final Logger logger = Logger.getLogger(UserApi.class.getName());

    /**
     * Gets the currently logged user data, ensuring credentials are valid.
     *
     * @param context The current transaction context.
     * @param user    The Google user authentification credentials.
     *
     * @return The currently logged user entity.
     *
     * @throws UnauthorizedException If the given authentification credentials are invalid, or the
     *                               user has not already been registered within the datastore.
     */
    public static UserEntity getCurrentUser(TransactionContext context, User user)
                throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Missing user credentials.");
        }

        final UserEntityManager userManager = UserEntityManager.get(context);

        logger.info("Retrieving user data...");
        final UserEntity userEntity = userManager.find(user);

        if (userEntity == null) {
            throw new UnauthorizedException("Unregistered user.");
        }

        logger.info("User data retrieved.");
        return userEntity;
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".post",
        path       = UserApiSchema.RELATIVE_PATH,
        httpMethod = HttpMethod.POST)
    public UserResource postUser(User user, UserUpdater userUpdater) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Missing user credentials.");
        }

        final TransactionManager transactionManager = TransactionManager.begin();
        final TransactionContext context = transactionManager.getContext();
        final UserEntityManager userManager = UserEntityManager.get(context);

        logger.info("Registering user...");
        UserEntity userEntity = userManager.find(user);

        if (userEntity != null) {
            logger.info("User already registered: data retrieved.");
        } else {
            userEntity = userManager.register(user, "", "");
            userUpdater.update(userEntity);
            userEntity.persistUsing(context);
            logger.info("User successfully registered.");
        }

        transactionManager.commit();

        return new UserResource(userEntity);
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".get",
        path       = UserApiSchema.RELATIVE_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public UserResource getUser(User user,
                                @Named(UserApiSchema.KEY_ARGUMENT_NAME) String targetUserKey)
                throws EntityNotFoundException, UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.beginReadOnly();
        final TransactionContext context = transactionManager.getContext();
        final UserEntityManager userManager = UserEntityManager.get(context);

        final UserEntity currentUser = getCurrentUser(context, user);

        logger.info("Retrieving user data...");
        final UserEntity userEntity = userManager.get(KeyFactory.stringToKey(targetUserKey));
        logger.info("User data successfully retrieved.");

        transactionManager.commit();

        return new UserResource(currentUser, userEntity);
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".put",
        path       = UserApiSchema.RELATIVE_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.PUT)
    public UserResource putUser(User user, @Named(UserApiSchema.KEY_ARGUMENT_NAME) String userKey,
                                LoggedUserUpdater userUpdater)
                throws EntityNotFoundException, UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.begin();
        final TransactionContext context = transactionManager.getContext();
        final UserEntityManager userManager = UserEntityManager.get(context);

        final UserEntity currentUser = getCurrentUser(context, user);

        logger.info("Retrieving target user data...");
        final UserEntity userEntity = userManager.get(KeyFactory.stringToKey(userKey));
        logger.info("Target user data successfully retrieved.");

        logger.info("Updating target user data...");
        userUpdater.update(context, currentUser, userEntity);
        userEntity.persistUsing(context);
        logger.info("Target user data successfully updated.");

        transactionManager.commit();

        return new UserResource(currentUser, userEntity);
    }
}
