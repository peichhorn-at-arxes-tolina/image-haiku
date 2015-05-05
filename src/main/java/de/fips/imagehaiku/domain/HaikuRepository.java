package de.fips.imagehaiku.domain;

import de.fips.common.lang.Base62;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.Optional;

public class HaikuRepository {

    private final Datastore datastore;

    public HaikuRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    public Optional<Haiku> save(final Haiku haiku) {
        datastore.save(haiku);
        return Optional.of(haiku);
    }

    public Optional<Haiku> haikuById(final String haikuId) {
        return getObjectId(haikuId).map(id -> datastore.get(Haiku.class, id));
    }

    public Optional<Haiku> haikuByRandomGte(final double random) {
        final Query<Haiku> q = datastore.createQuery(Haiku.class);
        q.or(
                q.criteria("random").greaterThanOrEq(random)
        );
        return Optional.ofNullable(q.limit(1).order("random").get());
    }

    public Optional<Haiku> haikuByRandomLt(final double random) {
        final Query<Haiku> q = datastore.createQuery(Haiku.class);
        q.or(
                q.criteria("random").lessThan(random)
        );
        return Optional.ofNullable(q.limit(1).order("random").get());
    }

    private static Optional<ObjectId> getObjectId(String idInBase62) {
        try {
            return Optional.of(new ObjectId(Base62.toHex(idInBase62)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
