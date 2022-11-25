package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

/**
 * An interface to manage {@link FeedNodeEntity} entities within a specific transaction.
 */
public interface FeedNodeEntityManager extends TypedEntityManager<FeedNodeEntity> {

    /**
     * Gets the feed node entity interface for a specific transaction.
     *
     * @param context The transaction context.
     *
     * @return The feed node entity interface associated to <b>context</b>.
     */
    static FeedNodeEntityManager get(TransactionContext context) {
        return new FeedNodeEntityManagerImpl(context);
    }

    /**
     * Creates a new feed node entity.
     *
     * @param user The feed user entity.
     * @param post The feed post entity.
     *
     * @return The generated feed node entity.
     */
    default FeedNodeEntity register(UserEntity user, PostEntity post) {
        return register(user.getKey(), post);
    }

    /**
     * Creates a new feed node entity.
     *
     * @param userKey The key of the feed user entity.
     * @param post    The feed post entity.
     *
     * @return The generated feed node entity.
     */
    FeedNodeEntity register(Key userKey, PostEntity post);

    /**
     * Fetches all feed nodes of a user feed from the datastore.
     *
     * @param user The user entity to fetch all feed nodes of.
     *
     * @return An iterator of all feed nodes from the feed of <b>user</b>.
     */
    default Iterator<FeedNodeEntity> findAllOfUser(UserEntity user) {
        return findAllOfUser(user.getKey());
    }

    /**
     * Fetches all feed nodes of a user feed from the datastore.
     *
     * @param userKey The key of the user entity to fetch all feed nodes of.
     *
     * @return An iterator of all feed nodes from the feed of the user entity associated to
     *         <b>userKey</b>.
     */
    Iterator<FeedNodeEntity> findAllOfUser(Key userKey);

    /**
     * Fetches all feed nodes about a post from the datastore.
     *
     * @param post The post entity to fetch all feed nodes about.
     *
     * @return An iterator of all feed nodes about <b>post</b>.
     */
    default Iterator<FeedNodeEntity> findAllOfPost(PostEntity post) {
        return findAllOfPost(post.getKey());
    }

    /**
     * Fetches all feed nodes about a post from the datastore.
     *
     * @param postKey The key of the post entity to fetch all feed nodes about.
     *
     * @return An iterator of all feed nodes about the post entity associated to <b>postKey</b>.
     */
    Iterator<FeedNodeEntity> findAllOfPost(Key postKey);

    /**
     * Fetches the global post feed.
     *
     * @param page The feed page token.
     *
     * @return The fetched post feed.
     */
    Feed findPaged(String page);

    /**
     * Fetches a user post feed.
     *
     * @param user The user entity.
     * @param page The feed page token.
     *
     * @return The fetched post feed.
     */
    default Feed findPaged(UserEntity user, String page) {
        return findPaged(user.getKey(), page);
    }

    /**
     * Fetches a user post feed.
     *
     * @param userKey The key of the associated user entity.
     * @param page    The feed page token.
     *
     * @return The fetched post feed.
     */
    Feed findPaged(Key userKey, String page);

    /**
     * Fetches the post feed of posts written by a user.
     *
     * @param user The user entity.
     * @param page The feed page token.
     *
     * @return The fetched post feed.
     */
    default Feed findPagedFrom(UserEntity user, String page) {
        return findPagedFrom(user.getKey(), page);
    }

    /**
     * Fetches the post feed of posts written by a user.
     *
     * @param userKey The key of the associated user entity.
     * @param page    The feed page token.
     *
     * @return The fetched post feed.
     */
    Feed findPagedFrom(Key userKey, String page);
}
