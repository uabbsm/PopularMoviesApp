package com.example.popularmoviesapp.utilities;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.models.Trailer;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private Trailer[] mTrailersArray;
    private TextView mName;
    private TextView mType;

    public TrailersAdapter(Trailer[] mTrailersArray) {
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

    public class TrailersViewHolder extends RecyclerView.ViewHolder{

        private TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.iv_trailer_name);
            mType = itemView.findViewById(R.id.iv_trailer_type);
        }

    }
}