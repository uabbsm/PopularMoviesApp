package com.example.popularmoviesapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    /**
     * will get all the results from favorite movies and order them by release date
     * @return
     */
    @Query("SELECT * FROM favorite_movies ORDER BY movie_release")
    LiveData<FavoriteMovie[]> loadAllFavoriteMovies();

    /**
     * will select all from favorite movies given an id
     * @param id
     * @return
     */
    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    LiveData<FavoriteMovie> loadMovie(String id);

    /**
     * add a movie to favorite
     * @param movie
     */
    @Insert
    void addFavoriteMovie(FavoriteMovie movie);

    /**
     * delete a movie from favorite
     * @param movie
     */
    @Delete
    void removeFavoriteMovie(FavoriteMovie movie);
}
