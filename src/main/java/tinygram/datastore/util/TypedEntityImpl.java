package tinygram.datastore.util;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * An basic implementation of the {@TypedEntity} interface, which is intended to be specialized for
 * any entity kind, providing operations by using {@TypedEntityInternal}.
 */
public abstract class TypedEntityImpl implements TypedEntityInternal {

    private final Entity raw;
    private Set<TypedEntity> relatedEntities;

    /**
     * Creates a new entity, not already added it to the datastore.
     *
     * @param kind The entity kind.
     */
    public TypedEntityImpl(String kind) {
        raw = new Entity(kind);
        relatedEntities = new HashSet<>();
    }

    /**
     * Creates a new entity, not already added it to the datastore.
     *
     * @param kind    The entity kind.
     * @param keyName The entity name, mandatory when trying to fetch the entity within the same
     *                transaction.
     */
    public TypedEntityImpl(String kind, String keyName) {
        raw = new Entity(kind, keyName);
        relatedEntities = new HashSet<>();
    }

    /**
     * Encapsulates an already existing entity.
     *
     * @param kind The entity kind.
     * @param raw  The raw entity.
     */
    public TypedEntityImpl(String kind, Entity raw) {
        this.raw = Objects.requireNonNull(raw);
        KindException.ensure(getKind(), raw);
        relatedEntities = new HashSet<>();
    }

    @Override
    public String getKind() {
        return raw.getKind();
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

    /**
     * Adds all related entities to the datastore or updates these within a given transaction.
     *
     * @param context The transaction context.
     */
    private void persistRelatedEntities(TransactionContext context) {
        final Set<TypedEntity> toPersist = relatedEntities;
        relatedEntities = new HashSet<>();
        toPersist.forEach(entity -> entity.persistUsing(context));
    }

    @Override
    public void persistUsing(TransactionContext context) {
        context.persist(raw);
        persistRelatedEntities(context);
    }

    @Override
    public void forgetUsing(TransactionContext context) {
        context.forget(raw);
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
