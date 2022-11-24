package tinygram.datastore.util;

import java.util.Objects;

/**
 * An implementation of the {@link Property} interface.
 *
 * @param <T> The Java property value type.
 */
class PropertyImpl<T> implements Property<T> {

    /**
     * The property name.
     */
    private final String name;
    /**
     * Whether the property is indexed or not.
     */
    private final boolean indexed;

    /**
     * Creates a property reference.
     *
     * @param name    The property name.
     * @param indexed Whether the property is indexed or not.
     */
    public PropertyImpl(String name, boolean indexed) {
        this.name = Objects.requireNonNull(name);
        this.indexed = indexed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isIndexed() {
        return indexed;
    }

    @Override
    public String toString() {
        return name + " [INDEXED]";
    }
}
