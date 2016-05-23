package com.example.mapdemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The {@link LoginResultData} used for GET Login result key value <b>data</b>
 * from web service.
 *
 * @author karannassa44@gmail.com
 * @version 1.0
 * @since 22 May, 2016
 */
public class LoginResultData {

    @SerializedName("userId")
    @Expose
    private String userId;

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
