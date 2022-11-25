package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

/**
 * An interface to manage {@link PostEntity} entities within a specific transaction.
 */
public interface PostEntityManager extends TypedEntityManager<PostEntity> {

    /**
     * Gets the post entity interface for a specific transaction.
     *
     * @param context The transaction context.
     *
     * @return The post entity interface associated to <b>context</b>.
     */
    static PostEntityManager get(TransactionContext context) {
        return new PostEntityManagerImpl(context);
    }

    /**
     * Creates a new post entity.
     *
     * @param user        The post owner entity.
     * @param image       The post image URL.
     * @param description The post image description.
     *
     * @return The generated post entity.
     */
    PostEntity register(UserEntity user, String image, String description);

    /**
     * Fetches the latest post written by a user from the datastore.
     *
     * @param user The user entity to fetch the latest post of.
     *
     * @return The latest post entity written by <b>user</b> if one exists, {@code null} otherwise.
     */
    default PostEntity findLatest(UserEntity user) {
        return findLatest(user.getKey());
    }

    /**
     * Fetches the latest post written by a user from the datastore.
     *
     * @param userKey The key of the user entity to fetch the latest post of.
     *
     * @return The latest post entity written by the user entity associated to <b>userKey</b> if one
     *         exists, {@code null} otherwise.
     */
    PostEntity findLatest(Key userKey);

    /**
     * Fetches all posts written by a user from the datastore.
     *
     * @param user The user entity to fetch all posts of.
     *
     * @return An iterator of all posts written by <b>user</b>.
     */
    default Iterator<PostEntity> findAll(UserEntity user) {
        return findAll(user.getKey());
    }

    /**
     * Fetches all posts written by a user from the datastore.
     *
     * @param userKey The key of the user entity to fetch all posts of.
     *
     * @return An iterator of all posts written by the user entity associated to <b>userKey</b>.
     */
    Iterator<PostEntity> findAll(Key userKey);
}
