package de.fips.imagehaiku.domain;

import org.mongodb.morphia.Datastore;

import java.util.Optional;

public class ImageRepository {

    private final Datastore datastore;

    public ImageRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    public Optional<Image> save(final Image image) {
        datastore.save(image);
        return Optional.of(image);
    }

    public Optional<Image> imageByFilename(final String filename) {
        return Optional.of(datastore.find(Image.class).field("filename").equal(filename).get());
    }
}
