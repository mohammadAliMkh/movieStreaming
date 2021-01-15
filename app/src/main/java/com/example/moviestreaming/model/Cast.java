package com.example.moviestreaming.model;

public class Cast {

    String id , name , image_link;

    public Cast(){

    }

    public Cast(String name, String image_link) {
        this.name = name;
        this.image_link = image_link;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
