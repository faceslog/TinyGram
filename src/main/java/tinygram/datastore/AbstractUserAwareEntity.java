package tinygram.datastore;

import java.util.Objects;

import com.google.appengine.api.datastore.Entity;

public abstract class AbstractUserAwareEntity extends AbstractTypedEntity implements UserAware {

    private UserProvider userProvider;

    public AbstractUserAwareEntity(UserProvider userProvider) {
        setUserProvider(userProvider);
    }

    public AbstractUserAwareEntity(UserProvider userProvider, String keyName) {
        super(keyName);

        setUserProvider(userProvider);
    }

    public AbstractUserAwareEntity(UserProvider userProvider, Entity raw) {
        super(raw);

        setUserProvider(userProvider);
    }

    protected void setUserProvider(UserProvider userProvider) {
        this.userProvider = Objects.requireNonNull(userProvider);
    }

    public UserProvider getUserProvider() {
        return userProvider;
    }
}
