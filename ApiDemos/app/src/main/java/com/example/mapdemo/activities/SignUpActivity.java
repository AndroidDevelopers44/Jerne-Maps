package com.example.mapdemo.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mapdemo.R;
import com.example.mapdemo.api.WebServiceMethods;
import com.example.mapdemo.constant.WebAPI;
import com.example.mapdemo.models.LoginAPI;
import com.example.mapdemo.models.LoginResult;
import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class SignUpActivity extends BaseActivity {

    private String LOG_TAG = SignUpActivity.class.getSimpleName();

    /**
     * EMAIL and PHONE number validation.
     */
//    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";

    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "###-#######";

    /*************************************
     * DECLARATION OF CLASS INSTANCES
     ************************************/
    @Bind(R.id.tbSignUp)
    Toolbar tbSignUp;

    @Bind(R.id.tilFname)
    TextInputLayout tilFname;

    @Bind(R.id.tilLname)
    TextInputLayout tilLname;

    @Bind(R.id.tilEmail)
    TextInputLayout tilEmail;

    @Bind(R.id.tilPassword)
    TextInputLayout tilPassword;

    @Bind(R.id.tilConfirmPassword)
    TextInputLayout tilConfirmPassword;

    @Bind(R.id.tilContact)
    TextInputLayout tilContact;

    @Bind(R.id.tvJerneTagline)
    TextView tvJerneTagline;

    @Bind(R.id.tvErrorView)
    TextView tvErrorView;

    @Bind(R.id.etFname)
    EditText etFname;

    @Bind(R.id.etLname)
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

    //Custom TypeFaceb declaration.
    Typeface tfCaviarDreamsBold, tfCaviarDreams;

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

            //Get all input fields data to local memory.
            strFirstName = etFname.getText().toString();
            strLastName = etLastName.getText().toString();
            strEmail = etEmail.getText().toString();
            strPassword = etPassword.getText().toString();
            strConfirmPassword = etConfirmPassword.getText().toString();
            strContact = etContact.getText().toString();

            if (isValidRegisterForm()) {
                if (isOnline(SignUpActivity.this)) {
                    showPleaseWait("Loading, please wait...");
                    callDataService();
                } else {
                    showAlertMessage("Please check your internet connection.");
                }
            } else {
                tvErrorView.setText(strErrorMessage);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initialize Butter-knife
        ButterKnife.bind(this);

        setFontTypeFace();

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

    /**
     * Implements a method to hit web service using
     * RETROFIT {POST-TYPE} to get JSON data.
     */
    public void callDataService() {

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.sendRegisterDetail(strFirstName, strLastName, strEmail, strPassword, strContact/*loginAPI*/, new Callback<JsonObject>() {
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

//            //Navigate to HOME Activity.
//            Intent intent = new Intent(LogInActivity.this, GetDirections.class);
//            startActivity(intent);
//            this.finish();

            resetRegisterForm();

            showAlertMessage(getResources().getString(R.string.text_register_successfully));
        } else {
            showAlertMessage(loginResult.getMessage());
        }
        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements a method to reset SignUp form after fill
     * successfully.
     */
    private void resetRegisterForm() {
        etFname.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        etContact.setText("");
    }

//    // call this method when you need to check email validation
//    public static boolean isEmailAddress(EditText editText, boolean required) {
//        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
//    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }
        ;

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    /**
     * Implements a method to check/validation of REGISTRATION
     * form.
     */
    private boolean isValidRegisterForm() {

        if (strFirstName.length() != 0 || strLastName.length() != 0 || strEmail.length() != 0 || strPassword.length() != 0 ||
                strConfirmPassword.length() != 0 || strContact.length() != 0) {

            if (emailValidator(strEmail)) {

                if (strPassword.equals(strConfirmPassword)) {
                    return true;
                } else {
                    strErrorMessage = getResources().getString(R.string.error_password_no_match);
                }
            } else {
                strErrorMessage = getResources().getString(R.string.error_valid_email);
                return false;
            }

        } else {
            strErrorMessage = getResources().getString(R.string.error_all_required);
            return false;
        }
        return true;
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
     * Implements a method to set FONT style using .ttf by putting
     * in main\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfCaviarDreamsBold = Typeface.createFromAsset(getAssets(), "fonts/Caviar_Dreams_Bold.ttf");
        tfCaviarDreams = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");

        etFname.setTypeface(tfCaviarDreams);
        etLastName.setTypeface(tfCaviarDreams);
        etEmail.setTypeface(tfCaviarDreams);
        etPassword.setTypeface(tfCaviarDreams);
        etConfirmPassword.setTypeface(tfCaviarDreams);
        etContact.setTypeface(tfCaviarDreams);

        tilFname.setTypeface(tfCaviarDreams);
        tilLname.setTypeface(tfCaviarDreams);
        tilEmail.setTypeface(tfCaviarDreams);
        tilPassword.setTypeface(tfCaviarDreams);
        tilConfirmPassword.setTypeface(tfCaviarDreams);
        tilContact.setTypeface(tfCaviarDreams);

        tvJerneTagline.setTypeface(tfCaviarDreamsBold);
    }

}
