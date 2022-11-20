package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

public interface PostEntity extends TypedEntity {

    static final String KIND = "Post";

    static final Property<Key> PROPERTY_OWNER = Property.indexedKey("owner");
    static final Property<Date> PROPERTY_DATE = Property.indexedDate("date");
    static final Property<String> PROPERTY_IMAGE = Property.string("image");
    static final Property<String> PROPERTY_DESCRIPTION = Property.string("description");
    static final Property<Long> PROPERTY_LIKE_COUNT = Property.number("likecount");

    @Override
    default String getKind() {
        return KIND;
    }

    Key getOwner();

    Date getDate();

    String getImage();

    void setImage(String image);

    String getDescription();

    void setDescription(String description);

    void incrementLikeCount();

    void decrementLikeCount();

    long getLikeCount();
}
