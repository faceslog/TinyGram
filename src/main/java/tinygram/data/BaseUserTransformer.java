package tinygram.data;

public class BaseUserTransformer implements TypedEntityTransformer<BaseUserEntity, BaseUserTransformer.Response> {

    public static class Response extends TypedEntityTransformer.Response {

        public final Boolean followed;

        public Response(BaseUserEntity entity) {
            super(entity);

            UserEntity baseUser = null;
            try {
                baseUser = entity.getUserProvider().get();
            } catch (final IllegalStateException e) {}

            followed = baseUser == null ? null : baseUser.follows(entity);
        }
    }

    @Override
    public Response transformTo(BaseUserEntity entity) {
        return new Response(entity);
    }
}
