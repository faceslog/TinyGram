package tinygram.datastore;

import com.google.api.server.spi.auth.common.User;

import tinygram.datastore.util.TransactionContext;
import tinygram.datastore.util.TypedEntityManager;

/**
 * An interface to manage {@link UserEntity} entities within a specific transaction.
 */
public interface UserEntityManager extends TypedEntityManager<UserEntity> {

    /**
     * Gets the user entity interface for a specific transaction.
     *
     * @param context The transaction context.
     *
     * @return The user entity interface associated to <b>context</b>.
     */
    static UserEntityManager get(TransactionContext context) {
        return new UserEntityManagerImpl(context);
    }

    /**
     * Creates a new user entity.
     *
     * @param user  The Google user anthentification credentials.
     * @param name  The user name.
     * @param image The user profile picture.
     *
     * @return The generated user entity.
     */
    UserEntity register(User user, String name, String image);

    /**
     * Fetches a user entity from the datastore.
     *
     * @param user The Google user authentification credentials.
     *
     * @return The fetched user entity if it exists, {@code null} otherwise.
     */
    UserEntity find(User user);

    /**
     * Fetches a user entity from the datastore.
     *
     * @param userId The Google user identifier.
     *
     * @return The fetched user entity if it exists, {@code null} otherwise.
     */
    UserEntity find(String userId);
}
