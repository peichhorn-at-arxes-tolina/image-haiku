package de.fips.dropwizardmongo.managed;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;

public class MongoManaged implements Managed {

    private final MongoClient mongoClient;

    public MongoManaged(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }
}
