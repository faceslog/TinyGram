package tinygram.datastore;

public interface PostRepository extends Repository<PostEntity> {

    PostEntity register(UserEntity user, String image, String description);
}
