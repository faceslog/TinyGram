package tinygram.datastore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public abstract class AbstractTypedEntity implements TypedEntity {

    private static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private final Entity raw;
    private Set<TypedEntity> relatedEntities;

    public AbstractTypedEntity() {
        raw = new Entity(getKind());
        relatedEntities = new HashSet<>();
    }

    public AbstractTypedEntity(String keyName) {
        raw = new Entity(getKind(), keyName);
        relatedEntities = new HashSet<>();
    }

    public AbstractTypedEntity(Entity raw) {
        this.raw = Objects.requireNonNull(raw);
        KindException.ensure(getKind(), raw);
        relatedEntities = new HashSet<>();
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

    protected boolean addRelatedEntity(TypedEntity entity) {
        return relatedEntities.add(entity);
    }

    @Override
    public void persist() {
        datastore.put(raw);

        final Set<TypedEntity> toPersist = relatedEntities;
        relatedEntities = new HashSet<>();
        toPersist.forEach(TypedEntity::persist);
    }

    @Override
    public void forget() {
        datastore.delete(getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object == this || object instanceof TypedEntity &&
                ((TypedEntity) object).getKey().equals(getKey());
    }

    @Override
    public String toString() {
        return raw.toString();
    }
}
