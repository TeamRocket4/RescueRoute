package com.ambulance.data;

import com.ambulance.MainActivity;
import com.ambulance.entities.User;
import com.ambulance.requests.LoginRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private User user = null;
    public Result<User> login(String username, String password) {

        try {

            Call<User> call = MainActivity.apiService.login(new LoginRequest(username, password));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        user = response.body();
                    }
                }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                
            }
        });

            if(user==null){
                throw new Exception();
            }

            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}