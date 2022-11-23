package tinygram.datastore.util;

/**
 * An implementation of the {@link Property} interface.
 *
 * @param <T> The Java property value type.
 */
class PropertyImpl<T> implements Property<T> {

    private final String name;
    private final boolean indexed;

    /**
     * Creates an unindexed property reference.
     *
     * @param name The property name.
     */
    public PropertyImpl(String name) {
        this(name, false);
    }

    /**
     * Creates a property reference.
     *
     * @param name    The property name.
     * @param indexed Whether the property is indexed.
     */
    public PropertyImpl(String name, boolean indexed) {
        this.name = name;
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
