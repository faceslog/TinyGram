package tinygram.api.util;

import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.util.TypedEntity;

public class EntityResponse<T extends TypedEntity> {

    public final String key;

    public EntityResponse(T entity) {
        key = KeyFactory.keyToString(entity.getKey());
    }
}
