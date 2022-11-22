package tinygram.datastore.util;

import com.google.appengine.api.datastore.Key;

public interface TypedEntity {

    Key getKey();

    String getKind();

    void persistUsing(TransactionContext context);

    void forgetUsing(TransactionContext context);
}
