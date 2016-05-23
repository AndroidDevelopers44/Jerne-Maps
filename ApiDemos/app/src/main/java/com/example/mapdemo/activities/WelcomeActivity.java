package com.example.mapdemo.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mapdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    /*************************************
     * DECLARATION OF CLASS INSTANCES
     ************************************/
    Intent intent;

    Typeface tfCaviarDreamsBold;

    @Bind(R.id.tvJerneTagline)
    TextView tvJerneTagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Initialize all view resources ID.
        ButterKnife.bind(WelcomeActivity.this);

        setFontTypeFace();
    }

    /***
     * Handle click events here.
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogIn:
                requestIntent(LogInActivity.class);
                break;

            case R.id.btSignUp:
                requestIntent(SignUpActivity.class);
                break;
        }
    }

    /**
     * Implements a method to use for INTENT service. All type of intent
     * navigation use this method.
     */
    private void requestIntent(Class intentToClass) {
        intent = new Intent(WelcomeActivity.this, intentToClass);
        startActivity(intent);
    }


    /**
     * Implements a method to set FONT style using .ttf by putting
     * in main\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfCaviarDreamsBold = Typeface.createFromAsset(getAssets(), "fonts/Caviar_Dreams_Bold.ttf");

        tvJerneTagline.setTypeface(tfCaviarDreamsBold);
    }
}
