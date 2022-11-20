package tinygram.datastore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

abstract class TypedEntityImpl implements TypedEntity, TypedEntityInternal {

    private final Entity raw;
    private Set<TypedEntity> relatedEntities;

    public TypedEntityImpl() {
        raw = new Entity(getKind());
        relatedEntities = new HashSet<>();
    }

    public TypedEntityImpl(String keyName) {
        raw = new Entity(getKind(), keyName);
        relatedEntities = new HashSet<>();
    }

    public TypedEntityImpl(Entity raw) {
        this.raw = Objects.requireNonNull(raw);
        KindException.ensure(getKind(), raw);
        relatedEntities = new HashSet<>();
    }

    @Override
    public Key getKey() {
        return raw.getKey();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProperty(Property<? extends T> property) {
        return (T) raw.getProperty(property.getName());
    }

    @Override
    public <T> void setProperty(Property<? super T> property, T value) {
        if (property.isIndexed()) {
            raw.setIndexedProperty(property.getName(), value);
        } else {
            raw.setUnindexedProperty(property.getName(), value);
        }
    }

    @Override
    public boolean addRelatedEntity(TypedEntity entity) {
        return relatedEntities.add(entity);
    }

    private void persistRelatedEntities(Persister persister) {
        final Set<TypedEntity> toPersist = relatedEntities;
        relatedEntities = new HashSet<>();
        toPersist.forEach(entity -> entity.persistUsing(persister));
    }

    @Override
    public void persistUsing(Persister persister) {
        persister.persist(raw);
        persistRelatedEntities(persister);
    }

    @Override
    public void forgetUsing(Forgetter forgetter) {
        // persistRelatedEntities(forgetter);
        forgetter.forget(raw);
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
