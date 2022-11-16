package tinygram.api.config;

import java.util.HashMap;
import java.util.Map;

import tinygram.util.Path;

public class ResourceResponse<T> {

    public final T result;
    public final Map<String, String> _links;

    public ResourceResponse(T result) {
        this.result = result;
        _links = new HashMap<>();
    }

    public boolean addLink(String name, Path path) {
        return addLink(name, path.getRelative());
    }

    public boolean addLink(String name, String url) {
        return _links.putIfAbsent(name, url) == null;
    }
}
