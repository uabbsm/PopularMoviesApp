package com.example.popularmoviesapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM favorite_movies ORDER BY movie_release")
    FavoriteMovie[] loadAllFavoriteMovies();

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    FavoriteMovie loadMovie(String id);

    @Insert
    void addFavoriteMovie(FavoriteMovie movie);

    @Delete
    void removeFavoriteMovie(FavoriteMovie movie);
}