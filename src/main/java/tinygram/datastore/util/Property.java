package tinygram.datastore.util;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

/**
 * A reference to a datatore entity property.
 *
 * @param <T> The Java property value type.
 */
public interface Property<T> {

    /**
     * Creates a reference to an unindexed date property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<Date> date(String name) {
        return new PropertyImpl<>(name, false);
    }

    /**
     * Creates a reference to an unindexed entity key property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<Key> key(String name) {
        return new PropertyImpl<>(name, false);
    }

    /**
     * Creates a reference to an unindexed numeric property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<Long> number(String name) {
        return new PropertyImpl<>(name, false);
    }

    /**
     * Creates a reference to an unindexed string property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<String> string(String name) {
        return new PropertyImpl<>(name, false);
    }

    /**
     * Creates a reference to an indexed date property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<Date> indexedDate(String name) {
        return new PropertyImpl<>(name, true);
    }

    /**
     * Creates a reference to an indexed entity key property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<Key> indexedKey(String name) {
        return new PropertyImpl<>(name, true);
    }

    /**
     * Creates a reference to an indexed numeric property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<Long> indexedNumber(String name) {
        return new PropertyImpl<>(name, true);
    }

    /**
     * Creates a reference to an indexed string property.
     *
     * @param name The property name.
     *
     * @return The generated property reference.
     */
    static Property<String> indexedString(String name) {
        return new PropertyImpl<>(name, true);
    }

    /**
     * Gets the name of the property.
     *
     * @return The string property name.
     */
    String getName();

    /**
     * Indicates whether the property is, and should be, indexed in the datastore.
     *
     * @return {@code true} if the property is indexed, {@code false} otherwise.
     */
    boolean isIndexed();
}
