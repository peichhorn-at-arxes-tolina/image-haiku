package de.fips.dropwizardmongo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;

public class MongoHealthCheck extends HealthCheck {

    private final MongoClient mongoClient;

    public MongoHealthCheck(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        try {
            mongoClient.getDB("system").getStats();
        } catch (MongoClientException ex) {
            return Result.unhealthy(ex.getMessage());
        }
        return Result.healthy();
    }
}
