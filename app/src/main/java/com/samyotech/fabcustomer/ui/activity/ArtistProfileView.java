package com.samyotech.fabcustomer.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.gson.Gson;
import com.samyotech.fabcustomer.DTO.ArtistBookingDTO;
import com.samyotech.fabcustomer.DTO.ArtistDetailsDTO;
import com.samyotech.fabcustomer.DTO.ProductDTO;
import com.samyotech.fabcustomer.DTO.QualificationsDTO;
import com.samyotech.fabcustomer.DTO.ReviewsDTO;
import com.samyotech.fabcustomer.DTO.SkillsDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.network.NetworkManager;
import com.samyotech.fabcustomer.preferences.SharedPrefrence;
import com.samyotech.fabcustomer.ui.adapter.PreviousworkPagerAdapter;
import com.samyotech.fabcustomer.ui.adapter.ProductPagerAdapter;
import com.samyotech.fabcustomer.ui.adapter.QualificationAdapter;
import com.samyotech.fabcustomer.ui.adapter.ReviewAdapter;
import com.samyotech.fabcustomer.ui.adapter.SkillsAdapter;
import com.samyotech.fabcustomer.utils.CustomButton;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistProfileView extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private String TAG = ArtistProfileView.class.getSimpleName();
    private Context mContext;
    private LinearLayout llBack, llMsg;
    private CustomButton cbRequest;
    private CustomTextViewBold tvNameHedar, tvName, tvReviewsText;
    private CircleImageView ivArtist;
    private CustomTextView tvWork, tvLocation, tvArtistRate, tvRating, tvJobComplete, tvProfileComplete, tvAbout;
    private RatingBar ratingbar;
    private RecyclerView rvQualification, rvSkills, rvReviews;
    private ViewPager vpProducts, vpPreviousWork;
    private ImageView ic_left_pro, ic_right_pro, ic_left_pw, ic_right_pw;
    private String artist_id = "";
    private ArtistDetailsDTO artistDetailsDTO;

    private SkillsAdapter skillsAdapter;
    private QualificationAdapter qualificationAdapter;
    private ProductPagerAdapter productPagerAdapter;
    private PreviousworkPagerAdapter previousworkPagerAdapter;
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager mLayoutManagerSkills, mLayoutManagerQuali, mLayoutManagerReview;

    private ArrayList<SkillsDTO> skillsDTOList;
    private ArrayList<QualificationsDTO> qualificationsDTOList;
    private ArrayList<ProductDTO> productDTOList;
    private ArrayList<ArtistBookingDTO> artistBookingDTOList;
    private ArrayList<ReviewsDTO> reviewsDTOList;

    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private CustomTextViewBold tvBookNow;
    private HashMap<String, String> paramsBookingOp;
    private HashMap<String, String> paramBookAppointment = new HashMap<>();

    private Date date;
    SimpleDateFormat sdf1, timeZone;
    public static String name = "", email = "", mobile = "";
    private DialogInterface dialog_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile_view);
        mContext = ArtistProfileView.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        sdf1 = new SimpleDateFormat(Consts.DATE_FORMATE_SERVER, Locale.ENGLISH);
        timeZone = new SimpleDateFormat(Consts.DATE_FORMATE_TIMEZONE, Locale.ENGLISH);

        date = new Date();
        userDTO = prefrence.getParentUser(Consts.USER_DTO);
        if (getIntent().hasExtra(Consts.ARTIST_ID)) {
            artist_id = getIntent().getStringExtra(Consts.ARTIST_ID);

        }
        parms.put(Consts.ARTIST_ID, artist_id);
        parms.put(Consts.USER_ID, userDTO.getUser_id());

        setUiAction();
    }

    public void setUiAction() {
        tvBookNow = findViewById(R.id.tvBookNow);
        llBack = findViewById(R.id.llBack);
        llMsg = findViewById(R.id.llMsg);
        cbRequest = findViewById(R.id.cbRequest);
        tvNameHedar = findViewById(R.id.tvNameHedar);
        tvName = findViewById(R.id.tvName);
        ivArtist = findViewById(R.id.ivArtist);
        tvWork = findViewById(R.id.tvWork);
        tvLocation = findViewById(R.id.tvLocation);
        tvArtistRate = findViewById(R.id.tvArtistRate);
        tvRating = findViewById(R.id.tvRating);
        tvJobComplete = findViewById(R.id.tvJobComplete);
        tvProfileComplete = findViewById(R.id.tvProfileComplete);
        tvAbout = findViewById(R.id.tvAbout);
        tvReviewsText = findViewById(R.id.tvReviewsText);
        ratingbar = findViewById(R.id.ratingbar);
        rvQualification = findViewById(R.id.rvQualification);
        rvSkills = findViewById(R.id.rvSkills);
        rvReviews = findViewById(R.id.rvReviews);
        vpProducts = findViewById(R.id.vpProducts);
        vpPreviousWork = findViewById(R.id.vpPreviousWork);
        ic_left_pro = findViewById(R.id.ic_left_pro);
        ic_right_pro = findViewById(R.id.ic_right_pro);
        ic_left_pw = findViewById(R.id.ic_left_pw);
        ic_right_pw = findViewById(R.id.ic_right_pw);

        llBack.setOnClickListener(this);
        llMsg.setOnClickListener(this);
        cbRequest.setOnClickListener(this);
        ic_left_pro.setOnClickListener(this);
        ic_right_pro.setOnClickListener(this);
        ic_left_pw.setOnClickListener(this);
        ic_right_pw.setOnClickListener(this);
        tvBookNow.setOnClickListener(this);

        mLayoutManagerSkills = new LinearLayoutManager(getApplicationContext());
        mLayoutManagerQuali = new LinearLayoutManager(getApplicationContext());
        mLayoutManagerReview = new LinearLayoutManager(getApplicationContext());

        rvSkills.setLayoutManager(mLayoutManagerSkills);
        rvQualification.setLayoutManager(mLayoutManagerQuali);
        rvReviews.setLayoutManager(mLayoutManagerReview);

        if (NetworkManager.isConnectToInternet(mContext)) {
            getArtist();

        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBookNow:
                bookDailog();

                break;
            case R.id.llBack:
                finish();
                break;
            case R.id.llMsg:
                Intent in = new Intent(mContext, OneTwoOneChat.class);
                in.putExtra(Consts.ARTIST_ID, artistDetailsDTO.getUser_id());
                in.putExtra(Consts.ARTIST_NAME, artistDetailsDTO.getName());
                mContext.startActivity(in);
                break;
            case R.id.cbRequest:
                paramBookAppointment.put(Consts.USER_ID, userDTO.getUser_id());
                paramBookAppointment.put(Consts.ARTIST_ID, artistDetailsDTO.getUser_id());
                clickScheduleDateTime();
                break;
            case R.id.ic_left_pro:
                int previous = getItemMinusPro(1);
                vpProducts.setCurrentItem(previous);
                break;
            case R.id.ic_right_pro:
                int next = getItemPlusPro(1);
                vpProducts.setCurrentItem(next);
                break;
            case R.id.ic_left_pw:
                int previousPw = getItemMinusPW(1);
                vpPreviousWork.setCurrentItem(previousPw);
                break;
            case R.id.ic_right_pw:
                int nextPw = getItemPlusPW(1);
                vpPreviousWork.setCurrentItem(nextPw);
                break;

        }
    }

    public void getArtist() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_ARTIST_BY_ID_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    try {
                        ProjectUtils.showToast(mContext, msg);

                        artistDetailsDTO = new Gson().fromJson(response.getJSONObject("data").toString(), ArtistDetailsDTO.class);
                        showData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

    public void showData() {
        tvNameHedar.setText(artistDetailsDTO.getName());
        tvName.setText(artistDetailsDTO.getName());
        ratingbar.setRating(artistDetailsDTO.getAva_rating());
        tvWork.setText(artistDetailsDTO.getCategory_name());
        tvLocation.setText(artistDetailsDTO.getLocation());
        tvArtistRate.setText("Rate $" + artistDetailsDTO.getPrice() +  "/hr Add On : $"+ artistDetailsDTO.getCategory_price());
        tvRating.setText("(" + artistDetailsDTO.getAva_rating() + "/5)");
        tvJobComplete.setText(artistDetailsDTO.getJobDone() + " Jobs Completed");
        tvProfileComplete.setText(artistDetailsDTO.getName());
        tvProfileComplete.setText((int) ProjectUtils.roundTwoDecimals(artistDetailsDTO.getCompletePercentages()) + "% Completion");

        tvAbout.setText(artistDetailsDTO.getAbout_us());

        Glide.with(mContext).
                load(artistDetailsDTO.getImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivArtist);

        skillsDTOList = new ArrayList<>();
        skillsDTOList = artistDetailsDTO.getSkills();
        skillsAdapter = new SkillsAdapter(mContext, skillsDTOList);
        rvSkills.setAdapter(skillsAdapter);

        qualificationsDTOList = new ArrayList<>();
        qualificationsDTOList = artistDetailsDTO.getQualifications();
        qualificationAdapter = new QualificationAdapter(mContext, qualificationsDTOList);
        rvQualification.setAdapter(qualificationAdapter);


        artistBookingDTOList = new ArrayList<>();
        artistBookingDTOList = artistDetailsDTO.getArtist_booking();
        previousworkPagerAdapter = new PreviousworkPagerAdapter(mContext, artistBookingDTOList);
        vpPreviousWork.setAdapter(previousworkPagerAdapter);
        vpPreviousWork.setPageTransformer(true, new ZoomOutSlideTransformer());
        vpPreviousWork.setCurrentItem(0);
        vpPreviousWork.setOnPageChangeListener(this);

        productDTOList = new ArrayList<>();
        productDTOList = artistDetailsDTO.getProducts();
        productPagerAdapter = new ProductPagerAdapter(mContext, productDTOList);
        vpProducts.setAdapter(productPagerAdapter);
        vpProducts.setPageTransformer(true, new ZoomOutSlideTransformer());
        vpProducts.setCurrentItem(0);
        vpProducts.setOnPageChangeListener(this);

        reviewsDTOList = new ArrayList<>();
        reviewsDTOList = artistDetailsDTO.getReviews();
        reviewAdapter = new ReviewAdapter(mContext, reviewsDTOList);
        rvReviews.setAdapter(reviewAdapter);
        tvReviewsText.setText("REVIEW(" + reviewsDTOList.size() + ")");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
/*        int lastPosPro = productDTOList.size();
        if (position == 0) {
            ic_left_pro.setVisibility(View.GONE);
        } else {
            ic_left_pro.setVisibility(View.VISIBLE);
        }
        if (position == (lastPosPro - 1)) {

            ic_right_pro.setVisibility(View.GONE);
        } else {
            ic_right_pro.setVisibility(View.VISIBLE);
        }

        int lastPosPW = artistBookingDTOList.size();
        if (position == 0) {
            ic_left_pw.setVisibility(View.GONE);
        } else {
            ic_left_pw.setVisibility(View.VISIBLE);
        }
        if (position == (lastPosPW - 1)) {

            ic_right_pw.setVisibility(View.GONE);
        } else {
            ic_right_pw.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getItemPlusPro(int i) {
        return vpProducts.getCurrentItem() + i;
    }

    public int getItemMinusPro(int i) {
        return vpProducts.getCurrentItem() - i;
    }

    public int getItemPlusPW(int i) {
        return vpPreviousWork.getCurrentItem() + i;
    }

    public int getItemMinusPW(int i) {
        return vpPreviousWork.getCurrentItem() - i;
    }


    public void bookArtist() {

        paramsBookingOp = new HashMap<>();
        paramsBookingOp.put(Consts.USER_ID, userDTO.getUser_id());
        paramsBookingOp.put(Consts.ARTIST_ID, artistDetailsDTO.getUser_id());
        paramsBookingOp.put(Consts.DATE_STRING, sdf1.format(date));
        paramsBookingOp.put(Consts.TIMEZONE, timeZone.format(date));
        paramsBookingOp.put(Consts.PRICE, artistDetailsDTO.getPrice());

        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.BOOK_ARTIST_API, paramsBookingOp, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    dialog_book.dismiss();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }




    public void bookAppointment() {

        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.BOOK_APPOINTMENT_API, paramBookAppointment, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.pauseProgressDialog();
                    ProjectUtils.showToast(mContext, msg);

                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }


    public void clickScheduleDateTime() {
        new SingleDateAndTimePickerDialog.Builder(this)
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

/*
*  ProjectUtils.showDialog(mContext, "", "Are you sure you want to start a service with this Artist?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookArtist();

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, false);

*
*
* */
    public void bookDailog() {
        try {
            new AlertDialog.Builder(mContext)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Book Artist")
                    .setMessage("Are you sure you want to start a service with this Artist?")
                    .setCancelable(false)
                    .setPositiveButton("YES!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog_book = dialog;
                            bookArtist();

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
