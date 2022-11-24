package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

/**
 * An interface to manage {@link LikeEntity} entities within a specific transaction.
 */
public interface LikeEntityManager extends TypedEntityManager<LikeEntity> {

    /**
     * Gets the like entity interface for a specific transaction.
     *
     * @param context The transaction context.
     *
     * @return The like entity interface associated to <b>context</b>.
     */
    static LikeEntityManager get(TransactionContext context) {
        return new LikeEntityManagerImpl(context);
    }

    /**
     * Creates a new like entity.
     *
     * @param user The like source user entity.
     * @param post The liked post entity.
     *
     * @return The generated like entity.
     */
    LikeEntity register(UserEntity user, PostEntity post);

    /**
     * Fetches a like entity from the datastore.
     *
     * @param user The like source user entity.
     * @param post The liked post entity.
     *
     * @return The fetched like entity if it exists, {@code null} otherwise.
     */
    default LikeEntity find(UserEntity user, PostEntity post) {
        return find(user, post.getKey());
    }

    /**
     * Fetches a like entity from the datastore.
     *
     * @param userKey The key of the like source user entity.
     * @param post    The liked post entity.
     *
     * @return The fetched like entity if it exists, {@code null} otherwise.
     */
    default LikeEntity find(Key userKey, PostEntity post) {
        return find(userKey, post.getKey());
    }

    /**
     * Fetches a like entity from the datastore.
     *
     * @param user    The like source user entity.
     * @param postKey The key of the liked post entity.
     *
     * @return The fetched like entity if it exists, {@code null} otherwise.
     */
    default LikeEntity find(UserEntity user, Key postKey) {
        return find(user.getKey(), postKey);
    }

    /**
     * Fetches a like entity from the datastore.
     *
     * @param userKey The key of the like source user entity.
     * @param postKey The key of the liked post entity.
     *
     * @return The fetched like entity if it exists, {@code null} otherwise.
     */
    LikeEntity find(Key userKey, Key postKey);

    /**
     * Fetches all likes from a user from the datastore.
     *
     * @param user The user entity to fetch all likes of.
     *
     * @return An iterator of all likes from <b>user</b>.
     */
    default Iterator<LikeEntity> findAllFrom(UserEntity user) {
        return findAllFrom(user.getKey());
    }

    /**
     * Fetches all likes from a user from the datastore.
     *
     * @param userKey The key of the user entity to fetch all likes from.
     *
     * @return An iterator of all likes from the user entity associated to <b>userKey</b>.
     */
    Iterator<LikeEntity> findAllFrom(Key userKey);

    /**
     * Fetches all likes to a post from the datastore.
     *
     * @param post The post entity to fetch all likes to.
     *
     * @return An iterator of all likes to <b>post</b>.
     */
    default Iterator<LikeEntity> findAllTo(PostEntity post) {
        return findAllTo(post.getKey());
    }

    /**
     * Fetches all likes to a post from the datastore.
     *
     * @param postKey The key of the post entity to fetch all likes to.
     *
     * @return An iterator of all likes to the post entity associated to <b>postKey</b>.
     */
    Iterator<LikeEntity> findAllTo(Key postKey);
}
