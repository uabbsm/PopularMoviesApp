package com.example.popularmoviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.util.DisplayMetrics;
import android.os.Parcelable;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.popularmoviesapp.adapters.MoviesAdapter;
import com.example.popularmoviesapp.models.Movie;
import com.example.popularmoviesapp.utilities.AsyncTaskCompleteListener;
import com.example.popularmoviesapp.utilities.FetchAsyncTaskBase;

public class MainActivity extends AppCompatActivity
        implements MoviesAdapter.MoviesAdapterListItemClickListener, AsyncTaskCompleteListener {

    public static final String POPULAR_QUERY = "popular";
    public static final String TOP_RATED_QUERY = "top_rated";

    private static Bundle mBundleRecyclerViewState;
    private Movie[] mMovies;

    String query = "popular";
    private final String KEY_RECYCLER_STATE = "recycler";
    public static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    @BindView(R.id.recycle_view_movies)
    RecyclerView mRecyclerView;
    private  MoviesAdapter mMoviesAdapter;

    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;

    @BindView(R.id.progressbar)
    ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            query = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
        }
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager LayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));

        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMoviesAdapter);

        loadMovieData(query);
    }

    private void loadMovieData(String query){
        mLoadingIndicator.setVisibility(View.VISIBLE);
        showMoviesList();

        FetchAsyncTaskBase getMovies = new FetchAsyncTaskBase(query, this);
        getMovies.execute();
    }

    @Override
    public void onListItemClick(int item) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Movie", mMovies[item]); // Send the movie Object as Parcelable
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.sort_most_popular){
            query = POPULAR_QUERY;
            loadMovieData(POPULAR_QUERY);
        }else{
            query = TOP_RATED_QUERY;
            loadMovieData(TOP_RATED_QUERY);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMoviesList() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    // Following this thread: https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns
    // The best way to calculate the number of the columns that will be displayed
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    @Override
    public void onTaskComplete(Object movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movies != null) {
            showMoviesList();
            mMoviesAdapter = new MoviesAdapter((Movie[]) movies, MainActivity.this);
            mRecyclerView.setAdapter(mMoviesAdapter);
            mMovies = (Movie[]) movies;
        } else {
            showErrorMessage();
        }
    }

    //LifeCycle Mewthod's

    /**
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        query = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        String lifecycleSortBy = query;
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, lifecycleSortBy);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mBundleRecyclerViewState != null){
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}
