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

import tinygram.data.BasePostRepository;
import tinygram.data.BaseUserRepository;
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

    private UserRepository buildRepository(User user) throws UnauthorizedException {
        log.info("Retrieving user data...");
        final UserRepository userRepository = new BaseUserRepository(user);
        log.info("User data retrieved.");

        return userRepository;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "auth")
    public UserEntity test(User user) throws UnauthorizedException {
        return buildRepository(user).getCurrentUser();
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "add")
    public PostEntity addPost(User user, @Named("image") String image) throws UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        log.info("Registering post...");
        final PostEntity postEntity = postRepository.register(userRepository.getCurrentUser(), image);
        log.info("Post successfully registered.");

        return postEntity;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "users")
    public UserEntity getUser(User user, @Named("user") String targetUserKey) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);

        log.info("Retrieving user data...");
        final UserEntity userEntity = userRepository.get(KeyFactory.stringToKey(targetUserKey));
        log.info("User data successfully retrieved.");

        return userEntity;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "posts")
    public PostEntity getPost(User user, @Named("post") String targetPostKey) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        log.info("Retrieving post data...");
        final PostEntity postEntity = postRepository.get(KeyFactory.stringToKey(targetPostKey));
        log.info("Post data successfully retrieved.");

        return postEntity;
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "feed/followed")
    public FeedResponse<PostEntity> getFeed(User user, @Nullable @Named("page") String page) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        return postRepository.findFromFollowed(userRepository.getCurrentUser(), page);
    }

    @ApiMethod(httpMethod = HttpMethod.GET, path = "feed/from")
    public FeedResponse<PostEntity> getUserPosts(User user, @Nullable @Named("page") String page) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        return postRepository.findFrom(userRepository.getCurrentUser(), page);
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "follow/ok")
    public void addFollower(User user, @Named("target") String target) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);

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

    @ApiMethod(httpMethod = HttpMethod.POST, path = "follow/ko")
    public void removeFollower(User user, @Named("target") String target) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);

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

    @ApiMethod(httpMethod = HttpMethod.POST, path = "like/ok")
    public void addLike(User user, @Named("postId") String post) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        log.info("Retrieving post data...");
        final PostEntity postEntity = postRepository.get(KeyFactory.stringToKey(post));
        log.info("Post data successfully retrieved.");

        log.info("Adding like...");
        if (postEntity.like(userRepository.getCurrentUser())) {
            Util.withinTransaction(postEntity::persist);
            log.info("Like successfully added.");
        } else {
            log.info("Post already liked.");
        }
    }

    @ApiMethod(httpMethod = HttpMethod.POST, path = "like/ko")
    public void removeLike(User user, @Named("postId") String post) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = buildRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        log.info("Retrieving post data...");
        final PostEntity postEntity = postRepository.get(KeyFactory.stringToKey(post));
        log.info("Post data successfully retrieved.");

        log.info("Removing like...");
        if (postEntity.unlike(userRepository.getCurrentUser())) {
            Util.withinTransaction(postEntity::persist);
            log.info("Like successfully removed.");
        } else {
            log.info("Post already not liked.");
        }
    }
}
