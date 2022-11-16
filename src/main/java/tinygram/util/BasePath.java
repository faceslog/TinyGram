package tinygram.util;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class BasePath implements Path {

    private final String root;
    private final String path;

    public BasePath(String root, Key resource) {
        this(root, KeyFactory.keyToString(resource));
    }

    public BasePath(String root, String path) {
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
