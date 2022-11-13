package tinygram.api.config;

import com.google.api.server.spi.config.Transformer;

import tinygram.datastore.TypedEntity;

public interface EntityTransformer<T extends TypedEntity> extends Transformer<T, ResourceResponse<T>> {

    @Override
    default T transformFrom(ResourceResponse<T> response) {
        throw new IllegalStateException("Undefined entity deserialization.");
    }
}
