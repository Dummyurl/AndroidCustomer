package com.samyotech.fabcustomer.ui.adapter;

/**
 * Created by pushpraj on 20/2/18.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samyotech.fabcustomer.DTO.ArtistBookingDTO;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class PreviousworkPagerAdapter extends PagerAdapter {
    Context context;

    LayoutInflater layoutInflater;
    private ArrayList<ArtistBookingDTO> artistBookingDTOList;

    public PreviousworkPagerAdapter(Context context, ArrayList<ArtistBookingDTO> artistBookingDTOList) {
        this.context = context;
        this.artistBookingDTOList = artistBookingDTOList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return artistBookingDTOList.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.previousworkpageradapter, container, false);

        CircleImageView IVartist = (CircleImageView) itemView.findViewById(R.id.IVartist);
        CustomTextViewBold CTVBprevioususer = itemView.findViewById(R.id.CTVBprevioususer);
        RatingBar ratingbar = itemView.findViewById(R.id.ratingbar);
        CustomTextView CTVprice = itemView.findViewById(R.id.CTVprice);

        //IVpreviouswork.setImageResource(artistBookingDTOList.get(position).getUserImage());
        Glide.with(context).
                load(artistBookingDTOList.get(position).getUserImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(IVartist);
        ratingbar.setRating(artistBookingDTOList.get(position).getRating());
        CTVBprevioususer.setText(artistBookingDTOList.get(position).getUsername());
        CTVprice.setText("$"+artistBookingDTOList.get(position).getPrice());

        container.addView(itemView);

        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }


}


