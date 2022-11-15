package tinygram.datastore;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public interface Repository<T extends TypedEntity> {

    T get(Key key) throws EntityNotFoundException;
}
