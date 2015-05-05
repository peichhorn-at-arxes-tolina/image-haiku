package de.fips.imagehaiku.domain;

import java.util.UUID;

public class Haiku {

    private UUID id;
    private String author;
    private String text;
    private String image;

    public String getId() {
        return id.toString();
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
        haiku.id = UUID.randomUUID();
        haiku.author = author;
        haiku.text = text;
        haiku.image = image;
        return haiku;
    }
}
