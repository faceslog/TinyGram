package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

public interface FeedNodeEntity extends TypedEntity {

    static final String KIND = "FeedNode";

    static final Property<Key> PROPERTY_USER = Property.indexedKey("user");
    static final Property<Key> PROPERTY_POST = Property.indexedKey("post");
    static final Property<Date> PROPERTY_DATE = Property.indexedDate("date");

    @Override
    default String getKind() {
        return KIND;
    }

    Key getUser();

    Key getPost();

    Date getDate();
}
