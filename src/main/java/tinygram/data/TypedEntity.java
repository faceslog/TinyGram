package tinygram.data;

import com.google.appengine.api.datastore.Key;

public interface TypedEntity {

    Key getKey();
    String getKind();
    void persist();
}
