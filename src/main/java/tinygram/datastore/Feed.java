package tinygram.datastore;

import java.util.Collection;

import com.google.appengine.api.datastore.Key;

/**
 * A batch of posts sorted from the latest to the oldest, fetched from a post feed.
 */
public interface Feed {

    /**
     * Gets the feed post collection.
     *
     * @return A collection of the keys of the post entities within the feed.
     */
    Collection<Key> getPosts();

    /**
     * Gets the next feed page token to retrieve the following posts in the corresponding feed.
     *
     * @return The string next feed page token.
     */
    String getNextPage();

    /**
     * Indicates whether the feed is about posts from followed users of a user or not.
     *
     * <p> <b>Invariant:</b> {@code isAboutFollowed() ==> (getUser() != null)}
     *
     * @return {@code true} if the feed is about posts from followed users, {@code false} otherwise.
     */
    boolean isAboutFollowed();

    /**
     * Gets the user the feed is about if there is one.
     *
     * <p> <b>Invariant:</b> {@code (getUser() == null) ==> !isAboutFollowed()}
     *
     * @return The key of the user entity the feed is about if there is one, {@code null} otherwise.
     */
    Key getUser();
}
