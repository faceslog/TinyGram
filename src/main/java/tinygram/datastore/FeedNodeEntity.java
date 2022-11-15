package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

public interface FeedNodeEntity extends TypedEntity {

    static final String KIND = "FeedNode";

    static final String FIELD_USER = "user";
    static final String FIELD_POST = "post";
    static final String FIELD_DATE = "date";

    @Override
    default String getKind() {
        return KIND;
    }

    Key getUser();

    Key getPost();

    Date getDate();
}
