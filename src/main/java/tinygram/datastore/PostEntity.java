package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

public interface PostEntity extends TypedEntity {

    static final String KIND = "Post";

    static final Property<Key> PROPERTY_OWNER = Property.indexedKey("owner");
    static final Property<Date> PROPERTY_DATE = Property.indexedDate("date");
    static final Property<String> PROPERTY_IMAGE = Property.string("image");
    static final Property<String> PROPERTY_DESCRIPTION = Property.string("description");

    static final String COUNTER_LIKE = KIND + "_like";

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
