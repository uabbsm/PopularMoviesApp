package com.example.popularmoviesapp.utilities;


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

    public final class NetworkUtils {

        // Example URL for popular sorting: http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
        // Example URL for specific movie: http://api.themoviedb.org/3/movie/550?api_key=[YOUR_API_KEY]
        private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
        private static final String API_KEY = "api_key";

        private static String apiKey = "de61ed620d17e836e7f108166872e22a";

        public static URL buildUrl(String searchQuery) {
            Uri builtUri = Uri.parse(MOVIE_BASE_URL)
                    .buildUpon()
                    .appendEncodedPath(searchQuery)
                    .appendQueryParameter(API_KEY, apiKey)
                    .build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return url;
        }

        public static String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }

    }