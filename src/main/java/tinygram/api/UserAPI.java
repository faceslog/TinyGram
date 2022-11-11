package tinygram.api;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.Util;
import tinygram.data.UserEntity;
import tinygram.data.UserRepository;

@ApiReference(InstAPI.class)
@ApiClass
public class UserAPI {

    private static final String METHOD_PREFIX = "user.";

    private static final Logger log = Logger.getLogger(UserAPI.class.getName());

    @ApiMethod(
        name       = METHOD_PREFIX + "get",
        path       = "users/{user}",
        httpMethod = HttpMethod.GET)
    public UserEntity getUser(User user, @Named("user") String targetUserKey) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);

        log.info("Retrieving user data...");
        final UserEntity userEntity = userRepository.get(KeyFactory.stringToKey(targetUserKey));
        log.info("User data successfully retrieved.");

        return userEntity;
    }

    @ApiMethod(
        name       = METHOD_PREFIX + "follow",
        path       = "follow/ok/{target}",
        httpMethod = HttpMethod.POST)
    public void followUser(User user, @Named("target") String target) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);

        log.info("Retrieving target data...");
        final UserEntity targetEntity = userRepository.get(KeyFactory.stringToKey(target));
        log.info("Target data successfully retrieved.");

        final UserEntity currentUserEntity = userRepository.getCurrentUser();

        log.info("Following target...");
        if (currentUserEntity.follow(targetEntity)) {
            Util.withinTransaction(currentUserEntity::persist);
            log.info("Target successfully followed.");
        } else {
            log.info("Target already followed.");
        }
    }

    @ApiMethod(
        name       = METHOD_PREFIX + "unfollow",
        path       = "follow/ko/{target}",
        httpMethod = HttpMethod.POST)
    public void unfollowUser(User user, @Named("target") String target) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);

        log.info("Retrieving target data...");
        final UserEntity targetEntity = userRepository.get(KeyFactory.stringToKey(target));
        log.info("Target data successfully retrieved.");

        final UserEntity currentUserEntity = userRepository.getCurrentUser();

        log.info("Unfollowing target...");
        if (currentUserEntity.unfollow(targetEntity)) {
            Util.withinTransaction(currentUserEntity::persist);
            log.info("Target successfully unfollowed.");
        } else {
            log.info("Target already not followed.");
        }
    }
}
