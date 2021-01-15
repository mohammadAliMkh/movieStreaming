package com.example.moviestreaming.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavoriteMovie")
public class FavoriteMovie {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;


    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "director")
    public String director;

    @ColumnInfo(name = "rate")
    public String rate;

    @ColumnInfo(name = "rank")
    public String rank;

    @ColumnInfo(name = "publish")
    public String publish;

    @ColumnInfo(name = "image_link")
    public String image_link;

    @ColumnInfo(name = "genre")
    public String genre;

    @ColumnInfo(name = "category")
    public String category;
}
