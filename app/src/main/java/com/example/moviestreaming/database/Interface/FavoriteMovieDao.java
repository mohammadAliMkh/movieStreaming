package com.example.moviestreaming.database.Interface;

import com.example.moviestreaming.database.model.FavoriteMovie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Observable;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM FavoriteMovie")
    Observable<List<FavoriteMovie>> getFavoriteMovies();

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteMovie WHERE id=:id)")
    int isLiked(String id);

    @Insert
    void likeMovie(FavoriteMovie favoriteMovie);

    @Delete
    int unLikeMovie(FavoriteMovie favoriteMovie);
}
