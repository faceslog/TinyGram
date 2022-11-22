package tinygram.datastore;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

public interface UserEntity extends TypedEntity {

    static final String KIND = "User";

    static final Property<String> PROPERTY_ID = Property.indexedString("id");
    static final Property<String> PROPERTY_NAME = Property.string("name");
    static final Property<String> PROPERTY_IMAGE = Property.string("image");
    static final Property<Long> PROPERTY_FOLLOWING_COUNT = Property.number("followingcount");
    static final Property<Long> PROPERTY_POST_COUNT = Property.number("postcount");

    static final String COUNTER_FOLLOWER = KIND + "_follower";

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
