package tinygram.datastore;

import tinygram.datastore.util.Property;
import tinygram.datastore.util.TypedEntity;

public interface CounterShardEntity extends TypedEntity {

    static String KIND(CounterEntity counter) {
        return counter.getKind() + '_' + counter.getKey().getName();
    }

    static final Property<Long> PROPERTY_VALUE = Property.number("value");

    long getValue();

    void increment();

    void decrement();
}
