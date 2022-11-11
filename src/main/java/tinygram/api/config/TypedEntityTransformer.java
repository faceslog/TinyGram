package tinygram.api.config;

import com.google.api.server.spi.config.Transformer;
import com.google.appengine.api.datastore.KeyFactory;

import tinygram.datastore.TypedEntity;

public interface TypedEntityTransformer<T extends TypedEntity> extends Transformer<T, TypedEntityTransformer.Response<T>> {

    public static class Response<T extends TypedEntity> {
        public final String key;

        public Response(T entity) {
            key = KeyFactory.keyToString(entity.getKey());
        }
    }

    @Override
    default T transformFrom(TypedEntityTransformer.Response<T> in) {
        throw new IllegalStateException("Undefined entity deserialization.");
    }
}
