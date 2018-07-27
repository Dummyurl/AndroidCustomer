package com.payu.payuui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by varun on 27/2/17.
 */
public class CustomTextViewPayu extends TextView
{

    public CustomTextViewPayu(Context context) {

        super(context);
        applyCustomFont(context);
    }

    public CustomTextViewPayu(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextViewPayu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public CustomTextViewPayu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCachePayu.getTypeface("Raleway-Medium.ttf", context);
        setTypeface(customFont);
    }
}
