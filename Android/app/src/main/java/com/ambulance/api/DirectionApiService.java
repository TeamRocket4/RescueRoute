package com.ambulance.api;

import com.ambulance.requests.DirectionsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionApiService {
    @GET("directions/json")
    Call<DirectionsResponse> getDirections(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("traffic_model") String trafficModel,
            @Query("departure_time") String departureTime,
            @Query("key") String apiKey
    );
}
