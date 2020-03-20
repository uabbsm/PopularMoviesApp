package com.example.popularmoviesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Parcelable;
import android.content.Intent;
import android.widget.Toast;

import com.example.popularmoviesapp.models.Movie;
import com.squareup.picasso.Picasso;

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
        mMoviePoster = (ImageView) findViewById(R.id.details_poster);
        mMovieRating = (TextView) findViewById(R.id.details_rating_tv);
        mMovieYear = (TextView) findViewById(R.id.details_year_tv);
        mMovieTitle = (TextView) findViewById(R.id.movie_title_tv);

        Intent intent = getIntent();
        Movie selectedMovie = intent.getParcelableExtra("Movie");

        populateUi(selectedMovie);
    }

    private void populateUi(Movie movie){

        String notAvailable = "N/A";

        if(movie.getMovieTitle() != null && !(movie.getMovieTitle().equals(""))){
            mMovieTitle.setText(movie.getMovieTitle());
        }else{
            mMovieTitle.setText(notAvailable);
        }

        if(movie.getMovieRelease() != null && !(movie.getMovieRelease().equals(""))){
            // substring to get the 4 first characters of the string. The year
            mMovieYear.setText(movie.getMovieRelease().substring(0, 4));
        }else{
            mMovieTitle.setText(notAvailable);
        }

        if(movie.getMovieRate() != null && !(movie.getMovieRate().equals(""))){
            mMovieRating.setText(movie.getMovieRate());
        }else{
            mMovieTitle.setText(notAvailable);
        }

        if(movie.getMovieOverview() != null && !(movie.getMovieOverview().equals(""))){
            mMovieDescription.setText(movie.getMovieOverview());
        }else{
            mMovieTitle.setText(notAvailable);
        }

        Picasso.get()
                .load(movie.getMoviePoster())
                .placeholder(R.drawable.movie_poster_placeholder_image)
                .error(R.drawable.not_found_poster_image)
                .into(mMoviePoster);
    }
}
