package com.samyotech.fabcustomer.ui.adapter;

/**
 * Created by mayank on 31/10/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.samyotech.fabcustomer.DTO.HistoryDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.ui.activity.BaseActivity;
import com.samyotech.fabcustomer.ui.activity.HistoryActivity;
import com.samyotech.fabcustomer.ui.activity.PaymentActivity;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context mContext;
    private HistoryActivity historyActivity;
    private ArrayList<HistoryDTO> historyDTOList;
    private UserDTO userDTO;


    public HistoryAdapter(HistoryActivity historyActivity, ArrayList<HistoryDTO> historyDTOList, UserDTO userDTO) {
        this.historyActivity = historyActivity;
        this.mContext = historyActivity.getActivity();
        this.historyDTOList = historyDTOList;
        this.userDTO = userDTO;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterhistory, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.CTVBservice.setText("Service # " + historyDTOList.get(position).getInvoice_id());
        holder.CTVdate.setText(ProjectUtils.convertTimestampDateToTime(ProjectUtils.correctTimestamp(historyDTOList.get(position).getCreated_at())));
        holder.CTVprice.setText("$" + historyDTOList.get(position).getFinal_amount());
        holder.CTVServicetype.setText(historyDTOList.get(position).getCategoryName());
        holder.CTVwork.setText(historyDTOList.get(position).getCategoryName());
        holder.CTVname.setText(ProjectUtils.getFirstLetterCapital(historyDTOList.get(position).getArtistName()));

        Glide.with(mContext).
                load(historyDTOList.get(position).getArtistImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.IVprofile);

        if (historyDTOList.get(position).getFlag() == 0) {
            holder.llPay.setVisibility(View.VISIBLE);
            holder.tvStatus.setText("Unpaid");
            holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_red));
        } else if (historyDTOList.get(position).getFlag() == 1) {
            holder.llPay.setVisibility(View.GONE);
            holder.tvStatus.setText("Paid");
            holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_green));
        }
        holder.llPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, PaymentActivity.class);
                in.putExtra(Consts.HISTORY_DTO, historyDTOList.get(position));
                mContext.startActivity(in);

            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("mm.ss");

        try {
            Date dt = sdf.parse(historyDTOList.get(position).getWorking_min());
            sdf = new SimpleDateFormat("HH:mm:ss");

            holder.CTVTime.setText("DURATION: " + sdf.format(dt));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return historyDTOList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CustomTextViewBold CTVBservice, CTVBreportissue;
        public CustomTextView CTVprice, CTVdate, CTVServicetype, CTVwork, CTVname, tvStatus,CTVTime;
        public CircleImageView IVprofile;
        public LinearLayout llStatus, llPay;

        public MyViewHolder(View view) {
            super(view);
            llStatus = view.findViewById(R.id.llStatus);
            llPay = view.findViewById(R.id.llPay);
            tvStatus = view.findViewById(R.id.tvStatus);
            CTVBservice = view.findViewById(R.id.CTVBservice);
            CTVdate = view.findViewById(R.id.CTVdate);
            CTVBreportissue = view.findViewById(R.id.CTVBreportissue);
            CTVprice = view.findViewById(R.id.CTVprice);
            CTVTime = view.findViewById(R.id.CTVTime);
            CTVServicetype = view.findViewById(R.id.CTVServicetype);
            CTVwork = view.findViewById(R.id.CTVwork);
            CTVname = view.findViewById(R.id.CTVname);
            IVprofile = view.findViewById(R.id.IVprofile);


        }
    }




}