package com.example.popularmoviesapp.utilities;

import android.os.AsyncTask;

import com.example.popularmoviesapp.models.DetailMovie;
import com.example.popularmoviesapp.models.Movie;

import java.net.URL;

// Created a generic AsyncTask class. According toi this thread:
// https://stackoverflow.com/questions/24773574/android-generic-asynctask-class

public class FetchAsyncTaskBase extends AsyncTask<Void, Void, Void> {

    private AsyncTaskCompleteListener callback;
    private String searchQuery;
    private Movie[] mMovies;
    private DetailMovie mDetailMovies;

    public FetchAsyncTaskBase(String query, AsyncTaskCompleteListener callback) {
        this.searchQuery = query;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        URL movieRequestURL = NetworkUtils.buildUrl(searchQuery);

        try {
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestURL);
            switch (searchQuery) {
                case "top_rated":
                    mMovies = BaseJsonUtils.getSimpleMovieStringsFromJson(jsonMovieResponse);
                    break;
                case "popular":
                    mMovies = BaseJsonUtils.getSimpleMovieStringsFromJson(jsonMovieResponse);
                    break;
                default:
                    mDetailMovies = BaseJsonUtils.getDetailMovieStringsFromJson(jsonMovieResponse);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        if (searchQuery.equals("popular") || searchQuery.equals("top_rated")) {
            callback.onTaskComplete(mMovies);
        } else {
            callback.onTaskComplete(mDetailMovies);
        }

    }

}


