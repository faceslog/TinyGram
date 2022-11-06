package tinygram.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.QueryResultList;

public class FeedResponse<T extends TypedEntity> {

    public final List<T> data;
    public final String nextpage;

    public FeedResponse(Function<Entity, T> entityBuilder, QueryResultList<Entity> results) {
        data = new ArrayList<>(results.size());
        nextpage = results.getCursor().toWebSafeString();

        results.forEach(entity -> data.add(entityBuilder.apply(entity)));
    }
}
