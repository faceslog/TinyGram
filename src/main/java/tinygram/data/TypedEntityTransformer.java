package tinygram.data;

import com.google.api.server.spi.config.Transformer;
import com.google.appengine.api.datastore.KeyFactory;

public abstract class TypedEntityTransformer<TFrom extends TypedEntity, TTo extends TypedEntityTransformer.Response> implements Transformer<TFrom, TTo> {

    public static abstract class Response {
        public final String key;

        public Response(TypedEntity entity) {
            key = KeyFactory.keyToString(entity.getKey());
        }
    }

    @Override
    public TFrom transformFrom(TTo in) {
        return null;
    }
}
