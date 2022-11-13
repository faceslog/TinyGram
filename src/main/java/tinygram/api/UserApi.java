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

import tinygram.Config;
import tinygram.Util;
import tinygram.datastore.BaseUserRepository;
import tinygram.datastore.UserEntity;
import tinygram.datastore.UserRepository;

@ApiReference(InstApi.class)
public class UserApi {

    private static final Logger log = Logger.getLogger(UserApi.class.getName());

    public static UserRepository buildUserRepository(User user) throws UnauthorizedException {
        if (user == null && !Config.DEBUG) {
            throw new IllegalArgumentException("Missing user credentials.");
        }

        final UserRepository userRepository = new BaseUserRepository();

        log.info("Retrieving user data...");
        final UserEntity userEntity = userRepository.find(user);

        if (userEntity == null) {
            throw new IllegalArgumentException("Unregistered user.");
        }

        userRepository.setCurrentUser(userEntity);
        log.info("User data retrieved.");

        return userRepository;
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".post",
        path       = UserApiSchema.RELATIVE_PATH,
        httpMethod = HttpMethod.POST)
    public UserEntity postUser(User user, UserUpdater userUpdater) throws UnauthorizedException {
        if (user == null && !Config.DEBUG) {
            throw new IllegalArgumentException("Missing user credentials.");
        }

        final UserRepository userRepository = new BaseUserRepository();

        log.info("Registering user...");
        UserEntity userEntity = userRepository.find(user);

        if (userEntity != null) {
            log.info("User already registered: data retrieved.");
            return userEntity;
        }

        userEntity = userRepository.register(user, "", "");
        userUpdater.update(userEntity);

        Util.withinTransaction(userEntity::persist);
        log.info("User successfully registered.");

        return userEntity;
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".get",
        path       = UserApiSchema.RELATIVE_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.GET)
    public UserEntity getUser(User user, @Named(UserApiSchema.KEY_ARGUMENT_NAME) String targetUserKey) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildUserRepository(user);

        log.info("Retrieving user data...");
        final UserEntity userEntity = userRepository.get(KeyFactory.stringToKey(targetUserKey));
        log.info("User data successfully retrieved.");

        return userEntity;
    }

    @ApiMethod(
        name       = UserApiSchema.RESOURCE_NAME + ".put",
        path       = UserApiSchema.RELATIVE_PATH + UserApiSchema.KEY_ARGUMENT_SUFFIX,
        httpMethod = HttpMethod.PUT)
    public UserEntity putUser(User user, @Named(UserApiSchema.KEY_ARGUMENT_NAME) String userKey, UserUpdater userUpdater) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildUserRepository(user);

        log.info("Retrieving target user data...");
        final UserEntity userEntity = userRepository.get(KeyFactory.stringToKey(userKey));
        log.info("Target user data successfully retrieved.");

        log.info("Updating target user data...");
        userUpdater.update(userEntity);
        Util.withinTransaction(userEntity::persist);
        log.info("Target user data successfully updated.");

        return userEntity;
    }
}
