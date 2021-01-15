package com.example.moviestreaming.model;

public class Intro {

    String id , description , image_link;

    public Intro(){

    }

    public Intro(String id, String description, String image_link) {
        this.id = id;
        this.description = description;
        this.image_link = image_link;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
