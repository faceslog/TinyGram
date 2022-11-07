package tinygram;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.data.FeedResponse;
import tinygram.data.PostEntity;
import tinygram.data.PostRepository;
import tinygram.data.UserEntity;
import tinygram.data.UserRepository;

@Api(name      = "instapi",
     version   = "v1",
     audiences = "666928071557-7tupn0nhb8v6cg13vsjtlreg61b6akob.apps.googleusercontent.com",
     clientIds = "666928071557-7tupn0nhb8v6cg13vsjtlreg61b6akob.apps.googleusercontent.com",
     namespace = @ApiNamespace(ownerDomain = "helloworld.example.com",
                               ownerName   = "helloworld.example.com",
                               packagePath = ""))
public class API {

    private static final Logger log = Logger.getLogger(API.class.getName());

    private UserEntity getUserData(User user) throws UnauthorizedException {
        if (user == null && !Util.DEBUG) {
            throw new UnauthorizedException("Invalid credentials.");
        }

        log.info("Retrieving user data...");
        UserEntity userEntity = UserRepository.find(user);
        if (userEntity != null) {
            log.info("User data retrieved.");
            return userEntity;
        }

        log.info("Missing user data.");

        log.info("Creating user data...");
        userEntity = new UserEntity(user);
        log.info("User data successfully created.");

        log.info("Registering user...");
        Util.withinTransaction(userEntity::persist);
        log.info("User successfully registered.");

        return userEntity;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "auth")
    public UserEntity test(User user) throws UnauthorizedException {
        return getUserData(user);
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "add")
    public PostEntity addPost(User user, @Named("image") String image) throws UnauthorizedException {
        final UserEntity userEntity = getUserData(user);

        log.info("Creating new post...");
        final PostEntity postEntity = new PostEntity(userEntity, image);
        log.info("Post successfully created.");

        log.info("Registering post...");
        Util.withinTransaction(postEntity::persist);
        log.info("Post successfully registered.");

        return postEntity;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "users")
    public UserEntity getUser(@Named("user") String user) throws EntityNotFoundException, UnauthorizedException {
        log.info("Retrieving user data...");
        final UserEntity userEntity = UserRepository.get(KeyFactory.stringToKey(user));
        log.info("User data successfully retrieved.");

        return userEntity;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "posts")
    public PostEntity getPost(@Named("post") String post) throws EntityNotFoundException, UnauthorizedException {
        log.info("Retrieving post data...");
        final PostEntity postEntity = PostRepository.get(KeyFactory.stringToKey(post));
        log.info("Post data successfully retrieved.");

        return postEntity;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "feed/followed")
    public FeedResponse<PostEntity> getFeed(User user, @Nullable @Named("page") String page) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity userEntity = getUserData(user);

        return PostRepository.findFromFollowed(userEntity, page);
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "feed/from")
    public FeedResponse<PostEntity> getUserPosts(User user, @Nullable @Named("page") String page) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity userEntity = getUserData(user);

        return PostRepository.findFrom(userEntity, page);
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "follow/ok")
    public void addFollower(User user, @Named("target") String target) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity userEntity = getUserData(user);

        log.info("Retrieving target data...");
        final UserEntity targetEntity = UserRepository.get(KeyFactory.stringToKey(target));
        log.info("Target data successfully retrieved.");

        log.info("Following target...");
        if (userEntity.follow(targetEntity)) {
            Util.withinTransaction(userEntity::persist);
            log.info("Target successfully followed.");
        } else {
            log.info("Target already followed.");
        }
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "follow/ko")
    public void removeFollower(User user, @Named("target") String target) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity userEntity = getUserData(user);

        log.info("Retrieving target data...");
        final UserEntity targetEntity = UserRepository.get(KeyFactory.stringToKey(target));
        log.info("Target data successfully retrieved.");

        log.info("Unfollowing target...");
        if (userEntity.unfollow(targetEntity)) {
            Util.withinTransaction(userEntity::persist);
            log.info("Target successfully unfollowed.");
        } else {
            log.info("Target already not followed.");
        }
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "like/ok")
    public void addLike(User user, @Named("postId") String post) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity userEntity = getUserData(user);

        log.info("Retrieving post data...");
        final PostEntity postEntity = PostRepository.get(KeyFactory.stringToKey(post));
        log.info("Post data successfully retrieved.");

        log.info("Adding like...");
        if (postEntity.like(userEntity)) {
            Util.withinTransaction(postEntity::persist);
            log.info("Like successfully added.");
        } else {
            log.info("Post already liked.");
        }
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "like/ko")
    public void removeLike(User user, @Named("postId") String post) throws EntityNotFoundException, UnauthorizedException {
        final UserEntity userEntity = getUserData(user);

        log.info("Retrieving post data...");
        final PostEntity postEntity = PostRepository.get(KeyFactory.stringToKey(post));
        log.info("Post data successfully retrieved.");

        log.info("Removing like...");
        if (postEntity.unlike(userEntity)) {
            Util.withinTransaction(postEntity::persist);
            log.info("Like successfully removed.");
        } else {
            log.info("Post already not liked.");
        }
    }
}
