package tinygram.api.config;

import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.TypedEntity;

public class EntityResponse<T extends TypedEntity> {

    public final String key;

    public EntityResponse(T entity) {
        key = KeyFactory.keyToString(entity.getKey());
    }
}
