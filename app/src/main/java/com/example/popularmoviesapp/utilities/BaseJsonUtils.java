package com.example.popularmoviesapp.utilities;

import com.example.popularmoviesapp.models.DetailMovie;
import com.example.popularmoviesapp.models.Movie;
import com.example.popularmoviesapp.models.Review;
import com.example.popularmoviesapp.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class BaseJsonUtils {

    // Strings for movie details request
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

    // Strings for trailers request
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String KEY = "key";

    // Strings for review request
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    public static Movie[] getSimpleMovieStringsFromJson(String json) throws JSONException {

        JSONObject moviesJson = new JSONObject(json);

        JSONArray moviesResultJsonArray = moviesJson.optJSONArray(RESULTS);

        Movie[] moviesDetailsArray = new Movie[moviesResultJsonArray.length()];

        for(int i = 0; i < moviesResultJsonArray.length(); i++){
            String movieId = moviesResultJsonArray.getJSONObject(i).optString(ID);
            String moviePoster = moviesResultJsonArray.getJSONObject(i).optString(POSTER_PATH);

            moviesDetailsArray[i] = new Movie(movieId, POSTER_BASE_URL+POSTER_SIZE+moviePoster);
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
                POSTER_BASE_URL+POSTER_SIZE+moviePoster,
                movieRelease,
                movieRate,
                movieOverview,
                movieDuration);
    }

    public static Review[] getReviewsStringsFromJson(String json) throws JSONException {

        JSONObject reviewsJson = new JSONObject(json);
        JSONArray reviewsJsonArray = reviewsJson.optJSONArray(RESULTS);

        Review[] reviewsArray = new Review[reviewsJsonArray.length()];

        for(int i = 0; i < reviewsJsonArray.length(); i++){

            String author = reviewsJsonArray.getJSONObject(i).optString(AUTHOR);
            String content = reviewsJsonArray.getJSONObject(i).optString(CONTENT);

            reviewsArray[i] = new Review(author, content);
        }

        return reviewsArray;

    }

    public static Trailer[] getTrailersStringsFromJson(String json) throws JSONException {

        JSONObject trailersJson = new JSONObject(json);
        JSONArray trailersJsonArray = trailersJson.optJSONArray(RESULTS);

        Trailer[] trailersArray = new Trailer[trailersJsonArray.length()];

        for(int i = 0; i < trailersJsonArray.length(); i++){

            String name = trailersJsonArray.getJSONObject(i).optString(NAME);
            String type = trailersJsonArray.getJSONObject(i).optString(TYPE);
            String key = trailersJsonArray.getJSONObject(i).optString(KEY);

            trailersArray[i] = new Trailer(name, type, key);
        }

        return trailersArray;

    }


}