package com.example.moviestreaming.model;

public class HomeSlider {
    String id , name , time , publish , image_link;

    public HomeSlider() {
    }

    public HomeSlider(String id, String name, String time, String publish, String image_link) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.publish = publish;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    @Override
    public String toString() {
        return "HomeSlider{" +
                "name='" + name + '\'' +
                '}';
    }
}
