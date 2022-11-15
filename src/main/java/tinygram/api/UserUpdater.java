package tinygram.api;

import tinygram.datastore.UserEntity;
import tinygram.datastore.UserProvider;

public class UserUpdater implements EntityUpdater<UserEntity> {
    
    public String name;
    public String image;
    public Boolean followed;

    @Override
    public UserEntity update(UserEntity entity) {
        if (name != null) {
            entity.setName(name);
        }

        if (image != null) {
            entity.setImage(image);
        }

        final UserProvider userProvider = entity.getUserProvider();
        if (followed != null && userProvider.exists()) {
            final UserEntity currentUser = userProvider.get();

            // Doesn't scale, but a user should not be able to spam follow hundreds of people.
            if (followed) {
                entity.addFollow(currentUser);
            } else {
                entity.removeFollow(currentUser);
            }
        }

        return entity;
    }
}
