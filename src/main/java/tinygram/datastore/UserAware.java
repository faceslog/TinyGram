package tinygram.datastore;

@FunctionalInterface
public interface UserAware {

    UserProvider getUserProvider();
}
