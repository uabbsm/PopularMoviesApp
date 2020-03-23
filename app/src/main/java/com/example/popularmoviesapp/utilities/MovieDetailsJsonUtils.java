package com.example.popularmoviesapp.utilities;

import com.example.popularmoviesapp.models.Movie;
import com.example.popularmoviesapp.models.DetailMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MovieDetailsJsonUtils {

    private static final String ID = "id";
    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String DURATION = "runtime";

    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w500";

    public static Movie[] getSimpleMovieStringsFromJson(String json) throws JSONException {

        JSONObject moviesJson = new JSONObject(json);

        JSONArray moviesResultJsonArray = moviesJson.optJSONArray(RESULTS);

        Movie[] moviesDetailsArray = new Movie[moviesResultJsonArray.length()];

        for (int i = 0; i < moviesResultJsonArray.length(); i++) {
            String movieId = moviesResultJsonArray.getJSONObject(i).optString(ID);
            String moviePoster = moviesResultJsonArray.getJSONObject(i).optString(POSTER_PATH);

            moviesDetailsArray[i] = new Movie(movieId, POSTER_BASE_URL + POSTER_SIZE + moviePoster);
        }

        return moviesDetailsArray;

    }

    public static DetailMovie getDetailMovieStringsFromJson(String json) throws JSONException {

        JSONObject moviesJson = new JSONObject(json);

        String movieTitle = moviesJson.optString(TITLE);
        String moviePoster = moviesJson.optString(POSTER_PATH);
        String movieRelease = moviesJson.optString(RELEASE_DATE);
        String movieRate = moviesJson.optString(VOTE_AVERAGE);
        String movieOverview = moviesJson.optString(OVERVIEW);
        String movieDuration = moviesJson.optString(DURATION);

        return new DetailMovie(movieTitle,
                POSTER_BASE_URL + POSTER_SIZE + moviePoster,
                movieRelease,
                movieRate,
                movieOverview,
                movieDuration);
    }

}