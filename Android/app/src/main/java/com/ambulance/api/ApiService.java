package com.ambulance.api;


import com.ambulance.entities.Hospital;
import com.ambulance.entities.User;
import com.ambulance.requests.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/auth/login")
    Call<User> login(@Body LoginRequest loginRequest);

    @POST("api/user/clockin/{id}")
    Call<Void> clockin(@Path("id") Long id);

    @POST("api/user/clockout/{id}")
    Call<Void> clockout(@Path("id") Long id);

    @GET("hospitals")
    Call<List<Hospital>> getHospitals();
}
