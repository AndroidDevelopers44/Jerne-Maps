package com.example.mapdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mapdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity {

    /*************************************
     * DECLARATION OF CLASS INSTANCES
     ************************************/
    @Bind(R.id.tbSignUp)
    Toolbar tbSignUp;

    @Bind(R.id.etFirstName)
    EditText etFirstName;

    @Bind(R.id.etLastName)
    EditText etLastName;

    @Bind(R.id.etEmail)
    EditText etEmail;

    @Bind(R.id.etPassword)
    EditText etPassword;

    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;

    @Bind(R.id.etContact)
    EditText etContact;

    @Bind(R.id.btRegiter)
    Button btRegiter;

    /*************************************
     * DECLARATION OF LOCAL INSTANCES
     ************************************/
    String strFirstName, strLastName, strEmail, strPassword, strConfirmPassword, strContact;
    String strErrorMessage = "";

    /**
     * Register click event handle here.
     */
    private View.OnClickListener mRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (isValidRegisterForm()) {

            } else {
                showAlertMessage(strErrorMessage);
            }
        }
    };

    /**
     * Implements a method to check/validation of REGISTRATION
     * form.
     */
    private boolean isValidRegisterForm() {

        //Get all input fields data to local memory.
        strFirstName = etFirstName.getText().toString();
        strLastName = etLastName.getText().toString();
        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();
        strConfirmPassword = etConfirmPassword.getText().toString();
        strContact = etContact.getText().toString();

        if(strFirstName.trim().equals("")){
            etFirstName.setError("required.");
        }

        if(strEmail.trim().equals("") || strEmail.equalsIgnoreCase("name@example.com")){
            etFirstName.setError("required.");
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        setSupportActionBar(tbSignUp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_title_register));

        btRegiter.setOnClickListener(mRegisterListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /**
             *  Tool bar back arrow handler.
             */
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
