package com.example.popularmoviesapp;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.widget.Toast;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.example.popularmoviesapp.adapters.ReviewsAdapter;
import com.example.popularmoviesapp.databinding.ActivityDetailsBinding;
import com.example.popularmoviesapp.models.DetailMovie;
import com.example.popularmoviesapp.models.Movie;
import com.example.popularmoviesapp.models.Review;
import com.example.popularmoviesapp.models.Trailer;
import com.example.popularmoviesapp.utilities.AsyncTaskCompleteListener;
import com.example.popularmoviesapp.utilities.FetchAsyncTaskBase;
import com.example.popularmoviesapp.utilities.NetworkUtils;
import com.example.popularmoviesapp.utilities.BaseJsonUtils;
import com.example.popularmoviesapp.adapters.TrailersAdapter;
import com.squareup.picasso.Picasso;


public class MovieDetailsActivity extends AppCompatActivity implements AsyncTaskCompleteListener, TrailersAdapter.TrailersAdapterListItemClickListener {

    public static final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    private ReviewsAdapter mReviewsAdapter;
    private  TrailersAdapter mTrailersAdapter;

    private Trailer[] mTrailerArray;

    private ActivityDetailsBinding mDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        Movie selectedMovie = intent.getParcelableExtra("Movie"); // Receive the Movie object as Parcelable

        FetchAsyncTaskBase getMovies = new FetchAsyncTaskBase(selectedMovie.getMovieId(), this);
        getMovies.execute();

        mDetailsBinding.progressBarDetails.setVisibility(View.VISIBLE);
        mDetailsBinding.detailsLayout.setVisibility(View.INVISIBLE);
        getSupportActionBar().hide();

        loadReviewData(selectedMovie.getMovieId() + "/reviews");
        loadTrailerData(selectedMovie.getMovieId() + "/videos");
    }

    private void loadReviewData(String query){

        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
        mDetailsBinding.reviewsRecyclerviewLayout.detailsReviewsRecyclerView.setLayoutManager(reviewsLayoutManager);
        mDetailsBinding.reviewsRecyclerviewLayout.detailsReviewsRecyclerView.setHasFixedSize(true);
        mDetailsBinding.reviewsRecyclerviewLayout.detailsReviewsRecyclerView.setAdapter(mReviewsAdapter);

        new FetchReviewTask().execute(query);
    }

    private void loadTrailerData(String query){

        LinearLayoutManager trailersLayoutManager = new LinearLayoutManager(this);
        mDetailsBinding.trailersRecyclerviewLayout.detailsTrailersRecyclerView.setLayoutManager(trailersLayoutManager);
        mDetailsBinding.trailersRecyclerviewLayout.detailsTrailersRecyclerView.setHasFixedSize(true);
        mDetailsBinding.trailersRecyclerviewLayout.detailsTrailersRecyclerView.setAdapter(mTrailersAdapter);

        new FetchTrailerTask().execute(query);
    }

    @SuppressLint("SetTextI18n")
    private void populateUi(DetailMovie movie){

        String notAvailable = "N/A";

        if(movie.getMovieTitle() != null && !(movie.getMovieTitle().equals(""))){
            mDetailsBinding.detailsMovieTitleTv.setText(movie.getMovieTitle());
        }else{
            mDetailsBinding.detailsMovieTitleTv.setText(notAvailable);
        }

        if(movie.getMovieRelease() != null && !(movie.getMovieRelease().equals(""))){
            // substring to get the 4 first characters of the string. The year
            mDetailsBinding.movieDetailsLayout.detailsYearTv.setText(movie.getMovieRelease().substring(0, 4));
        }else{
            mDetailsBinding.movieDetailsLayout.detailsYearTv.setText(notAvailable);
        }

        if(movie.getMovieRate() != null && !(movie.getMovieRate().equals(""))){
            mDetailsBinding.movieDetailsLayout.detailsRatingTv.setText(movie.getMovieRate() + "/10");
        }else{
            mDetailsBinding.movieDetailsLayout.detailsRatingTv.setText(notAvailable);
        }

        if(movie.getMovieOverview() != null && !(movie.getMovieOverview().equals(""))){
            mDetailsBinding.movieDetailsLayout.detailsDescriptionTv.setText(movie.getMovieOverview());
        }else{
            mDetailsBinding.movieDetailsLayout.detailsDescriptionTv.setText(notAvailable);
        }

        if(movie.getMovieDuration() != null && !(movie.getMovieDuration().equals(""))){
            mDetailsBinding.movieDetailsLayout.detailsDurationTv.setText(movie.getMovieDuration() + " min");
        }else{
            mDetailsBinding.movieDetailsLayout.detailsDurationTv.setText(notAvailable);
        }

        Picasso.get()
                .load(movie.getMoviePoster())
                .placeholder(R.drawable.movie_poster_placeholder_image)
                .error(R.drawable.not_found_poster_image)
                .into(mDetailsBinding.movieDetailsLayout.detailsPoster);
    }

    @Override
    public void onTaskComplete(Object movie) {
        mDetailsBinding.progressBarDetails.setVisibility(View.INVISIBLE);
        mDetailsBinding.detailsLayout.setVisibility(View.VISIBLE);
        populateUi((DetailMovie) movie);
    }

    @Override
    public void onListItemClick(int item) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + mTrailerArray[item].getKey()));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There was an error while opening the link", Toast.LENGTH_SHORT).show();
        }
    }

    public class FetchReviewTask extends AsyncTask<String, Void, Review[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Review[] doInBackground(String... params) {

            if (params.length == 0){ return null; }
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(params[0]));

                return BaseJsonUtils.getReviewsStringsFromJson(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Review[] reviewData) {
            if (reviewData != null) {
                mReviewsAdapter = new ReviewsAdapter(reviewData);
                if(mReviewsAdapter.getItemCount() == 0){
                    mDetailsBinding.reviewsRecyclerviewLayout.noReviewsTv.setText(getApplicationContext().getString(R.string.no_reviews));
                    mDetailsBinding.reviewsRecyclerviewLayout.noReviewsTv.setVisibility(View.VISIBLE);
                }else{
                    mDetailsBinding.reviewsRecyclerviewLayout.detailsReviewsRecyclerView.setAdapter(mReviewsAdapter);
                    mDetailsBinding.reviewsRecyclerviewLayout.noReviewsTv.setVisibility(View.GONE);
                }
            }
        }

    }

    public class FetchTrailerTask extends AsyncTask<String, Void, Trailer[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Trailer[] doInBackground(String... params) {

            if (params.length == 0){ return null; }
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl(params[0]));

                return BaseJsonUtils.getTrailersStringsFromJson(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Trailer[] trailerData) {
            if (trailerData != null) {
                mTrailersAdapter = new TrailersAdapter(MovieDetailsActivity.this, trailerData);
                if(mTrailersAdapter.getItemCount() == 0){
                    mDetailsBinding.trailersRecyclerviewLayout.noTrailersTv.setText(getApplicationContext().getString(R.string.no_trailers));
                    mDetailsBinding.trailersRecyclerviewLayout.noTrailersTv.setVisibility(View.VISIBLE);
                }else{
                    mTrailerArray = trailerData;
                    mDetailsBinding.trailersRecyclerviewLayout.detailsTrailersRecyclerView.setAdapter(mTrailersAdapter);
                    mDetailsBinding.trailersRecyclerviewLayout.noTrailersTv.setVisibility(View.GONE);
                }
            }
        }

    }

}