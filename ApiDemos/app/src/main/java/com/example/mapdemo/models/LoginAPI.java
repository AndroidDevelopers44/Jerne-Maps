package com.example.mapdemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The {@link LoginAPI} used for POST Login web service params like:
 * <p/>
 * <br/> 1. EMAIL
 * <br/> 2. PASSWORD
 *
 * @author karannassa44@gmail.com
 * @version 1.0
 * @since 22 May, 2016
 */
public class LoginAPI {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     */
    public LoginAPI() {
    }

    /**
     * @param email
     * @param password
     */
    public LoginAPI(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
