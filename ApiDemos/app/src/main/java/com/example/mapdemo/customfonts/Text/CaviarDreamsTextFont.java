package com.example.mapdemo.customfonts.Text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by karan@ucreate.co.in to create custom
 * font style which CaviarDreams.ttf on 16-05-2016.
 */
public class CaviarDreamsTextFont extends TextView {

    public CaviarDreamsTextFont(Context context) {
        super(context);
        setFont();
    }

    public CaviarDreamsTextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CaviarDreamsTextFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (defStyle == Typeface.ITALIC) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/CaviarDreams.ttf");
            setTypeface(font, Typeface.ITALIC);
        }

        //setFont();
    }

    private void setFont() {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/CaviarDreams.ttf");
        setTypeface(font, Typeface.ITALIC);
    }

}