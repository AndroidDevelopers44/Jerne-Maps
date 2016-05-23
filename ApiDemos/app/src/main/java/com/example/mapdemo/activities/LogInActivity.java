package com.example.mapdemo.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mapdemo.R;
import com.example.mapdemo.api.WebServiceMethods;
import com.example.mapdemo.constant.WebAPI;
import com.example.mapdemo.models.LoginAPI;
import com.example.mapdemo.models.LoginResult;
import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * The {@link LogInActivity} used for LOGIN functionality
 * before navigate to {@link WelcomeActivity}.
 * <p/>
 *
 * @author karannassa44@gmail.com
 * @version 1.0
 * @since 16 May, 2016
 */
public class LogInActivity extends BaseActivity {

    /*************************************
     * DECLARATION OF LOCAL INSTANCES
     ************************************/
    public String LOG_TAG = LogInActivity.class.getSimpleName();

    String strEmail, strPassword, strErrorMessage;

    /*************************************
     * DECLARATION OF CLASS INSTANCES
     ************************************/
    @Bind(R.id.tbLogIn)
    Toolbar tbLogIn;

    @Bind(R.id.rbRememberPassword)
    RadioButton rbRememberPassword;

    @Bind(R.id.etEmail)
    EditText etEmail;

    @Bind(R.id.etPassword)
    EditText etPassword;

    @Bind(R.id.btLogIn)
    Button btLogIn;

    @Bind(R.id.tvJerneTagline)
    TextView tvJerneTagline;

    //Custom TypeFaceb declaration.
    Typeface tfCaviarDreamsBold, tfCaviarDreams;

    // LoginAPI instance for pass in Login API.
    LoginAPI loginAPI;

    // Instance of LoginResult model class for get API json response.
    LoginResult loginResult;

    /**
     * Declaration of Login callback listener.
     */
    private View.OnClickListener mLogInCallback = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            strEmail = etEmail.getText().toString();
            strPassword = etPassword.getText().toString();

            if (isValidInput()) {
                if (isOnline(LogInActivity.this)) {
                    showPleaseWait("Loading, please wait...");
                    callDataService();
                } else {
                    showAlertMessage("Please check your internet connection.");
                }
            } else {
                showAlertMessage(strErrorMessage);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Initialize Butter-knife
        ButterKnife.bind(this);

        setFontTypeFace();

        //Set Action Tool bar.
        try {
            setSupportActionBar(tbLogIn);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.text_title_Login));
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        //Login button click event handle here.
        btLogIn.setOnClickListener(mLogInCallback);
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

    /**
     * When user pressed Forgot Password to recover.
     *
     * @param view
     */
    public void onForgotPasword(View view) {
        showAlertMessage("Coming soon...");
    }

    /**
     * Implements a method to validate EMAIL &
     * Password correct or not.
     *
     * @return True, if Email format and Password correct otherwise False.
     */
    private boolean isValidInput() {

        if (strEmail.length() != 0 && strPassword.length() != 0) {
            if (emailValidator(strEmail)) {
                return true;
            } else {
                strErrorMessage = getResources().getString(R.string.error_valid_email);
                return false;
            }
        } else {
            strErrorMessage = getResources().getString(R.string.error_email_password_required);
            return false;
        }
    }

    /**
     * validate your email address format. Ex-name@example.com
     *
     * @param email
     */
    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Implements a method to hit web service using
     * RETROFIT {POST-TYPE} to get JSON data.
     */
    public void callDataService() {

        loginAPI = new LoginAPI(strEmail, strPassword);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.checkLoginDetail(strEmail, strPassword/*loginAPI*/, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();

                showAlertMessage("" + error);
            }
        });
    }

    /**
     * Implement this method for get LOGIN API response. If result
     * SUCCESS then proceed to further process.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<LoginResult>() {
        }.getType();
        LoginResult loginResult = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        if (loginResult.getStatus().equalsIgnoreCase("Success")) {

            Log.e(LOG_TAG, "getMessage : " + loginResult.getMessage());
            Log.e(LOG_TAG, "getStatus : " + loginResult.getStatus());
            Log.e(LOG_TAG, "getData().getUserId() : " + loginResult.getData().getUserId());

            //Navigate to HOME Activity.
            Intent intent = new Intent(LogInActivity.this, GetDirections.class);
            startActivity(intent);
            this.finish();
        } else {
            showAlertMessage(loginResult.getMessage());
        }
        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements a method to set FONT style using .ttf by putting
     * in main\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfCaviarDreamsBold = Typeface.createFromAsset(getAssets(), "fonts/Caviar_Dreams_Bold.ttf");
        tfCaviarDreams = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");

        rbRememberPassword.setTypeface(tfCaviarDreams);
        etEmail.setTypeface(tfCaviarDreams);
        etPassword.setTypeface(tfCaviarDreams);
        tvJerneTagline.setTypeface(tfCaviarDreamsBold);
    }
}
