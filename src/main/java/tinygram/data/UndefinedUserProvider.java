package tinygram.data;

public class UndefinedUserProvider implements UserProvider {

    @Override
    public UserEntity get() {
        throw new IllegalStateException("No user to be provided.");
    }
}
