package tinygram.data;

public class BaseUserTransformer implements TypedEntityTransformer<BaseUserEntity, BaseUserTransformer.Response> {

    public static class Response extends TypedEntityTransformer.Response {

        public final boolean followed;

        public Response(BaseUserEntity entity) {
            super(entity);

            followed = entity.getUserProvider().get().follows(entity);
        }
    }

    @Override
    public Response transformTo(BaseUserEntity entity) {
        return new Response(entity);
    }
}
