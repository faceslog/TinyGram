package tinygram.datastore.util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * An exception thrown when an entity was found with an invalid or the wrong kind.
 */
public class KindException extends RuntimeException {

    /**
     * Ensures an entity is of the correct kind.
     *
     * @param expectedKind The expected entity kind.
     * @param entity       The entity to match kinds against.
     *
     * @throws KindException If <b>entity</b> is not of the <b>expectedKind</b> kind.
     */
    public static void ensure(String expectedKind, TypedEntity entity) {
        if (!entity.getKind().equals(expectedKind))
            throw new KindException(expectedKind, entity.getKind());
    }

    /**
     * Ensures an entity is of the correct kind.
     *
     * @param expectedKind The expected entity kind.
     * @param entity       The entity to match kinds against.
     *
     * @throws KindException If <b>entity</b> is not of the <b>expectedKind</b> kind.
     */
    public static void ensure(String expectedKind, Entity entity) {
        if (!entity.getKind().equals(expectedKind))
            throw new KindException(expectedKind, entity.getKind());
    }

    /**
     * Ensures an entity is of the correct kind.
     *
     * @param expectedKind The expected entity kind.
     * @param entityKey    The entity key to match kinds against.
     *
     * @throws KindException If <b>entityKey</b> is not of the <b>expectedKind</b> kind.
     */
    public static void ensure(String expectedKind, Key entityKey) {
        if (!entityKey.getKind().equals(expectedKind))
            throw new KindException(expectedKind, entityKey.getKind());
    }

    /**
     * Creates a kind exception.
     *
     * @param expectedKind The expected entity kind.
     * @param entityKind   The actual entity kind.
     */
    public KindException(String expectedKind, String entityKind) {
        super("Entity of kind " + expectedKind + " expected, got one of kind " + entityKind + ".");
    }

    /**
     * Creates a kind exception caused by another source.
     *
     * @param expectedKind The expected entity kind.
     * @param entityKind   The actual entity kind.
     * @param cause        The initial cause.
     */
    public KindException(String expectedKind, String entityKind, Throwable cause) {
        super("Entity of kind " + expectedKind + " expected, got one of kind " + entityKind + ".", cause);
    }
}
