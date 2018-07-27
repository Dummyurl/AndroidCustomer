package com.payu.payuui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by samyotech on 27/2/17.
 */

public class CustomButtonPayu extends Button {
    public CustomButtonPayu(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomButtonPayu(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomButtonPayu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public CustomButtonPayu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCachePayu.getTypeface("Raleway-Medium.ttf", context);
        setTypeface(customFont);
    }
}
