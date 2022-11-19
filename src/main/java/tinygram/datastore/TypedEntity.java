package tinygram.datastore;

import com.google.appengine.api.datastore.Key;

public interface TypedEntity {

    Key getKey();

    String getKind();

    void persistUsing(Persister persister);

    void forgetUsing(Forgetter forgetter);
}
