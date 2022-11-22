package tinygram.datastore.util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class KindException extends RuntimeException {

    public static void ensure(String expectedKind, TypedEntity entity) {
        if (!entity.getKind().equals(expectedKind))
            throw new KindException(expectedKind, entity.getKind());
    }

    public static void ensure(String expectedKind, Entity entity) {
        if (!entity.getKind().equals(expectedKind))
            throw new KindException(expectedKind, entity.getKind());
    }

    public static void ensure(String expectedKind, Key entityKey) {
        if (!entityKey.getKind().equals(expectedKind))
            throw new KindException(expectedKind, entityKey.getKind());
    }

    public KindException(String expectedKind, String entityKind) {
        super("Entity of kind " + expectedKind + " expected, got one of kind " + entityKind + ".");
    }

    public KindException(String expectedKind, String entityKind, Throwable cause) {
        super("Entity of kind " + expectedKind + " expected, got one of kind " + entityKind + ".", cause);
    }
}
