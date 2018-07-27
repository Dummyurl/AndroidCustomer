package com.samyotech.fabcustomer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.samyotech.fabcustomer.DTO.TicketCommentDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import java.util.ArrayList;

/**
 * Created by varunverma on 5/7/17.
 */

public class AdapterViewCommentTicket extends BaseAdapter {
    private Context mContext;
    private ArrayList<TicketCommentDTO> ticketCommentDTOSList;
    private UserDTO userDTO;

    public AdapterViewCommentTicket(Context mContext, ArrayList<TicketCommentDTO> ticketCommentDTOSList, UserDTO userDTO) {
        this.mContext = mContext;
        this.ticketCommentDTOSList = ticketCommentDTOSList;
        this.userDTO = userDTO;

    }

    @Override
    public int getCount() {
        return ticketCommentDTOSList.size();
    }

    @Override
    public Object getItem(int postion) {
        return ticketCommentDTOSList.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //ViewHolder holder = new ViewHolder();
        if (ticketCommentDTOSList.get(position).getRole() != userDTO.getRole()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_view_comment, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_view_comment_my, parent, false);

        }

        CustomTextView textViewMessage = (CustomTextView) view.findViewById(R.id.textViewMessage);
        CustomTextView textViewTime = (CustomTextView) view.findViewById(R.id.textViewTime);
        CustomTextView tvName = (CustomTextView) view.findViewById(R.id.tvName);

        textViewMessage.setText(ticketCommentDTOSList.get(position).getComment());
        tvName.setText(ticketCommentDTOSList.get(position).getUserName());
        textViewTime.setText(ProjectUtils.convertTimestampDateToTime(ProjectUtils.correctTimestamp(ticketCommentDTOSList.get(position).getCreated_at())));
        return view;
    }

}
