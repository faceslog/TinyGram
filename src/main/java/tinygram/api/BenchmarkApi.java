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

/**
 * The Tinygram benchmark API.
 *
 * <p> <b>Warning:</b> Operations provided by this API may cause severe performance issues. Reminder
 * that the datastore does not allow adding, updating or removing more than 500 entities during the
 * same transaction, a limit that can easily be reached with any of these operations. Going past
 * this limit will allow transactions to commit, without modifying everything they should have,
 * breaking the invariant of existing entities and potentially breaking the entire Tinygram API.
 * Please use these operations with a lot of care.
 */
@ApiReference(InstApi.class)
public class BenchmarkApi {

    private static final String DEFAULT_POST_IMAGE = "https://i.pinimg.com/originals/2b/d9/91/2bd991b5f196840f2cb2dd36810e3304.jpg";

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
        TransactionManager transactionManager = TransactionManager.begin();
        TransactionContext context = transactionManager.getContext();
        final UserEntityManager userManager = UserEntityManager.get(context);

        final String publisherId = Randomizer.randomize(Randomizer.ALPHANUMERIC, 16);
        final User publisher = getPublisher(publisherId);

        logger.info("Registering publisher...");
        final UserEntity publisherEntity = userManager.register(publisher, getPublisherName(publisherId), "");
        publisherEntity.persistUsing(context);

        transactionManager.commit();

        transactionManager = TransactionManager.begin();
        context = transactionManager.getContext();
        final FollowEntityManager followManager = FollowEntityManager.get(context);

        final UserEntity currentUser = UserApi.getCurrentUser(context, user);

        final FollowEntity followEntity = followManager.register(currentUser, publisherEntity);
        followEntity.persistUsing(context);

        transactionManager.commit();

        transactionManager = TransactionManager.begin();
        context = transactionManager.getContext();
        final PostEntityManager postManager = PostEntityManager.get(context);

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
