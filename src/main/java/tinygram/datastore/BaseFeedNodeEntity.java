package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

class BaseFeedNodeEntity extends AbstractTypedEntity implements FeedNodeEntity {

    public BaseFeedNodeEntity(UserEntity user, PostEntity post) {
        this(user.getKey(), post);
    }

    public BaseFeedNodeEntity(Key userKey, PostEntity post) {
        super();

        setProperty(FIELD_USER, userKey);
        setProperty(FIELD_POST, post.getKey());
        setProperty(FIELD_DATE, post.getDate());
    }

    public BaseFeedNodeEntity(Entity raw) {
        super(raw);
    }

    @Override
    public Key getUser() {
        return getProperty(FIELD_USER);
    }

    @Override
    public Key getPost() {
        return getProperty(FIELD_POST);
    }

    @Override
    public Date getDate() {
        return getProperty(FIELD_DATE);
    }
}
