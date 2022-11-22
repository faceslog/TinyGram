package tinygram.api;

import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;

import tinygram.datastore.FollowEntity;
import tinygram.datastore.FollowEntityManager;
import tinygram.datastore.PostEntity;
import tinygram.datastore.PostEntityManager;
import tinygram.datastore.UserEntity;
import tinygram.datastore.UserEntityManager;
import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TransactionManager;
import tinygram.util.Randomizer;

@ApiReference(InstApi.class)
public class BenchmarkApi {

    private static final String DEFAULT_POST_IMAGE = "https://cdn0.matrimonio.com.co/usr/2/1/0/2/cfb_315540.jpg";

    private static final Logger logger = Logger.getLogger(PostApi.class.getName());

    private static User getFollower(String userId, int i) {
        return new User("BMF" + userId + i, "bmf" + userId + i + "@instapi.com");
    }

    private static String getFollowerName(String userId, int i) {
        return "Benchmark-Follower-" + userId + "-" + i;
    }

    private static User getPublisher(String userId) {
        return new User("BMP" + userId, "bmp" + userId + "@instapi.com");
    }

    private static String getPublisherName(String userId) {
        return "Benchmark-Publisher-" + userId;
    }

    @ApiMethod(
        name       = "benchmark.followers",
        path       = "benchmark/followers/{count}",
        httpMethod = HttpMethod.PUT)
    public void addFollowers(User user, @Named("count") int count) throws UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.begin();
        final TransactionContext context = transactionManager.getContext();
        final UserEntityManager userManager = UserEntityManager.get(context);
        final FollowEntityManager followManager = FollowEntityManager.get(context);

        final UserEntity currentUser = UserApi.getCurrentUser(context, user);
        final String userId = currentUser.getId();

        logger.info("Setting follower count to " + count + "...");
        int persistCount = 0;

        int i = 1;
        for (; i <= count; ++i) {
            final User follower = getFollower(userId, i);

            UserEntity followerEntity = userManager.find(follower.getId());
            if (followerEntity != null) continue;

            followerEntity = userManager.register(follower, getFollowerName(userId, i), "");

            final FollowEntity followEntity = followManager.register(followerEntity, currentUser);

            logger.info("Registering missing follower " + i + "...");
            followEntity.persistUsing(context);
            ++persistCount;
        }

        logger.info(persistCount + " missing followers successfully registered.");

        int forgetCount = 0;

        for (;; ++i) {
            final User follower = getFollower(userId, i);

            UserEntity followerEntity = userManager.find(follower.getId());
            if (followerEntity == null) break;

            logger.info("Unregistering excess follower " + i + "...");
            followerEntity.forgetUsing(context);
            ++forgetCount;
        }

        logger.info(forgetCount + " excess followers successfully unregistered.");

        logger.info("Follower count successfully set to " + count + ".");

        transactionManager.commit();
    }

    @ApiMethod(
        name       = "benchmark.publisher",
        path       = "benchmark/publisher/{postCount}",
        httpMethod = HttpMethod.POST)
    public UserEntity makePublisher(User user, @Named("postCount") int postCount) throws UnauthorizedException {
        final TransactionManager transactionManager = TransactionManager.begin();
        final TransactionContext context = transactionManager.getContext();
        final UserEntityManager userManager = UserEntityManager.get(context);
        final FollowEntityManager followManager = FollowEntityManager.get(context);
        final PostEntityManager postManager = PostEntityManager.get(context);

        final UserEntity currentUser = UserApi.getCurrentUser(context, user);

        final String publisherId = Randomizer.randomize(Randomizer.ALPHANUMERIC, 16);
        final User publisher = getPublisher(publisherId);

        logger.info("Registering publisher...");
        final UserEntity publisherEntity = userManager.register(publisher, getPublisherName(publisherId), "");
        final FollowEntity followEntity = followManager.register(currentUser, publisherEntity);

        followEntity.persistUsing(context);
        logger.info("Publisher successfully registered.");

        for (int i = 1; i <= postCount; ++i) {
            final PostEntity postEntity = postManager.register(publisherEntity, DEFAULT_POST_IMAGE, "My post " + i + ".");

            logger.info("Registering post " + i + "...");
            postEntity.persistUsing(context);
        }

        logger.info(postCount + " posts successfully registered.");

        transactionManager.commit();

        return publisherEntity;
    }
}
