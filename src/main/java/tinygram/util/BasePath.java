package tinygram.util;

import java.util.Objects;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * A basic implementation of the {@link Path} interface.
 */
public class BasePath implements Path {

    /**
     * The root path component.
     */
    private final String root;
    /**
     * The relative path component.
     */
    private final String path;

    /**
     * Creates a path from a root component and an entity key.
     *
     * @param root The root path component.
     * @param key  The entity key, acting as the relative path suffix.
     */
    public BasePath(String root, Key key) {
        this(root, KeyFactory.keyToString(key));
    }

    /**
     * Creates a path from both a root and a relative path component.
     *
     * @param root The root path component.
     * @param path The relative path component.
     */
    public BasePath(String root, String path) {
        this.root = Objects.requireNonNull(root);
        this.path = Objects.requireNonNull(path);
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
