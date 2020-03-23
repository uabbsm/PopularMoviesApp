package com.example.popularmoviesapp.utilities;

import com.example.popularmoviesapp.models.Review;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ReviewsJsonUtils {

    private static final String RESULTS = "results";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

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

}
