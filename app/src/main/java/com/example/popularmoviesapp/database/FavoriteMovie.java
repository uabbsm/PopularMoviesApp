package com.example.popularmoviesapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "movie_title")
    private String movieTitle;
    @ColumnInfo(name = "movie_poster")
    private String moviePoster;
    @ColumnInfo(name = "movie_release")
    private String movieRelease;
    @ColumnInfo(name = "movie_rate")
    private String movieRate;
    @ColumnInfo(name = "movie_overview")
    private String movieOverview;
    @ColumnInfo(name = "movie_duration")
    private String movieDuration;

    public FavoriteMovie(int id, String movieTitle, String moviePoster, String movieRelease, String movieRate, String movieOverview, String movieDuration) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieRelease = movieRelease;
        this.movieRate = movieRate;
        this.movieOverview = movieOverview;
        this.movieDuration = movieDuration;
    }

    public int getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieDuration() {
        return movieDuration;
    }
}
