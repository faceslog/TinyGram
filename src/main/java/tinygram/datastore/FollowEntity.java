package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

public interface FollowEntity extends TypedEntity {

    static final String KIND = "Follow";

    static final Property<Key> PROPERTY_SOURCE = Property.indexedKey("source");
    static final Property<Key> PROPERTY_TARGET = Property.indexedKey("target");

    @Override
    default String getKind() {
        return KIND;
    }

    Key getSource();

    Key getTarget();
}
