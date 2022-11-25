package tinygram.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.QueryResultList;

import tinygram.Config;

/**
 * An implementation of the {@link Feed} interface.
 */
public class FeedImpl implements Feed {

    /**
     * The feed post collection.
     */
    private final Collection<Key> posts;
    /**
     * The next feed page token.
     */
    private final String nextPage;
    /**
     * The key of the user entity the feed is about. May be {@code null} if the feed is the global
     * one.
     *
     * <p> <b>Invariant:</b> {@code (userKey == null) ==> !aboutFollowed }
     */
    private final Key userKey;
    /**
     * Whether the feed is about posts from followed users of the associated user entity or not.
     *
     * <p> <b>Invariant:</b> {@code aboutFollowed ==> (userKey != null)}
     */
    private final boolean aboutFollowed;

    /**
     * Creates a global post feed.
     *
     * @param entityBuilder The function to extract post entity keys from fetched entities.
     * @param results       The fetched entities from the datastore.
     */
    public FeedImpl(Function<Entity, Key> entityBuilder, QueryResultList<Entity> results) {
        this(entityBuilder, results, null);
    }

    /**
     * Creates a user post feed.
     *
     * @param entityBuilder The function to extract post entity keys from fetched entities.
     * @param results       The fetched entities from the datastore.
     * @param userKey       The key if the user entity the feed is about
     */
    public FeedImpl(Function<Entity, Key> entityBuilder, QueryResultList<Entity> results,
                    Key userKey) {
        this(entityBuilder, results, userKey, false);
    }

    /**
     * Creates a user post feed.
     *
     * @param entityBuilder The function to extract post entity keys from fetched entities.
     * @param results       The fetched entities from the datastore.
     * @param userKey       The key if the user entity the feed is about.
     * @param aboutFollowed Whether the feed is about posts from followed users of the user or not.
     */
    public FeedImpl(Function<Entity, Key> entityBuilder, QueryResultList<Entity> results,
                    Key userKey, boolean aboutFollowed) {
        final int postCount = results.size();

        posts = new ArrayList<>(postCount);
        nextPage = postCount < Config.FEED_LIMIT ? null : results.getCursor().toWebSafeString();

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
