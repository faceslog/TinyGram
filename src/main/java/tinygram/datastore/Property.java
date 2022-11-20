package tinygram.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

public interface Property<T> {

    static Property<Date> date(String name) {
        return new PropertyImpl<>(name);
    }

    static Property<Key> key(String name) {
        return new PropertyImpl<>(name);
    }

    static Property<Long> number(String name) {
        return new PropertyImpl<>(name);
    }

    static Property<String> string(String name) {
        return new PropertyImpl<>(name);
    }

    static Property<Date> indexedDate(String name) {
        return new PropertyImpl<>(name, true);
    }

    static Property<Key> indexedKey(String name) {
        return new PropertyImpl<>(name, true);
    }

    static Property<Long> indexedNumber(String name) {
        return new PropertyImpl<>(name, true);
    }

    static Property<String> indexedString(String name) {
        return new PropertyImpl<>(name, true);
    }

    String getName();

    boolean isIndexed();
}
