package tinygram.api;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.Util;
import tinygram.data.BasePostRepository;
import tinygram.data.FeedResponse;
import tinygram.data.PostEntity;
import tinygram.data.PostRepository;
import tinygram.data.UserRepository;

@ApiReference(InstAPI.class)
@ApiClass
public class PostAPI {

    private static final String METHOD_PREFIX = "post.";

    private static final Logger log = Logger.getLogger(PostAPI.class.getName());

    @ApiMethod(
        name       = METHOD_PREFIX + "add",
        path       = "add/{image}",
        httpMethod = HttpMethod.POST)
    public PostEntity addPost(User user, @Named("image") String image) throws UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        log.info("Registering post...");
        final PostEntity postEntity = postRepository.register(userRepository.getCurrentUser(), image);
        log.info("Post successfully registered.");

        return postEntity;
    }

    @ApiMethod(
        name       = METHOD_PREFIX + "get",
        path       = "posts/{post}",
        httpMethod = HttpMethod.GET)
    public PostEntity getPost(User user, @Named("post") String targetPostKey) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        log.info("Retrieving post data...");
        final PostEntity postEntity = postRepository.get(KeyFactory.stringToKey(targetPostKey));
        log.info("Post data successfully retrieved.");

        return postEntity;
    }

    @ApiMethod(
        name       = "feed.followed",
        path       = "feed/followed",
        httpMethod = HttpMethod.GET)
    public FeedResponse<PostEntity> getFeed(User user, @Nullable @Named("page") String page) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        return postRepository.findFromFollowed(userRepository.getCurrentUser(), page);
    }

    @ApiMethod(
        name       = "feed.user",
        path       = "feed/from",
        httpMethod = HttpMethod.GET)
    public FeedResponse<PostEntity> getUserPosts(User user, @Nullable @Named("page") String page) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);
        final PostRepository postRepository = new BasePostRepository(userRepository);

        return postRepository.findFrom(userRepository.getCurrentUser(), page);
    }

    @ApiMethod(
        name       = METHOD_PREFIX + "like",
        path       = "like/ok/{postId}",
        httpMethod = HttpMethod.POST)
    public void likePost(User user, @Named("postId") String post) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);
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

    @ApiMethod(
        name       = METHOD_PREFIX + "unlike",
        path       = "like/ko/{postId}",
        httpMethod = HttpMethod.POST)
    public void unlikePost(User user, @Named("postId") String post) throws EntityNotFoundException, UnauthorizedException {
        final UserRepository userRepository = InstAPI.buildUserRepository(user);
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
