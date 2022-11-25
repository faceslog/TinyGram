package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Key;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

/**
 * An interface to manage {@link FollowEntity} entities within a specific transaction.
 */
public interface FollowEntityManager extends TypedEntityManager<FollowEntity> {

    /**
     * Gets the follow entity interface for a specific transaction.
     *
     * @param context The transaction context.
     *
     * @return The follow entity interface associated to <b>context</b>.
     */
    static FollowEntityManager get(TransactionContext context) {
        return new FollowEntityManagerImpl(context);
    }

    /**
     * Creates a new follow entity.
     *
     * @param source The follower entity.
     * @param target The followed user entity.
     *
     * @return The generated follow entity.
     */
    FollowEntity register(UserEntity source, UserEntity target);

    /**
     * Fetches a follow entity from the datastore.
     *
     * @param source The follower entity.
     * @param target The followed user entity.
     *
     * @return The fetched follow entity if it exists, {@code null} otherwise.
     */
    default FollowEntity find(UserEntity source, UserEntity target) {
        return find(source, target.getKey());
    }

    /**
     * Fetches a follow entity from the datastore.
     *
     * @param sourceKey The key of the follower entity.
     * @param target    The followed user entity.
     *
     * @return The fetched follow entity if it exists, {@code null} otherwise.
     */
    default FollowEntity find(Key sourceKey, UserEntity target) {
        return find(sourceKey, target.getKey());
    }

    /**
     * Fetches a follow entity from the datastore.
     *
     * @param source    The follower entity.
     * @param targetKey The key of the followed user entity.
     *
     * @return The fetched follow entity if it exists, {@code null} otherwise.
     */
    default FollowEntity find(UserEntity source, Key targetKey) {
        return find(source.getKey(), targetKey);
    }

    /**
     * Fetches a follow entity from the datastore.
     *
     * @param sourceKey The key of the follower entity.
     * @param targetKey The key of the followed user entity.
     *
     * @return The fetched follow entity if it exists, {@code null} otherwise.
     */
    FollowEntity find(Key source, Key target);

    /**
     * Fetches all follows from a user from the datastore.
     *
     * @param user The user entity to fetch all follows from.
     *
     * @return An iterator of all follows from <b>user</b>.
     */
    default Iterator<FollowEntity> findAllFrom(UserEntity user) {
        return findAllFrom(user.getKey());
    }

    /**
     * Fetches all follows from a user from the datastore.
     *
     * @param userKey The key of the user entity to fetch all follows from.
     *
     * @return An iterator of all follows from the user entity associated to <b>userKey</b>.
     */
    Iterator<FollowEntity> findAllFrom(Key userKey);

    /**
     * Fetches all follows to a user from the datastore.
     *
     * @param user The user entity to fetch all follows to.
     *
     * @return An iterator of all follows to <b>user</b>.
     */
    default Iterator<FollowEntity> findAllTo(UserEntity user) {
        return findAllTo(user.getKey());
    }

    /**
     * Fetches all follows from a user to the datastore.
     *
     * @param userKey The key of the user entity to fetch all follows to.
     *
     * @return An iterator of all follows to the user entity associated to <b>userKey</b>.
     */
    Iterator<FollowEntity> findAllTo(Key userKey);
}
