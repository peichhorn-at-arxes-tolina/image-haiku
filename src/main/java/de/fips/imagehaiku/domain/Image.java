package de.fips.imagehaiku.domain;

import de.fips.common.lang.Base62;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity(value = "image", noClassnameStored = true)
public class Image {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String filename;
    private String mediaType;
    private byte[] data;

    public String getId() {
        return Base62.fromHex(id.toHexString());
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
        image.id = new ObjectId();
        image.filename = filename;
        image.mediaType = mediaType;
        image.data = data;
        return image;
    }
}
