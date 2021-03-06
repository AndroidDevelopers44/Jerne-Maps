package com.example.mapdemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The {@link LoginAPI} used for GET Login result params from web service
 * either success or Failure.
 *
 * @author karannassa44@gmail.com
 * @version 1.0
 * @since 22 May, 2016
 */
public class LoginResult {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private LoginResultData data;


    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The data
     */
    public LoginResultData getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(LoginResultData data) {
        this.data = data;
    }
}
