package com.example.popularmoviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    private String movieTitle;
    private String moviePoster;
    private String movieRelease;
    private String movieRate;
    private String movieOverview;

    public Movie(String movieTitle, String moviePoster, String movieRelease, String movieRate, String movieOverview) {
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieRelease = movieRelease;
        this.movieRate = movieRate;
        this.movieOverview = movieOverview;
    }


    protected Movie(Parcel in) {
        movieTitle = in.readString();
        movieOverview = in.readString();
        moviePoster = in.readString();
        movieRate = in.readString();
        movieRelease = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(moviePoster);
        dest.writeString(movieRelease);
        dest.writeString(movieRate);
        dest.writeString(movieOverview);
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }
}
