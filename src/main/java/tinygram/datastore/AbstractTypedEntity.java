package tinygram.datastore;

import java.util.Objects;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import tinygram.Util;

public abstract class AbstractTypedEntity implements TypedEntity {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private final Entity raw;

    public AbstractTypedEntity() {
        raw = new Entity(getKind());
    }

    public AbstractTypedEntity(String keyName) {
        raw = new Entity(getKind(), keyName);
    }

    public AbstractTypedEntity(Entity raw) {
        Objects.requireNonNull(raw);
        KindException.ensure(getKind(), raw);
        this.raw = Objects.requireNonNull(raw);
    }

    @Override
    public Key getKey() {
        return raw.getKey();
    }

    protected <T> T getProperty(String propertyName) {
        return Util.extractProperty(raw, propertyName);
    }

    protected void setProperty(String propertyName, Object value) {
        raw.setProperty(propertyName, value);
    }

    @Override
    public void persist() {
        datastore.put(raw);
    }
}
