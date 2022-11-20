package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

public interface FollowEntity extends TypedEntity {

    static final String KIND = "Follow";

    static final String FIELD_SOURCE = "source";
    static final String FIELD_TARGET = "target";

    @Override
    default String getKind() {
        return KIND;
    }

    Key getSource();

    Key getTarget();
}
