package tinygram.api;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class BaseApiPath implements ApiPath {

    private final String root;
    private final String path;

    public BaseApiPath(String root, Key resource) {
        this(root, KeyFactory.keyToString(resource));
    }

    public BaseApiPath(String root, String path) {
        this.root = root;
        this.path = path;
    }

    @Override
    public String getAbsolute() {
        return root + "/" + path;
    }

    @Override
    public String getRelative() {
        return path;
    }
}
