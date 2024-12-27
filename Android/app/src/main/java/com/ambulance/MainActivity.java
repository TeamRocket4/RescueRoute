package com.ambulance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import com.ambulance.api.ApiService;
import com.ambulance.config.RetrofitClient;
import com.ambulance.ui.login.LoginActivity;
import com.google.android.gms.maps.SupportMapFragment;


public class MainActivity extends AppCompatActivity
        //implements OnMapReadyCallback
{
    public static ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.buttonNavigate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SecondActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

//        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        Call<User> call = apiService.login(new LoginRequest("abcd@ed.com", "123"));
//
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()) {
//                    User user = response.body();
//                    Log.d("Retrofit", "User: " + user.toString());
//
//                } else {
//                    Log.e("Retrofit", "Error Code: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.e("Retrofit", "Failure: " + t.getMessage());
//            }
//        });
//
//
//
//
//        Toast.makeText(this, "dad", Toast.LENGTH_LONG).show();

//        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST, new OnMapsSdkInitializedCallback() {
//            @Override
//            public void onMapsSdkInitialized(MapsInitializer.Renderer renderer) {
//                Log.d("MapsDemo", "Renderer used: " + renderer.name());
//            }
//        });
    }

//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        Log.e("map",googleMap.getMapCapabilities().toString());
//        LatLng sydney = new LatLng(31.6225, 7.9898);
//        googleMap.setTrafficEnabled(true);
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}