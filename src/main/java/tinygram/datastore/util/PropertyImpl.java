package tinygram.datastore.util;

class PropertyImpl<T> implements Property<T> {

    private final String name;
    private final boolean indexed;

    public PropertyImpl(String name) {
        this(name, false);
    }

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
