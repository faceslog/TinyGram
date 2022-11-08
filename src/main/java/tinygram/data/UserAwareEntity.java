package tinygram.data;

import java.util.Objects;

import com.google.appengine.api.datastore.Entity;

public abstract class UserAwareEntity extends AbstractTypedEntity {

    private UserProvider userProvider;

    public UserAwareEntity(UserProvider userProvider) {
        setUserProvider(userProvider);
    }

    public UserAwareEntity(UserProvider userProvider, String keyName) {
        super(keyName);

        setUserProvider(userProvider);
    }

    public UserAwareEntity(UserProvider userProvider, Entity raw) {
        super(raw);

        setUserProvider(userProvider);
    }

    protected void setUserProvider(UserProvider userProvider) {
        this.userProvider = Objects.requireNonNull(userProvider);
    }

    protected UserProvider getUserProvider() {
        return userProvider;
    }
}
