package de.fips.imagehaiku.domain;

import de.fips.common.lang.Base62;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity(value = "haiku", noClassnameStored = true)
public class Haiku {

    @Id
    private ObjectId id;
    private String author;
    private String text;
    private String image;
    @Indexed
    private double random;

    public String getId() {
        return Base62.fromHex(id.toHexString());
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public static Haiku create(Haiku otherHaiku) {
        return create(otherHaiku.getAuthor(), otherHaiku.getText(), otherHaiku.getImage());
    }

    public static Haiku create(String author, String text, String image) {
        Haiku haiku = new Haiku();
        haiku.id = new ObjectId();
        haiku.author = author;
        haiku.text = text;
        haiku.image = image;
        haiku.random = Math.random();
        return haiku;
    }
}
