package tinygram.data;

import com.google.api.server.spi.config.Transformer;
import com.google.appengine.api.datastore.KeyFactory;

public interface TypedEntityTransformer<TFrom extends TypedEntity, TTo extends TypedEntityTransformer.Response> extends Transformer<TFrom, TTo> {

    public static abstract class Response {
        public final String key;

        public Response(TypedEntity entity) {
            key = KeyFactory.keyToString(entity.getKey());
        }
    }

    @Override
    default TFrom transformFrom(TTo in) {
        throw new IllegalStateException("Undefined entity deserialization.");
    }
}
