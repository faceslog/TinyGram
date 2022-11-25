package tinygram.api;

import tinygram.datastore.PostEntity;

/**
 * An JSON-deserializable object, to use when updating a post entity.
 */
public class PostUpdater {

    /**
     * The post image.
     */
    public String image;
    /**
     * The post image description.
     */
    public String description;

    /**
     * Updates a post entity according to the deserialized provided fields.
     *
     * @param post The post entity to update.
     */
    public void update(PostEntity post) {
        if (image != null) {
            post.setImage(image);
        }

        if (description != null) {
            post.setDescription(description);
        }
    }
}
