package tinygram.datastore.util;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

public interface TypedEntityManager<T extends TypedEntity> {

    T get(Key key) throws EntityNotFoundException;
}
