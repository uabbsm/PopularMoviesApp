package com.example.popularmoviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable { // Implement Parcelable so we can serialize the object to be easier to send it between different components
    private String movieId;
    private String moviePoster;

    public Movie(String movieId, String moviePoster) {
        this.movieId = movieId;
        this.moviePoster = moviePoster;
    }

    protected Movie(Parcel in) { // Parcelable constructor
        movieId = in.readString();
        moviePoster = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() { // The Parcel. Creator, we read the parcel data.
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
    public void writeToParcel(Parcel dest, int flags) { // writeToParcel method we simply write all the class attributes.
        dest.writeString(movieId);
        dest.writeString(moviePoster);
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieId() {
        return movieId;
    }
}

