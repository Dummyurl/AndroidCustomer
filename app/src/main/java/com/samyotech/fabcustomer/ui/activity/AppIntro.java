package com.samyotech.fabcustomer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.preferences.SharedPrefrence;
import com.samyotech.fabcustomer.ui.adapter.AppIntroPagerAdapter;
import com.samyotech.fabcustomer.utils.ProjectUtils;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.CustomTextView;


public class AppIntro extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    ViewPager mViewPager;
    private AppIntroPagerAdapter mAdapter;
    private LinearLayout viewPagerCountDots;
    private int dotsCount;
    private ImageView[] dots;
    public SharedPrefrence preference;
    private Context mContext;
    private LinearLayout llSignin, llSignup;
    private CustomTextView btnSkip, btnStart;
    int[] mResources = {R.drawable.intro_1, R.drawable.intro_2, R.drawable.intro_3};
    public int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.Fullscreen(AppIntro.this);
        setContentView(R.layout.activity_app_intro2);
        mContext = AppIntro.this;
        preference = SharedPrefrence.getInstance(mContext);

        btnSkip = (CustomTextView) findViewById(R.id.btnSkip);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        btnStart = (CustomTextView) findViewById(R.id.btnLetsStart);
        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerCountDots = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        llSignin.setOnClickListener(this);
        llSignup.setOnClickListener(this);


        mAdapter = new AppIntroPagerAdapter(AppIntro.this, mContext, mResources);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
        setPageViewIndicator();
        if (getIntent().hasExtra(Consts.PICKER_FLAG)) {
            flag = getIntent().getIntExtra(Consts.PICKER_FLAG, 0);
        }
    }

    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    18,
                    18
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }


        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {

        } else {

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void scrollPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (flag == 1) {
            finish();
        } else {
            //  clickDone();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSignin:
                startActivity(new Intent(mContext, SignInActivity.class));
                break;
            case R.id.llSignup:
                startActivity(new Intent(mContext, SignUpActivity.class));
                break;
        }
    }
}
