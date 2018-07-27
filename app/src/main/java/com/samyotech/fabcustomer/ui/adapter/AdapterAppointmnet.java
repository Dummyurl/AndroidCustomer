package com.samyotech.fabcustomer.ui.adapter;

/**
 * Created by mayank on 31/10/17.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.samyotech.fabcustomer.DTO.AppointmentDTO;
import com.samyotech.fabcustomer.DTO.AppointmentDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.ui.activity.MyBooking;
import com.samyotech.fabcustomer.ui.fragment.AppointmentFrag;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterAppointmnet extends RecyclerView.Adapter<AdapterAppointmnet.MyViewHolder> {
    private String TAG = AdapterAppointmnet.class.getSimpleName();
    private AppointmentFrag appointmentFrag;
    private Context mContext;
    private ArrayList<AppointmentDTO> appointmentDTOSList;
    private UserDTO userDTO;
    SimpleDateFormat sdf1, timeZone;
    private HashMap<String, String> paramBookAppointment = new HashMap<>();
    private HashMap<String, String> paramDeclineAppointment = new HashMap<>();
    private DialogInterface dialog_book;

    public AdapterAppointmnet(AppointmentFrag appointmentFrag, ArrayList<AppointmentDTO> appointmentDTOSList, UserDTO userDTO) {
        this.appointmentFrag = appointmentFrag;
        mContext = appointmentFrag.getActivity();
        this.appointmentDTOSList = appointmentDTOSList;
        this.userDTO = userDTO;

        sdf1 = new SimpleDateFormat(Consts.DATE_FORMATE_SERVER, Locale.ENGLISH);
        timeZone = new SimpleDateFormat(Consts.DATE_FORMATE_TIMEZONE, Locale.ENGLISH);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_appointment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Glide.with(mContext).
                load(appointmentDTOSList.get(position).getArtistImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivArtist);

        holder.tvWork.setText(appointmentDTOSList.get(position).getCategory_name());
        holder.tvLocation.setText(appointmentDTOSList.get(position).getArtistAddress());
        holder.tvName.setText(appointmentDTOSList.get(position).getArtistName());
        holder.tvDate.setText(appointmentDTOSList.get(position).getDate_string());


        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramBookAppointment.put(Consts.USER_ID, userDTO.getUser_id());
                paramBookAppointment.put(Consts.ARTIST_ID, appointmentDTOSList.get(position).getArtist_id());
                paramBookAppointment.put(Consts.APPOINTMENT_ID, appointmentDTOSList.get(position).getId());
                clickScheduleDateTime();
            }
        });
        holder.tvDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramDeclineAppointment.put(Consts.USER_ID, userDTO.getUser_id());
                paramDeclineAppointment.put(Consts.APPOINTMENT_ID, appointmentDTOSList.get(position).getId());
                bookDailog();
            }
        });
    }


    @Override
    public int getItemCount() {

        return appointmentDTOSList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivArtist;
        public CustomTextView tvWork, tvLocation, tvDate;
        public CustomTextViewBold tvName;
        public CustomTextView tvDecline, tvEdit;

        public MyViewHolder(View view) {
            super(view);
            ivArtist = view.findViewById(R.id.ivArtist);
            tvWork = view.findViewById(R.id.tvWork);
            tvName = view.findViewById(R.id.tvName);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvDate = view.findViewById(R.id.tvDate);
            tvDecline = view.findViewById(R.id.tvDecline);
            tvEdit = view.findViewById(R.id.tvEdit);


        }
    }

    public void clickScheduleDateTime() {
        new SingleDateAndTimePickerDialog.Builder(mContext)
                .bottomSheet()
                .curved()
                .mustBeOnFuture()
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        paramBookAppointment.put(Consts.DATE_STRING, String.valueOf(sdf1.format(date)));
                        paramBookAppointment.put(Consts.TIMEZONE, String.valueOf(timeZone.format(date)));
                        bookAppointment();
                    }
                })
                .display();
    }

    public void bookAppointment() {
        new HttpsRequest(Consts.EDIT_APPOINTMENT_API, paramBookAppointment, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    appointmentFrag.getHistroy();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }
    public void declineAppointment() {

        new HttpsRequest(Consts.DECLINE_APPOINTMENT_API, paramDeclineAppointment, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    dialog_book.dismiss();
                    appointmentFrag.getHistroy();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }

    public void bookDailog() {
        try {
            new AlertDialog.Builder(mContext)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Decline")
                    .setMessage("Are you sure you want to decline this appointment?")
                    .setCancelable(false)
                    .setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog_book = dialog;
                            declineAppointment();

                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    })
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}