package tinygram.api;

import com.google.api.server.spi.config.Transformer;

public interface ResourceTransformer<T extends Resource, U> extends Transformer<T, ResourceResponse<U>> {

    @Override
    default T transformFrom(ResourceResponse<U> response) {
        throw new IllegalStateException("Undefined entity deserialization.");
    }
}
