package com.vgroup.c2c_embedded_library;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class PoppinsBoldTextView extends TextView {

    public PoppinsBoldTextView(Context context) {
        super(context);
        style(context);
    }

    public PoppinsBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context);
    }

    public PoppinsBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context);
    }

    private void style(Context context) {
        Typeface typeface = ResourcesCompat.getFont(context, R.font.poppins_bold);
        setTypeface(typeface);
    }

}


