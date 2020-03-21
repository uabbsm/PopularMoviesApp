package com.example.popularmoviesapp.models;

public class DetailMovie {
    private String movieTitle;
    private String moviePoster;
    private String movieRelease;
    private String movieRate;
    private String movieOverview;
    private String movieDuration;

    public DetailMovie(String movieTitle, String moviePoster, String movieRelease, String movieRate, String movieOverview, String movieDuration) {
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieRelease = movieRelease;
        this.movieRate = movieRate;
        this.movieOverview = movieOverview;
        this.movieDuration = movieDuration;
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