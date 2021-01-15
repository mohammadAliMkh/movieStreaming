package com.example.moviestreaming.database.database;

import android.content.Context;

import com.example.moviestreaming.database.Interface.FavoriteMovieDao;
import com.example.moviestreaming.database.model.FavoriteMovie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class} , version = 1)
public abstract class FavoriteDB extends RoomDatabase {

    public abstract FavoriteMovieDao getFavoriteMovieDao();

    private static FavoriteDB favoriteDB;

    public static FavoriteDB getNewInstance(Context context){

        if(favoriteDB == null){
            favoriteDB = Room.databaseBuilder(context , FavoriteDB.class , "favorite_movie_db")
                    .allowMainThreadQueries()
                    .build();
        }

        return  favoriteDB;
    }
}
