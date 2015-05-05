package de.fips.imagehaiku;

import com.mongodb.MongoClient;
import de.fips.common.lang.Images;
import de.fips.dropwizardmongo.factory.MongoFactory;
import de.fips.dropwizardmongo.health.MongoHealthCheck;
import de.fips.dropwizardmongo.managed.MongoManaged;
import de.fips.imagehaiku.configuration.ImageHaikuConfiguration;
import de.fips.imagehaiku.domain.Haiku;
import de.fips.imagehaiku.domain.HaikuRepository;
import de.fips.imagehaiku.domain.Image;
import de.fips.imagehaiku.domain.ImageRepository;
import de.fips.imagehaiku.resources.HaikuResource;
import de.fips.imagehaiku.resources.ImageResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class ImageHaikuApplication extends Application<ImageHaikuConfiguration> {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length != 2) {
            args = new String[]{"server", "./server/config.yml"};
        }
        new ImageHaikuApplication().run(args);
    }

    @Override
    public String getName() {
        return "Image Haiku";
    }

    @Override
    public void initialize(final Bootstrap<ImageHaikuConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(final ImageHaikuConfiguration configuration, final Environment environment) throws Exception {
        final MongoClient mongoClient = setupMongoDb(configuration, environment);

        final Datastore datastore = new Morphia().createDatastore(mongoClient, "ImageHaiku");

        if (datastore.createQuery(Haiku.class).countAll() <= 0) {
            datastore.save(Haiku.create("Basho Matsuo", "An old silent pond...\nA frog jumps into the pond,\nsplash! Silence again.", "img001.png"));
            datastore.save(Image.create("img001.png", "image/png", Images.createExampleImage("PNG", 512, 256, new java.awt.Color(153, 217, 234))));
        }

        environment.jersey().register(new ImageResource(new ImageRepository(datastore)));
        environment.jersey().register(new HaikuResource(new HaikuRepository(datastore)));
    }

    private MongoClient setupMongoDb(final ImageHaikuConfiguration configuration, final Environment environment) throws Exception {
        final MongoFactory mongoFactory = configuration.getMongoFactory();
        final MongoClient mongoClient = mongoFactory.buildClient();
        environment.healthChecks().register("MongoDB", new MongoHealthCheck(mongoClient));
        environment.lifecycle().manage(new MongoManaged(mongoClient));
        return mongoClient;
    }
}
