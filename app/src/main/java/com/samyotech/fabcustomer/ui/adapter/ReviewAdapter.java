package com.samyotech.fabcustomer.ui.adapter;

/**
 * Created by mayank on 31/10/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samyotech.fabcustomer.DTO.QualificationsDTO;
import com.samyotech.fabcustomer.DTO.ReviewsDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<ReviewsDTO> reviewsDTOList;


    public ReviewAdapter(Context mContext, ArrayList<ReviewsDTO> reviewsDTOList) {
        this.mContext = mContext;
        this.reviewsDTOList = reviewsDTOList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_review, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvName.setText(reviewsDTOList.get(position).getName());
        holder.tvRating.setText("(" + reviewsDTOList.get(position).getRating() + "/5)");
        holder.tvComment.setText(reviewsDTOList.get(position).getComment());
        holder.tvTime.setText(ProjectUtils.getDisplayableTime(ProjectUtils.correctTimestamp(reviewsDTOList.get(position).getCreated_at())));
        holder.ratingbar.setRating(reviewsDTOList.get(position).getRating());
        Glide.with(mContext).
                load(reviewsDTOList.get(position).getImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivArtist);

    }

    @Override
    public int getItemCount() {

        return reviewsDTOList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView ivArtist;
        public CustomTextViewBold tvName;
        public CustomTextView tvRating, tvComment, tvTime;
        public RatingBar ratingbar;

        public MyViewHolder(View view) {
            super(view);

            ivArtist = view.findViewById(R.id.ivArtist);
            tvName = view.findViewById(R.id.tvName);
            tvRating = view.findViewById(R.id.tvRating);
            tvComment = view.findViewById(R.id.tvComment);
            tvTime = view.findViewById(R.id.tvTime);
            ratingbar = view.findViewById(R.id.ratingbar);

        }
    }

}