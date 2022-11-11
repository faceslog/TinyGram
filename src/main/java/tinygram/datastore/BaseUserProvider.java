package tinygram.datastore;

public class BaseUserProvider implements UserProvider {

    private final UserEntity entity;

    public BaseUserProvider(UserEntity entity) {
        this.entity = entity;
    }

    @Override
    public UserEntity get() {
        return entity;
    }
}
