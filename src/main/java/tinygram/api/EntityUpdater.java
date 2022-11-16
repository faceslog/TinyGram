package tinygram.api;

import tinygram.datastore.TypedEntity;

public interface EntityUpdater<T extends TypedEntity> {

    T update(T userEntity);
}
