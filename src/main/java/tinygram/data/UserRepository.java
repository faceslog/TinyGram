package tinygram.data;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import tinygram.Util;

public final class UserRepository {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public static UserEntity get(Key key) throws EntityNotFoundException {
        return new UserEntity(datastore.get(key));
    }

    public static UserEntity find(User user) {
        return find(Util.DEBUG ? "test" : user.getId());
    }

    public static UserEntity find(String userId) {
        final Query query = new Query(UserEntity.KIND).setFilter(new FilterPredicate(UserEntity.FIELD_ID, FilterOperator.EQUAL, userId));
        final Entity raw = datastore.prepare(query).asSingleEntity();
        return raw == null ? null : new UserEntity(raw);
    }
}
