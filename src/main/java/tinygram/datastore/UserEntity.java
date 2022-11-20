package tinygram.datastore;

public interface UserEntity extends TypedEntity {

    static final String KIND = "User";

    static final String FIELD_ID = "id";
    static final String FIELD_NAME = "name";
    static final String FIELD_IMAGE = "image";
    static final String FIELD_FOLLOWERS = "followers";
    static final String FIELD_FOLLOWER_COUNT = "followercount";
    static final String FIELD_FOLLOWING_COUNT = "followingcount";
    static final String FIELD_POST_COUNT = "postcount";

    @Override
    default String getKind() {
        return KIND;
    }

    String getId();

    String getName();

    void setName(String name);

    String getImage();

    void setImage(String image);

    void incrementFollowerCount();

    void decrementFollowerCount();

    long getFollowerCount();

    void incrementFollowingCount();

    void decrementFollowingCount();

    long getFollowingCount();

    void incrementPostCount();

    void decrementPostCount();

    long getPostCount();
}
