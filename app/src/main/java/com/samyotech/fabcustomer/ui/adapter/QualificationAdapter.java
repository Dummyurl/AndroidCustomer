package com.samyotech.fabcustomer.ui.adapter;

/**
 * Created by mayank on 31/10/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samyotech.fabcustomer.DTO.QualificationsDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.CustomTextView;

import java.util.ArrayList;


public class QualificationAdapter extends RecyclerView.Adapter<QualificationAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<QualificationsDTO> qualificationsDTOList;


    public QualificationAdapter(Context mContext, ArrayList<QualificationsDTO> qualificationsDTOList) {
        this.mContext = mContext;
        this.qualificationsDTOList = qualificationsDTOList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterqualification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.CTVtitel.setText(qualificationsDTOList.get(position).getTitle());
        holder.CTVdescription.setText(qualificationsDTOList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {

        return qualificationsDTOList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public CustomTextView CTVtitel, CTVdescription;

        public MyViewHolder(View view) {
            super(view);

            CTVtitel = view.findViewById(R.id.CTVtitel);
            CTVdescription = view.findViewById(R.id.CTVdescription);

        }
    }

}