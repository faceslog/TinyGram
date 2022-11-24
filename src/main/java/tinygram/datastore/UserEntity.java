package tinygram.datastore;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

/**
 * A datastore entity, representing a user.
 */
public interface UserEntity extends TypedEntity {

    /**
     * The kind of any user entity.
     */
    static final String KIND = "User";

    /**
     * The Google user identifier property.
     */
    static final Property<String> PROPERTY_ID = Property.indexedString("id");
    /**
     * The user name property.
     */
    static final Property<String> PROPERTY_NAME = Property.string("name");
    /**
     * The user profile picture property.
     */
    static final Property<String> PROPERTY_IMAGE = Property.string("image");
    /**
     * The property storing the number of people the user is following.
     */
    static final Property<Long> PROPERTY_FOLLOWING_COUNT = Property.number("followingcount");
    /**
     * The property storing the number of posts the user has written.
     */
    static final Property<Long> PROPERTY_POST_COUNT = Property.number("postcount");

    /**
     * The kind of any counter storing the number of followers.
     */
    static final String COUNTER_FOLLOWER = KIND + "_follower";

    @Override
    default String getKind() {
        return KIND;
    }

    /**
     * Gets the Google user identifier.
     *
     * @return The string Google user identifier.
     */
    String getId();

    /**
     * Gets the user name.
     *
     * @return The string user name.
     */
    String getName();

    /**
     * Sets the user name.
     *
     * @param name The new user name.
     */
    void setName(String name);

    /**
     * Gets the user profile picture.
     *
     * @return The user profile picture URL.
     */
    String getImage();

    /**
     * Sets the user profile picture.
     *
     * @param image The new user profile picture URL.
     */
    void setImage(String image);

    /**
     * Increments the number of followers, from a randomly selected counter shard.
     */
    void incrementFollowerCount();

    /**
     * Decrement the number of followers, from a randomly selected counter shard.
     */
    void decrementFollowerCount();

    /**
     * Gets the number of followers, summing all counter shard values.
     *
     * @return The total number of followers.
     */
    long getFollowerCount();

    /**
     * Increments the number of people following the user, from a randomly selected counter shard.
     */
    void incrementFollowingCount();

    /**
     * Decrements the number of people following the user, from a randomly selected counter shard.
     */
    void decrementFollowingCount();

    /**
     * Gets the number of people following the user, summing all counter shard values.
     *
     * @return The total number of people following the user.
     */
    long getFollowingCount();

    /**
     * Increments the number of posts the user has written, from a randomly selected counter shard.
     */
    void incrementPostCount();

    /**
     * Decrements the number of posts the user has written, from a randomly selected counter shard.
     */
    void decrementPostCount();

    /**
     * Gets the number of posts the user has written, summing all counter shard values.
     *
     * @return The total number of posts the user has written.
     */
    long getPostCount();
}
