package com.example.popularmoviesapp.utilities;

import com.example.popularmoviesapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsJsonUtils {

    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String DURATION = "runtime";

    private static final String POSTER_BASE_ULT = "http://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w500";

    public static Movie[] getSimpleInfoStringsFromJson(String json) throws JSONException {

        JSONObject moviesJson = new JSONObject(json);

        JSONArray moviesResultJsonArray = moviesJson.getJSONArray(RESULTS);

        Movie[] moviesDetailsArray = new Movie[moviesResultJsonArray.length()];

        for (int i = 0; i < moviesResultJsonArray.length(); i++) {

            String movieTitle = moviesResultJsonArray.getJSONObject(i).optString(TITLE);
            String moviePoster = moviesResultJsonArray.getJSONObject(i).optString(POSTER_PATH);
            String movieRelease = moviesResultJsonArray.getJSONObject(i).optString(RELEASE_DATE);
            String movieRate = moviesResultJsonArray.getJSONObject(i).optString(VOTE_AVERAGE);
            String movieOverview = moviesResultJsonArray.getJSONObject(i).optString(OVERVIEW);
            String movieDuration = moviesResultJsonArray.getJSONObject(i).optString(DURATION);

            moviesDetailsArray[i] = new Movie(movieTitle,
                    POSTER_BASE_ULT + POSTER_SIZE + moviePoster,
                    movieRelease,
                    movieRate,
                    movieOverview,
                    movieDuration);
        }
        return moviesDetailsArray;
    }
}
