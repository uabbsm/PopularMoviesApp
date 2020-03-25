package com.example.popularmoviesapp.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.models.Movie;


import com.squareup.picasso.Picasso;

/**
 * Exposes the list of movies
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    final private MoviesAdapterListItemClickListener mOnClickListener;
    private Movie[] mMoviesArray;

    public MoviesAdapter(Movie[] numberOfMovies, MoviesAdapterListItemClickListener listener) {
        mMoviesArray = numberOfMovies;
        mOnClickListener = listener;
    }

    /**
     * inflate movie list item layout
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_list_item, viewGroup, false);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder moviesViewHolder, int position) {
        moviesViewHolder.bind(position);
    }

    /**
     * Counts the items from the array
     * @return 0 or the length of the array
     */
    @Override
    public int getItemCount() {
        if (null == mMoviesArray) return 0;
        return mMoviesArray.length;
    }

    public interface MoviesAdapterListItemClickListener {
        void onListItemClick(int item);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView mMoviePoster;

        private MoviesViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = itemView.findViewById(R.id.iv_movie);
            itemView.setOnClickListener(this);
        }

        /**
         * There was an issue with http images coming from the server.
         * The Picasso as well as Glide (tried both)
         * could not load 'http', only 'https' urls.
         * Therefore I implement this solution found on SO:
         * https://stackoverflow.com/questions/53288020/picasso-doesnt-load-images-from-http-links-in-api-28
         * Including "android:usesCleartextTraffic="true"" into the Manifest solve the issue.
         * @param pos
         */
        void bind(int pos) {
            String imagePath = mMoviesArray[pos].getMoviePoster();
            Picasso.get()
                    .load(imagePath)
                    .placeholder(R.drawable.movie_poster_placeholder_image)
                    .error(R.drawable.not_found_poster_image)
                    .into(mMoviePoster);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(adapterPosition);
        }
    }

}