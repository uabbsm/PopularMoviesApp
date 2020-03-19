package com.example.popularmoviesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView mMoviePoster;
    private TextView mMovieTitle;
    private TextView mMovieYear;
    private TextView mMovieRating;
    private TextView mMovieDuration;
    private TextView mMovieDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovieDescription = (TextView) findViewById(R.id.details_description_tv);
        mMovieDuration = (TextView) findViewById(R.id.details_duration_tv);
        mMoviePoster = (ImageView) findViewById(R.id.details_poster_iv);
        mMovieRating = (TextView) findViewById(R.id.details_rating_tv);
        mMovieYear = (TextView) findViewById(R.id.details_year_tv);
        mMovieTitle = (TextView) findViewById(R.id.movie_title_tv);
    }
}
