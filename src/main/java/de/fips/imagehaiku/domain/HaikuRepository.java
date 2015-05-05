package de.fips.imagehaiku.domain;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.Optional;

public class HaikuRepository {

    private final Datastore datastore;

    public HaikuRepository(Datastore datastore) {
        this.datastore = datastore;
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
}
