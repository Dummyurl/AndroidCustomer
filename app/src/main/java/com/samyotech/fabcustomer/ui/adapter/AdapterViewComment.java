package com.samyotech.fabcustomer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.samyotech.fabcustomer.DTO.GetCommentDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import java.util.ArrayList;

/**
 * Created by varunverma on 5/7/17.
 */

public class
AdapterViewComment extends BaseAdapter {
    private Context mContext;
    private ArrayList<GetCommentDTO> getCommentDTOList;
    private UserDTO userDTO;

    public AdapterViewComment(Context mContext, ArrayList<GetCommentDTO> getCommentDTOList,UserDTO userDTO) {
        this.mContext = mContext;
        this.getCommentDTOList = getCommentDTOList;
        this.userDTO = userDTO;

    }

    @Override
    public int getCount() {
        return getCommentDTOList.size();
    }

    @Override
    public Object getItem(int postion) {
        return getCommentDTOList.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //ViewHolder holder = new ViewHolder();
        if (!getCommentDTOList.get(position).getSend_by().equalsIgnoreCase(userDTO.getUser_id())) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_view_comment, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_view_comment_my, parent, false);

        }

        CustomTextView textViewMessage = (CustomTextView) view.findViewById(R.id.textViewMessage);
        CustomTextView textViewTime = (CustomTextView) view.findViewById(R.id.textViewTime);
        CustomTextView tvName = (CustomTextView) view.findViewById(R.id.tvName);

        textViewMessage.setText(getCommentDTOList.get(position).getMessage());
        tvName.setText(getCommentDTOList.get(position).getSender_name());
        textViewTime.setText(ProjectUtils.convertTimestampDateToTime(ProjectUtils.correctTimestamp(getCommentDTOList.get(position).getDate())));
        return view;
    }

}
