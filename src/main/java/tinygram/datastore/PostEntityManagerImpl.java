package tinygram.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import tinygram.datastore.util.TransactionContext;
import tinygram.util.IteratorUtils;

/**
 * An implementation of the {@link PostEntityManager} interface.
 */
class PostEntityManagerImpl implements PostEntityManager {

    /**
     * The current transaction context.
     */
    private final TransactionContext context;

    /**
     * Creates a post entity interface.
     *
     * @param context The transaction context.
     */
    public PostEntityManagerImpl(TransactionContext context) {
        this.context = context;
    }

    @Override
    public PostEntity register(UserEntity owner, String image, String description) {
        final UserEntityManager userManager = UserEntityManager.get(context);
        final FollowEntityManager followManager = FollowEntityManager.get(context);
        final FeedNodeEntityManager feedManager = FeedNodeEntityManager.get(context);

        final PostEntityImpl post = new PostEntityImpl(owner, image, description);

        // Add the new post to feeds of all user followers.
        followManager.findAllTo(owner).forEachRemaining(follow -> {
            try {
                final UserEntity follower = userManager.get(follow.getSource());
                final FeedNodeEntity feedNode = feedManager.register(follower, post);
                post.addRelatedEntity(feedNode);
            } catch (EntityNotFoundException e) {
                throw new IllegalStateException(e);
            }
        });

        return post;
    }

    @Override
    public PostEntity get(Key key) throws EntityNotFoundException {
        return new PostEntityImpl(context.get(key));
    }

    @Override
    public PostEntity findLatest(Key userKey) {
        final Filter filter = new FilterPredicate(PostEntity.PROPERTY_OWNER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(PostEntity.KIND).setFilter(filter)
                .addSort(PostEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final Iterator<Entity> iterator = context.findAll(query);
        return iterator.hasNext() ? new PostEntityImpl(iterator.next()) : null;
    }

    @Override
    public Iterator<PostEntity> findAll(Key userKey) {
        final Filter filter = new FilterPredicate(PostEntity.PROPERTY_OWNER.getName(), FilterOperator.EQUAL, userKey);
        final Query query = new Query(PostEntity.KIND).setFilter(filter)
                .addSort(PostEntity.PROPERTY_DATE.getName(), SortDirection.DESCENDING);

        final Iterator<Entity> iterator = context.findAll(query);
        return IteratorUtils.map(iterator, PostEntityImpl::new);
    }
}
