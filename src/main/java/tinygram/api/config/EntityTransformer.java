package tinygram.api.config;

import tinygram.datastore.TypedEntity;

public interface EntityTransformer<T extends TypedEntity> extends ResourceTransformer<T, EntityResponse<T>> {}
