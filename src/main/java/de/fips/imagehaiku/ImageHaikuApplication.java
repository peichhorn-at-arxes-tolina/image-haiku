package de.fips.imagehaiku;

import de.fips.imagehaiku.configuration.ImageHaikuConfiguration;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    }
}
