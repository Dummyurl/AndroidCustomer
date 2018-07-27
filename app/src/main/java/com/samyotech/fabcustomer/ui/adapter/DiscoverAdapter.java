package com.samyotech.fabcustomer.ui.adapter;

/**
 * Created by mayank on 31/10/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samyotech.fabcustomer.DTO.AllAtristListDTO;
import com.samyotech.fabcustomer.DTO.AllAtristListDTO;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.ui.activity.ArtistProfileView;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<AllAtristListDTO> allAtristListDTOList;
    private LayoutInflater inflater;

    public DiscoverAdapter(Context mContext, ArrayList<AllAtristListDTO> allAtristListDTOList, LayoutInflater inflater) {
        this.mContext = mContext;
        this.allAtristListDTOList = allAtristListDTOList;
        this.inflater = inflater;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater
                .inflate(R.layout.adapterdiscover, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.CTVartistwork.setText(allAtristListDTOList.get(position).getCategory_name());
        holder.CTVartistname.setText(allAtristListDTOList.get(position).getName());
        holder.CTVartistchargeprh.setText("$" + allAtristListDTOList.get(position).getPrice() + "/hr Add On : $"+ allAtristListDTOList.get(position).getCategory_price());
        holder.CTVlocation.setText(allAtristListDTOList.get(position).getLocation());
        holder.CTVdistance.setText(allAtristListDTOList.get(position).getDistance() + " KM From You");
        holder.CTVtime.setText(ProjectUtils.getDisplayableTime(ProjectUtils.correctTimestamp(allAtristListDTOList.get(position).getCreated_at())));

        holder.CTVjobdone.setText(allAtristListDTOList.get(position).getJobDone());
        holder.tvRating.setText("(" + allAtristListDTOList.get(position).getAva_rating() + "/5)");
        holder.CTVpersuccess.setText((int) ProjectUtils.roundTwoDecimals(allAtristListDTOList.get(position).getPercentages()) + "%");
        Glide.with(mContext).
                load(allAtristListDTOList.get(position).getImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.IVartist);
        holder.ratingbar.setRating(allAtristListDTOList.get(position).getAva_rating());
        holder.rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ArtistProfileView.class);
                in.putExtra(Consts.ARTIST_ID, allAtristListDTOList.get(position).getUser_id());
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {

        return allAtristListDTOList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CustomTextViewBold CTVartistname;
        public CustomTextView CTVartistwork, CTVjobdone, CTVpersuccess, CTVartistchargeprh, CTVlocation, CTVdistance, CTVtime, tvRating;
        public CircleImageView IVartist;
        public RatingBar ratingbar;
        public RelativeLayout rlClick;

        public MyViewHolder(View view) {
            super(view);

            rlClick = view.findViewById(R.id.rlClick);
            CTVartistname = view.findViewById(R.id.CTVartistname);
            CTVartistwork = view.findViewById(R.id.CTVartistwork);
            CTVjobdone = view.findViewById(R.id.CTVjobdone);
            CTVpersuccess = view.findViewById(R.id.CTVpersuccess);
            CTVartistchargeprh = view.findViewById(R.id.CTVartistchargeprh);
            CTVlocation = view.findViewById(R.id.CTVlocation);
            CTVdistance = view.findViewById(R.id.CTVdistance);
            CTVtime = view.findViewById(R.id.CTVtime);
            IVartist = view.findViewById(R.id.IVartist);
            tvRating = view.findViewById(R.id.tvRating);
            ratingbar = view.findViewById(R.id.ratingbar);

        }
    }

}