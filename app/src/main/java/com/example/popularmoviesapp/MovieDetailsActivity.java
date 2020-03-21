package com.example.popularmoviesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Intent;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.popularmoviesapp.models.DetailMovie;
import com.example.popularmoviesapp.models.Movie;
import com.example.popularmoviesapp.utilities.AsyncTaskCompleteListener;
import com.example.popularmoviesapp.utilities.FetchAsyncTaskBase;
import com.example.popularmoviesapp.utilities.MovieDetailsJsonUtils;
import com.example.popularmoviesapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity implements AsyncTaskCompleteListener {

    @BindView(R.id.details_poster)
    ImageView mMoviePoster;
    @BindView(R.id.details_movie_title_tv)
    TextView mMovieTitle;
    @BindView(R.id.details_year_tv)
    TextView mMovieYear;
    @BindView(R.id.details_rating_tv)
    TextView mMovieRating;
    @BindView(R.id.details_description_tv)
    TextView mMovieDescription;
    @BindView(R.id.details_duration_tv)
    TextView mMovieDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Movie selectedMovie = intent.getParcelableExtra("Movie"); // Receive the Movie object as Parcelable

        FetchAsyncTaskBase getMovies = new FetchAsyncTaskBase(selectedMovie.getMovieId(), this);
        getMovies.execute();

        //populateUi(selectedMovie);
    }

    @SuppressLint("SetTextI18n")
    private void populateUi(DetailMovie movie){

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
            mMovieYear.setText(notAvailable);
        }

        if(movie.getMovieRate() != null && !(movie.getMovieRate().equals(""))){
            mMovieRating.setText(movie.getMovieRate() + "/10");
        }else{
            mMovieRating.setText(notAvailable);
        }

        if(movie.getMovieOverview() != null && !(movie.getMovieOverview().equals(""))){
            mMovieDescription.setText(movie.getMovieOverview());
        }else{
            mMovieDescription.setText(notAvailable);
        }

        if(movie.getMovieDuration() != null && !(movie.getMovieDuration().equals(""))){
            mMovieDuration.setText(movie.getMovieDuration() + " min");
        }else{
            mMovieDuration.setText(notAvailable);
        }

        Picasso.get()
                .load(movie.getMoviePoster())
                .placeholder(R.drawable.movie_poster_placeholder_image)
                .error(R.drawable.not_found_poster_image)
                .into(mMoviePoster);
    }

    @Override
    public void onTaskComplete(Object movie) {
        populateUi((DetailMovie) movie);
    }
}
