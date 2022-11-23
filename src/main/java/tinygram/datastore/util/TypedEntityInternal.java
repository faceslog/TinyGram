package tinygram.datastore.util;

/**
 * Entity operations only available to implementations of {@link TypedEntityImpl}.
 */
interface TypedEntityInternal extends TypedEntity {

    /**
     * Gets a property from the raw encapsulated entity.
     *
     * @param <T>      The Java property value type.
     *
     * @param property The property to get.
     *
     * @return The property value if it has already been set, {@code null} otherwise.
     */
    <T> T getProperty(Property<? extends T> property);

    /**
     * Sets a property value within the raw encapsulated entity.
     *
     * @param <T>      The Java property value type.
     *
     * @param property The property to set.
     * @param value    The new property value.
     */
    <T> void setProperty(Property<? super T> property, T value);

    /**
     * Annotates an entity to be added to the datastore or updated when this one will be, within a
     * transaction.
     *
     * @param entity The entity to annotate.
     *
     * @return {@code true} if the entity has not already been annotated, {@code false} otherwise.
     */
    boolean addRelatedEntity(TypedEntity entity);
}
