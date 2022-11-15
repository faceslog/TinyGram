package tinygram.api.config;

import com.google.api.server.spi.config.Transformer;

public interface ResourceTransformer<T, U> extends Transformer<T, ResourceResponse<U>> {

    @Override
    default T transformFrom(ResourceResponse<U> response) {
        throw new IllegalStateException("Undefined entity deserialization.");
    }
}
