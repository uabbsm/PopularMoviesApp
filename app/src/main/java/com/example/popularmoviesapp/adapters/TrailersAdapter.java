package com.example.popularmoviesapp.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.models.Trailer;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    final private TrailersAdapterListItemClickListener mOnClickListener;
    private Trailer[] mTrailersArray;
    private TextView mName;
    private TextView mType;

    public TrailersAdapter(TrailersAdapterListItemClickListener mOnClickListener, Trailer[] mTrailersArray) {
        this.mOnClickListener = mOnClickListener;
        this.mTrailersArray = mTrailersArray;
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_list_item_for_recyclerview, viewGroup, false);

        return new TrailersAdapter.TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder trailersViewHolder, int position) {
        mName.setText(mTrailersArray[position].getName());
        mType.setText(mTrailersArray[position].getType());
    }

    @Override
    public int getItemCount() {
        if (null == mTrailersArray) return 0;
        return mTrailersArray.length;
    }

    public interface TrailersAdapterListItemClickListener{
        void onListItemClick(int item);
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.iv_trailer_name);
            mType = itemView.findViewById(R.id.iv_trailer_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(adapterPosition);
        }
    }
}