package tinygram.api.config;

import java.util.HashMap;
import java.util.Map;

import tinygram.api.ApiPath;
import tinygram.datastore.TypedEntity;

public class ResourceResponse<T extends TypedEntity> {

    public final EntityResponse<T> result;
    public final Map<String, String> _links;

    public ResourceResponse(EntityResponse<T> result) {
        this.result = result;
        _links = new HashMap<>();
    }

    public boolean addLink(String name, ApiPath path) {
        return addLink(name, path.getRelative());
    }

    public boolean addLink(String name, String url) {
        return _links.putIfAbsent(name, url) == null;
    }
}
