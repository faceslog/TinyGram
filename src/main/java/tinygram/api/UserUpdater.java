package tinygram.api;

import tinygram.datastore.UserEntity;

public class UserUpdater {

    public String name;
    public String image;

    public void update(UserEntity user) {
        if (name != null) {
            user.setName(name);
        }

        if (image != null) {
            user.setImage(image);
        }
    }
}
