package tinygram.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.QueryResultList;

import tinygram.Config;

public class FeedImpl implements Feed {

    private final Collection<Key> posts;
    private final String nextPage;
    private final Key userKey;
    private final boolean aboutFollowed;

    public FeedImpl(Function<Entity, Key> entityBuilder, QueryResultList<Entity> results) {
        this(entityBuilder, results, null);
    }

    public FeedImpl(Function<Entity, Key> entityBuilder, QueryResultList<Entity> results, Key userKey) {
        this(entityBuilder, results, userKey, false);
    }

    public FeedImpl(Function<Entity, Key> entityBuilder, QueryResultList<Entity> results, Key userKey, boolean aboutFollowed) {
        posts = new ArrayList<>(results.size());
        nextPage = results.size() < Config.FEED_LIMIT ? null : results.getCursor().toWebSafeString();

        this.userKey = userKey;
        this.aboutFollowed = aboutFollowed;

        results.forEach(entity -> posts.add(entityBuilder.apply(entity)));
    }

    @Override
    public Collection<Key> getPosts() {
        return posts;
    }

    @Override
    public String getNextPage() {
        return nextPage;
    }

    @Override
    public boolean isAboutFollowed() {
        return aboutFollowed;
    }

    @Override
    public Key getUser() {
        return userKey;
    }
}