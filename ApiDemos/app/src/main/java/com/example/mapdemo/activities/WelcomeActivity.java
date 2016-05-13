package com.example.mapdemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mapdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    /*************************************
     * DECLARATION OF CLASS INSTANCES
     ************************************/
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    /***
     * Handle click events here.
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogIn:
                requestIntent(GetDirections.class);
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
}
