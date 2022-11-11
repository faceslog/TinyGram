package tinygram.api;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;

import tinygram.api.config.PostTransformer;
import tinygram.api.config.UserTransformer;
import tinygram.datastore.BaseUserRepository;
import tinygram.datastore.UserEntity;
import tinygram.datastore.UserRepository;

@Api(
    name         = InstAPI.NAME,
    title        = "InstAPI",
    description  = "Lets you manage the TinyGram datastore",
    audiences    = "666928071557-7tupn0nhb8v6cg13vsjtlreg61b6akob.apps.googleusercontent.com",
    clientIds    = "666928071557-7tupn0nhb8v6cg13vsjtlreg61b6akob.apps.googleusercontent.com",
    transformers = { UserTransformer.class, PostTransformer.class })
@ApiClass
public class InstAPI {

    public static final String NAME = "instapi";

    public static final String PATH_AUTH = "auth";

    private static final Logger log = Logger.getLogger(InstAPI.class.getName());

    public static UserRepository buildUserRepository(User user) throws UnauthorizedException {
        log.info("Retrieving user data...");
        final UserRepository userRepository = new BaseUserRepository(user);
        log.info("User data retrieved.");

        return userRepository;
    }

    @ApiMethod(
        name       = "auth",
        path       = PATH_AUTH,
        httpMethod = HttpMethod.GET)
    public UserEntity test(User user) throws UnauthorizedException {
        return buildUserRepository(user).getCurrentUser();
    }
}
