package com.example.mapdemo.customfonts.Button;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by karan@ucreate.co.in to create custom
 * font style which CaviarDreams.ttf on 16-05-2016.
 */
public class CaviarDreamsButtonFont extends Button {

    public CaviarDreamsButtonFont(Context context) {
        super(context);
        setFont();
    }

    public CaviarDreamsButtonFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CaviarDreamsButtonFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (defStyle == Typeface.ITALIC) {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/CaviarDreams.ttf");
            setTypeface(font, Typeface.ITALIC);
        }

        //setFont();
    }

    private void setFont() {

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/CaviarDreams.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}