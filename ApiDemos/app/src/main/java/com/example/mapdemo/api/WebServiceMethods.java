package com.example.mapdemo.api;

import com.example.mapdemo.models.LoginAPI;
import com.google.gson.JsonObject;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by karan@ucreate.co.in for
 * all web services on 08-03-2016.
 */
public interface WebServiceMethods {

    /**
     * Declaration of POST LOGIN JSON data web service method.
     * <p/>
     * TYPE : POST
     * <p/>
     */
    @Multipart
    @POST("/api/login.php")
    public void checkLoginDetail(@Part("email") String email, @Part("password") String password, Callback<JsonObject> response);

    /**
     * Declaration of POST REGISTER JSON data web service method.
     * <p/>
     * TYPE : POST
     * <p/>
     */
    @Multipart
    @POST("/api/register.php")
    public void sendRegisterDetail(@Part("fname") String fname, @Part("lname") String lname, @Part("email") String email, @Part("password") String password, @Part("mnumber") String mnumber, Callback<JsonObject> response);
}

