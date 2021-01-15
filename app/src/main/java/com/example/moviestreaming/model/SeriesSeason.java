package com.example.moviestreaming.model;

public class SeriesSeason {
    String id, home_id , number , episodes , img_link;

    public SeriesSeason(String id, String home_id, String number, String episodes, String img_link) {
        this.id = id;
        this.home_id = home_id;
        this.number = number;
        this.episodes = episodes;
        this.img_link = img_link;
    }

    public SeriesSeason() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }


}
