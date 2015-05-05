package de.fips.imagehaiku.domain;

import java.util.UUID;

public class Image {

    private UUID id;
    private String filename;
    private String mediaType;
    private byte[] data;

    public String getId() {
        return id.toString();
    }

    public String getFilename() {
        return filename;
    }

    public String getMediaType() {
        return mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public static Image create(String filename, String mediaType, byte[] data) {
        Image image = new Image();
        image.id = UUID.randomUUID();
        image.filename = filename;
        image.mediaType = mediaType;
        image.data = data;
        return image;
    }
}
