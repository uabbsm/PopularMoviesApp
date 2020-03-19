package com.example.popularmoviesapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmoviesapp.models.Movie;


import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    final private MoviesAdapterListItemClickListener mOnClickListener;
    private Movie[] mNumberItems;

    public MoviesAdapter(Movie[] numberOfMovies, MoviesAdapterListItemClickListener listener) {
        mNumberItems = numberOfMovies;
        mOnClickListener = listener;
    }

    @NonNull
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

    @Override
    public int getItemCount() {
        if (null == mNumberItems) return 0;
        return mNumberItems.length;
    }

    public interface MoviesAdapterListItemClickListener {
        void onListItemClick(int item);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMoviePoster;

        private MoviesViewHolder(View itemView) {
            super(itemView);

            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie);
            itemView.setOnClickListener(this);
        }

        void bind(int pos) {

            String imagePath = mNumberItems[pos].getMoviePoster();
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

    public void setMoviesData() {
        notifyDataSetChanged();
    }
}