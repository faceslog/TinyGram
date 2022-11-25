package tinygram.api.util;

import com.google.api.server.spi.config.Transformer;

/**
 * An interface to convert a {@link Resource} to a {@link ResourceResponse}.
 */
public interface ResourceTransformer<T extends Resource, U> extends Transformer<T, ResourceResponse<U>> {

    /**
     * Converts a resource to a JSON-serializable response object.
     *
     * @param resource The resource to convert.
     *
     * @return The resource response object representing <b>resource</b>.
     */
    ResourceResponse<U> transformTo(T resource);

    @Override
    default T transformFrom(ResourceResponse<U> response) {
        throw new UnsupportedOperationException("Resource deserialization is not handled with " +
                "this resource transformer. Maybe the wrong one got selected by Google?");
    }
}
