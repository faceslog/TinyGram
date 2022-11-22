package tinygram.datastore.util;

interface TypedEntityInternal {

    <T> T getProperty(Property<? extends T> property);

    <T> void setProperty(Property<? super T> property, T value);

    boolean addRelatedEntity(TypedEntity entity);
}
