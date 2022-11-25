package tinygram.api.util;

import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.util.TypedEntity;

/**
 * An object to be serialized into a JSON value, encapsulating a datastore entity.
 *
 * <p> <b>Warning:</b> Should only be built from a {@link Resource} by using its associated
 * {@link ResourceTransformer}.
 *
 * @param <T> The typed entity type.
 */
public class EntityResponse<T extends TypedEntity> {

    /**
     * The serialized entity key.
     */
    public final String key;

    /**
     * Creates an entity response object.
     *
     * @param entity The entity to be serialized.
     */
    public EntityResponse(T entity) {
        key = KeyFactory.keyToString(entity.getKey());
    }
}
