package de.fips.imagehaiku.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.fips.dropwizardmongo.factory.MongoFactory;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class ImageHaikuConfiguration extends Configuration {

    @NotNull
    private MongoFactory mongoFactory = new MongoFactory();

    @JsonCreator
    public ImageHaikuConfiguration(
            @JsonProperty("mongoDB") MongoFactory mongoFactory
    ) {
        this.mongoFactory = mongoFactory;
    }

    @JsonProperty("mongoDB")
    public MongoFactory getMongoFactory() {
        return this.mongoFactory;
    }
}
