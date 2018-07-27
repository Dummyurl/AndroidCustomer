package com.samyotech.fabcustomer.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.samyotech.fabcustomer.utils.CustomButton;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.utils.CustomEditText;

public class PromotionActivity extends AppCompatActivity implements View.OnClickListener {


    Context mContext;
    ImageView IVback;
    CustomEditText CETcode;
    CustomButton CBapplycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        mContext = PromotionActivity.this;
        init();
    }

    public void init() {
        IVback = findViewById(R.id.IVback);
        CETcode = findViewById(R.id.CETcode);
        CBapplycode = findViewById(R.id.CBapplycode);

        IVback.setOnClickListener(this);
        CBapplycode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IVback:
                finish();
                break;
            case R.id.CBapplycode:
                break;

        }
    }
}
