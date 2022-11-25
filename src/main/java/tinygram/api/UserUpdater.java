package tinygram.api;

import tinygram.datastore.UserEntity;

/**
 * An JSON-deserializable object, to use when updating a user entity.
 */
public class UserUpdater {

    /**
     * The user name.
     */
    public String name;
    /**
     * The user profile picture.
     */
    public String image;

    /**
     * Updates a user entity according to the deserialized provided fields.
     *
     * @param user The user entity to update.
     */
    public void update(UserEntity user) {
        if (name != null) {
            user.setName(name);
        }

        if (image != null) {
            user.setImage(image);
        }
    }
}
