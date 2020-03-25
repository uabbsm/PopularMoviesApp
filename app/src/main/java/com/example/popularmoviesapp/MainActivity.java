package com.example.popularmoviesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.util.DisplayMetrics;
import android.os.Parcelable;

import com.example.popularmoviesapp.database.AppDatabase;
import com.example.popularmoviesapp.database.FavoriteMovie;
import com.example.popularmoviesapp.adapters.MoviesAdapter;
import com.example.popularmoviesapp.databinding.ActivityMainBinding;
import com.example.popularmoviesapp.models.Movie;
import com.example.popularmoviesapp.utilities.AsyncTaskCompleteListener;
import com.example.popularmoviesapp.utilities.FetchAsyncTaskBase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements MoviesAdapter.MoviesAdapterListItemClickListener, AsyncTaskCompleteListener {

    public static final String POPULAR_QUERY = "popular";

    public static final String TOP_RATED_QUERY = "top_rated";

    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    private final String KEY_RECYCLER_STATE = "recycler_state";

    private String LIFECYCLE_CALLBACKS_BOOL_FAVORITES_FLAG = "favorite_selected";

    private AppDatabase mDb;

    String query = "popular";

    private static Bundle mBundleRecyclerViewState;

    private Movie[] mMovies;

    private MoviesAdapter mMoviesAdapter;

    ActivityMainBinding mMainBinding;

    private boolean favorites_flag = false; // a flag to track whether sorting as favorites has been selected or not

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
            favorites_flag = savedInstanceState.getBoolean(LIFECYCLE_CALLBACKS_BOOL_FAVORITES_FLAG);
        }
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager LayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));

        mMainBinding.recyclerViewMovies.setLayoutManager(LayoutManager);
        mMainBinding.recyclerViewMovies.setHasFixedSize(true);

        mMainBinding.recyclerViewMovies.setAdapter(mMoviesAdapter);

        if(!favorites_flag){
            loadMovieData(query);
        }
    }

    /**
     *
     * @param item
     */
    @Override
    public void onListItemClick(int item) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Movie", mMovies[item]); // Send the movie Object as Parcelable
        startActivity(intent);
    }

    /**
     * inflates the main screen options
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_options, menu);
        return true;
    }

    /**
     * changes the query based on the option selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_most_popular:
                query = POPULAR_QUERY;
                favorites_flag = false;
                loadMovieData(POPULAR_QUERY);
                break;
            case R.id.sort_highest_rated:
                query = TOP_RATED_QUERY;
                favorites_flag = false;
                loadMovieData(TOP_RATED_QUERY);
                break;
            default:
                loadFavoritePostersToGrid();
                favorites_flag = true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * loads the posters to the grid
     * @param movies
     */
    @Override
    public void onTaskComplete(Object movies) {
        loadPostersToGrid((Movie[]) movies);
    }

    /**
     * Lifecycle methods
     * @param savedInstanceState
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        query = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
        favorites_flag = savedInstanceState.getBoolean(LIFECYCLE_CALLBACKS_BOOL_FAVORITES_FLAG);
    }

    /**
     * Lifecycle methods
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        String lifecycleSortBy = query;
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, lifecycleSortBy);
        outState.putBoolean(LIFECYCLE_CALLBACKS_BOOL_FAVORITES_FLAG, favorites_flag);
        super.onSaveInstanceState(outState);
    }

    /**
     * Lifecycle methods
     */
    @Override
    protected void onPause() {
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mMainBinding.recyclerViewMovies.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        mBundleRecyclerViewState.putBoolean(LIFECYCLE_CALLBACKS_BOOL_FAVORITES_FLAG, favorites_flag);
        super.onPause();
    }

    /**
     * Lifecycle methods
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            if (mBundleRecyclerViewState.getBoolean(LIFECYCLE_CALLBACKS_BOOL_FAVORITES_FLAG)){
                loadFavoritePostersToGrid();
            }else{
                Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                mMainBinding.recyclerViewMovies.getLayoutManager().onRestoreInstanceState(listState);
            }

        }
    }


    // Helper methods

    /**
     * Load move data
     * @param query
     */
    private void loadMovieData(String query){
        mMainBinding.progressBar.setVisibility(View.VISIBLE);
        showMoviesList();

        FetchAsyncTaskBase getMovies = new FetchAsyncTaskBase(query, this);
        getMovies.execute();
    }

    /**
     * loads the posters of the movies to the grid
     */
    private void loadFavoritePostersToGrid() {
        mDb = AppDatabase.getInstance(getApplicationContext());
        final LiveData<FavoriteMovie[]> favoriteMovieLiveData = mDb.taskDao().loadAllFavoriteMovies();
        favoriteMovieLiveData.observe(this, new Observer<FavoriteMovie[]>() {
            @Override
            public void onChanged(@Nullable FavoriteMovie[] favoriteMovie) {
                List<Movie> moviesList = new ArrayList<>();
                for (FavoriteMovie favMovie : favoriteMovie)
                    moviesList.add(new Movie(String.valueOf(favMovie.getId()), favMovie.getMoviePoster()));
                loadPostersToGrid(moviesList.toArray(new Movie[0]));
            }
        });
    }

    /**
     * shows the movie list
     */
    private void showMoviesList() {
        mMainBinding.tvErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMainBinding.recyclerViewMovies.setVisibility(View.VISIBLE);
    }

    /**
     * shows an error message
     */
    private void showErrorMessage() {
        mMainBinding.recyclerViewMovies.setVisibility(View.INVISIBLE);
        mMainBinding.tvErrorMessageDisplay.setText(getString(R.string.error_message));
        mMainBinding.tvErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * shows "no favorites" message
     */
    private void showNoFavorites() {
        mMainBinding.tvErrorMessageDisplay.setText(getString(R.string.no_favorites_yet));
        mMainBinding.recyclerViewMovies.setVisibility(View.INVISIBLE);
        mMainBinding.tvErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns
     * The best way to calculate the number of the columns that will be displayed
     * @param context
     * @return
     */
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    /**
     * load the posters to grid
     * @param movies
     */
    private void loadPostersToGrid(Movie[] movies) {
        mMainBinding.progressBar.setVisibility(View.INVISIBLE);
        if (movies != null) {
            if (movies.length == 0) {
                showNoFavorites();
            }else{
                showMoviesList();
                mMoviesAdapter = new MoviesAdapter(movies, MainActivity.this);
                mMainBinding.recyclerViewMovies.setAdapter(mMoviesAdapter);
                mMovies = movies;
            }

        } else {
            showErrorMessage();
        }
    }
}