package com.example.mapdemo.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.example.mapdemo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by karan@ucreate.co.in for base
 * of all {@link android.app.Activity} on 10-05-2016.
 */
public class BaseActivity extends AppCompatActivity {

    Snackbar snackbar;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @return True if internet is working, Otherwise @return
     * False.
     */
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(BaseActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /**
     * create common snackbar for all application to display
     * toast messages.
     */
    public void showSnackBarMessages(CoordinatorLayout coordinatorLayout, String sMessage) {

        snackbar = Snackbar
                .make(coordinatorLayout, sMessage, Snackbar.LENGTH_LONG);

        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(Color.GRAY);
        snackbar.show();
    }

    /**
     * Show progress "Please wait" message.
     */
    public void showPleaseWait(String sMessage) {

        if (mProgress == null) {
            mProgress = new ProgressDialog(BaseActivity.this);
            mProgress.setCancelable(false);
            mProgress.setMessage(sMessage);
            mProgress.show();
        }
    }

    /**
     * hide Progress dialog.
     */
    public void hideProgress() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
            mProgress = null;
        }
    }

    /**
     * Implement a method Custom showEnterCompetitionDialog
     * Alert Dialog for input ic_user First & Last name,
     * email address and Mobile number.
     */
    public void showAlertMessage(String strAlertMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(strAlertMessage)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * Implements a method to RETURN the name of MONTH from
     * specific date format.
     *
     * @param strDate : Example => "2009-11-30T18:30:00Z"
     * @return strDate  : MMM [NOV]
     */
    public String getFormateMonth(String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM");

        try {
            Date date = inputFormat.parse(strDate);
            strDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strDate;
    }

}
