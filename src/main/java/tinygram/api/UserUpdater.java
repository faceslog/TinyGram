package tinygram.api;

import tinygram.datastore.UserEntity;

public class UserUpdater implements EntityUpdater<UserEntity> {
    
    public String name;
    public String image;
    public Boolean followed;

    @Override
    public UserEntity update(UserEntity userEntity) {
        if (name != null) {
            userEntity.setName(name);
        }

        if (image != null) {
            userEntity.setImage(image);
        }

        if (followed != null && userEntity.getUserProvider().exists()) {
            if (followed) {
                userEntity.addFollow(userEntity.getUserProvider().get());
            } else {
                userEntity.removeFollow(userEntity.getUserProvider().get());
            }
        }

        return userEntity;
    }
}
