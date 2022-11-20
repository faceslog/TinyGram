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
import tinygram.datastore.TransactionManager;

@ApiReference(InstApi.class)
public class UserApi {

    private static final Logger logger = Logger.getLogger(UserApi.class.getName());
    private static final TransactionManager transactionManager = TransactionManager.get();
    private static final UserEntityManager userManager = UserEntityManager.get();

    public static UserEntity getCurrentUser(User user) throws UnauthorizedException {
        if (user == null) {
            throw new IllegalArgumentException("Missing user credentials.");
        }

        logger.info("Retrieving user data...");
        final UserEntity userEntity = userManager.find(user);

        if (userEntity == null) {
            throw new IllegalArgumentException("Unregistered user.");
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
            throw new IllegalArgumentException("Missing user credentials.");
        }

        logger.info("Registering user...");
        UserEntity userEntity = userManager.find(user);

        if (userEntity != null) {
            logger.info("User already registered: data retrieved.");
        } else {
            userEntity = userManager.register(user, "", "");
            userUpdater.update(userEntity);
            transactionManager.persist(userEntity);
            logger.info("User successfully registered.");
        }

        return new UserResource(userEntity);
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".get",
        path       = UserApiSchema.RELATIVE_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public UserResource getUser(User user, @Named(UserApiSchema.KEY_ARGUMENT_NAME) String targetUserKey) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity currentUser = getCurrentUser(user);

        logger.info("Retrieving user data...");
        final UserEntity userEntity = userManager.get(KeyFactory.stringToKey(targetUserKey));
        logger.info("User data successfully retrieved.");

        return new UserResource(currentUser, userEntity);
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".put",
        path       = UserApiSchema.RELATIVE_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.PUT)
    public UserResource putUser(User user, @Named(UserApiSchema.KEY_ARGUMENT_NAME) String userKey, LoggedUserUpdater userUpdater) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity currentUser = getCurrentUser(user);

        logger.info("Retrieving target user data...");
        final UserEntity userEntity = userManager.get(KeyFactory.stringToKey(userKey));
        logger.info("Target user data successfully retrieved.");

        logger.info("Updating target user data...");
        userUpdater.update(currentUser, userEntity);
        transactionManager.persist(userEntity);
        logger.info("Target user data successfully updated.");

        return new UserResource(getCurrentUser(user), userEntity);
    }
}
