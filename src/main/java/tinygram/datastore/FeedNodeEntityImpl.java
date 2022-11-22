package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TypedEntityImpl;

class FeedNodeEntityImpl extends TypedEntityImpl implements FeedNodeEntity {

    public FeedNodeEntityImpl(UserEntity user, PostEntity post) {
        this(user.getKey(), post);
    }

    public FeedNodeEntityImpl(Key userKey, PostEntity post) {
        super(KIND, post.getKey().getName() + userKey.getName());

        setProperty(PROPERTY_USER, userKey);
        setProperty(PROPERTY_POST, post.getKey());
        setProperty(PROPERTY_DATE, post.getDate());
    }

    public FeedNodeEntityImpl(Entity raw) {
        super(KIND, raw);
    }

    @Override
    public Key getUser() {
        return getProperty(PROPERTY_USER);
    }

    @Override
    public Key getPost() {
        return getProperty(PROPERTY_POST);
    }

    @Override
    public Date getDate() {
        return getProperty(PROPERTY_DATE);
    }
}
