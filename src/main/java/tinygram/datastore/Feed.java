package tinygram.datastore;

import java.util.Collection;

import com.google.appengine.api.datastore.Key;

public interface Feed {

    Collection<Key> getPosts();

    String getNextPage();

    boolean isAboutFollowed();

    Key getUser();
}
