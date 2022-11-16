package tinygram.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;

import tinygram.datastore.PostEntity;
import tinygram.datastore.PostRepository;
import tinygram.datastore.UserEntity;
import tinygram.datastore.UserRepository;
import tinygram.datastore.Util;
import tinygram.util.Randomizer;

@ApiReference(InstApi.class)
public class BenchmarkApi {

    private static final String DEFAULT_POST_IMAGE = "https://cdn0.matrimonio.com.co/usr/2/1/0/2/cfb_315540.jpg";

    private static final Logger log = Logger.getLogger(PostApi.class.getName());

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
        final UserRepository userRepository = UserApi.buildRepository(user);

        final UserEntity userEntity = userRepository.getCurrentUser();
        final String userId = user.getId();

        final List<UserEntity> toPersist = new ArrayList<>();

        int i = 1;
        for (; i <= count; ++i) {
            final User follower = getFollower(userId, i);

            UserEntity followerEntity = userRepository.find(follower.getId());
            if (followerEntity != null) continue;

            followerEntity = userRepository.register(follower, getFollowerName(userId, i), "");
            userEntity.addFollow(followerEntity);

            toPersist.add(userEntity);
        }

        Util.withinTransaction(toPersist::forEach, UserEntity::persist);

        final List<UserEntity> toForget = new ArrayList<>();

        for (;; ++i) {
            final User follower = getFollower(userId, i);

            UserEntity followerEntity = userRepository.find(follower.getId());
            if (followerEntity == null) break;

            toForget.add(followerEntity);
        }

        Util.withinTransaction(toForget::forEach, UserEntity::forget);
    }

    @ApiMethod(
        name       = "benchmark.publisher",
        path       = "benchmark/publisher/{postCount}",
        httpMethod = HttpMethod.POST)
    public UserEntity makePublisher(User user, @Named("postCount") int postCount) throws UnauthorizedException {
        final UserRepository userRepository = UserApi.buildRepository(user);
        final PostRepository postRepository = PostApi.buildRepository(userRepository);

        final String publisherId = Randomizer.alphanum(16);
        final User publisher = getPublisher(publisherId);

        final UserEntity userEntity = userRepository.getCurrentUser();
        final UserEntity publisherEntity = userRepository.register(publisher, getPublisherName(publisherId), "");

        publisherEntity.addFollow(userEntity);
        Util.withinTransaction(publisherEntity::persist);

        final List<PostEntity> toPersist = new ArrayList<>();

        for (int i = 1; i <= postCount; ++i) {
            final PostEntity postEntity = postRepository.register(publisherEntity, DEFAULT_POST_IMAGE, "My post " + i + ".");
            toPersist.add(postEntity);
        }

        Util.withinTransaction(toPersist::forEach, PostEntity::persist);

        return publisherEntity;
    }
}
