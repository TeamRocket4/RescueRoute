package com.ambulance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ambulance.api.ApiService;
import com.ambulance.config.RetrofitClient;
import com.ambulance.entities.Position;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class MainActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    //private SupportMapFragment mapFragment;

    private StompClient stompClient;
    private CompositeDisposable compositeDisposable;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap googleMap;


    private Gson gson = new Gson();

    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        requestLocationPermission();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Call<Void> call = MainActivity.apiService.clockin(Long.parseLong("1"));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    Log.d("Retrofit", "CLOCKED IN");

                } else {
                    Log.e("Retrofit", "Error Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        connectToWebSocket();

        LocationRequest locationRequest = new LocationRequest.Builder(5000)
                .setWaitForAccurateLocation(false)
                .build();


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        sendLocation(location.getLatitude(), location.getLongitude());
                    }
                }
            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //getCurrentLocation();
            googleMap.setMyLocationEnabled(true);
//            if(position != null && position.getId()!=0){
//                sendMessage("/app/position", position);
//            }else{
//                Log.e("position", "NO POSITION");
//            }
        }
    }

    @SuppressLint("CheckResult")
    private void connectToWebSocket() {
        String url = "ws://10.0.2.2:8080/position/websocket"; // Adjust the URL to match your server

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);
        compositeDisposable = new CompositeDisposable();

//        stompClient.lifecycle().subscribe(lifecycleEvent -> {
//            switch (lifecycleEvent.getType()) {
//
//                case OPENED:
//                    Log.d("Stomp", "Stomp connection opened");
//                    break;
//
//                case ERROR:
//                    Log.e("Stomp", "Error", lifecycleEvent.getException());
//                    break;
//
//                case CLOSED:
//                    Log.d("Stomp", "Stomp connection closed");
//                    break;
//            }
//        });

        stompClient.connect();

        subscribeToChannel("/map/positions");
    }

    private void subscribeToChannel(String topic) {
        Disposable disposable = stompClient.topic(topic).subscribe(message -> {
            Log.d("Stomp", "Received message: " + message.getPayload());
        }, throwable -> {
            Log.d("Stomp", "Error subscribing: " + throwable.getMessage());
        });
        compositeDisposable.add(disposable);
    }


    @SuppressLint("CheckResult")
    private void sendMessage(String destination, Position position) {
        stompClient.send(destination, gson.toJson(position)).subscribe(() -> {
            Log.d("Stomp","Message sent successfully");
        }, throwable -> {
            Log.d("Stomp","Error sending message: " + throwable.getMessage());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Call<Void> call = MainActivity.apiService.clockout(Long.parseLong("1"));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Retrofit", "CLOCKED OUT");

                } else {
                    Log.e("Retrofit", "Error Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Failure: " + t.getMessage());
            }
        });
        if (stompClient != null) {
            stompClient.disconnect();
        }
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }



//    private void getCurrentLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
//            if (location != null && googleMap != null) {
//                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                sendLocation(location.getLatitude(), location.getLongitude());
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));
//            }
//        });
//    }

    public void sendLocation(double Lat, double Long){
        sendMessage("/app/position", new Position(1, Lat, Long));
    }





































    private void requestLocationPermission() {
        ActivityResultLauncher<String[]> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                });

        requestPermissionLauncher.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

}