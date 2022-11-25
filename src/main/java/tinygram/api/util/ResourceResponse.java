package tinygram.api.util;

import java.util.HashMap;
import java.util.Map;

import tinygram.util.Path;

/**
 * An object to be serialized into a JSON value, encapsulating a resource object.
 *
 * <p> <b>Warning:</b> Should only be built from a {@link Resource} by using its associated
 * {@link ResourceTransformer}.
 *
 * @param <T> The resource object type.
 */
public class ResourceResponse<T> {

    /**
     * The resource object to be serialized.
     */
    public final T result;
    /**
     * A dictionary of API URLs related to the serialized resource.
     */
    public final Map<String, String> _links;

    /**
     * Creates an resource response object.
     *
     * @param result The resource object to be serialized.
     */
    public ResourceResponse(T result) {
        this.result = result;
        _links = new HashMap<>();
    }

    /**
     * Adds an API URL to the response object.
     *
     * @param name The unique string identifier associated to the URL.
     * @param path The API path representing the URL.
     *
     * @return {@code true} if the link has been properly added, {@code false} if one has already
     *         been added with the exact same identifier.
     */
    public boolean addLink(String name, Path path) {
        return addLink(name, path.getRelative());
    }

    /**
     * Adds an API URL to the response object.
     *
     * @param name The unique string identifier associated to the URL.
     * @param url  The API URL.
     *
     * @return {@code true} if the link has been properly added, {@code false} if one has already
     *         been added with the exact same identifier.
     */
    public boolean addLink(String name, String url) {
        return _links.putIfAbsent(name, url) == null;
    }
}
