package com.example.moviestreaming.model;

public class Comment {

    String text, id ,validation ,date ,username;

    public Comment() {
    }
    
    public Comment(String text , String id , String date , String username){
        
        this.text = text;
        this.id = id;
        this.date = date;
        this.username = username;
        
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
