package com.ambulance.ui.home;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ambulance.R;
import com.ambulance.api.DirectionApiService;
import com.ambulance.config.RetrofitClient;
import com.ambulance.databinding.FragmentHomeBinding;
import com.ambulance.requests.DirectionsResponse;
import com.ambulance.utils.PolyUtil;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomeBinding binding;

    private GoogleMap googleMap;

    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback;
    private Marker currentLocationMarker;

//    ApplicationInfo applicationInfo = getContext().getPackageManager()
//            .getApplicationInfo(requireContext().getPackageName(), PackageManager.GET_META_DATA);

    public HomeFragment() throws PackageManager.NameNotFoundException {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        final MapView mapView= binding.mapView;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        if(mapView!=null){
            Log.e("aa","ee");
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }
        Log.e("aa","eeaa");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setTrafficEnabled(true);
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            getCurrentLocation();
            Log.e("aa","eeeeeeeeeeeeeeeeeee");
        }else {
            requestLocationPermission();
        }

    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Create a LocationRequest for real-time updates
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setInterval(5000) // 5 seconds
                .setFastestInterval(2000); // 2 seconds

        // Handle location updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null && googleMap != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        // Move the camera to the current location
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));

                        // Update marker position
                        if (currentLocationMarker != null) {
                            currentLocationMarker.setPosition(currentLatLng);
                        } else {
                            currentLocationMarker = googleMap.addMarker(new MarkerOptions()
                                    .position(currentLatLng)
                                    .title("You are here"));
                        }
                    }
                }
            }
        };

        // Request location updates
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }





    private void requestLocationPermission() {
        ActivityResultLauncher<String[]> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                    Boolean fineLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                    if (fineLocationGranted != null && fineLocationGranted) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            googleMap.setMyLocationEnabled(true);
                            getCurrentLocation();
                        }

                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            googleMap.setMyLocationEnabled(true);
                            getCurrentLocation();
                        }
                    } else {
                        // Permission denied
                        // Handle the case where the user denies location access
                    }
                });

        // Request permissions
        requestPermissionLauncher.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }




    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null && googleMap != null) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng destination = new LatLng(31.5992824, -8.0428334);

                // Move the camera to the current location
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));

                // Optional: Add a marker at the current location
                googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("You are here").icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
                fetchRoute(currentLatLng, destination);
                googleMap.addMarker(new MarkerOptions().position(destination).title("Hospital").icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));
            }
        });
    }



    private void fetchRoute(LatLng origin, LatLng destination) {
        String originParam = origin.latitude + "," + origin.longitude;
        String destinationParam = destination.latitude + "," + destination.longitude;

        DirectionApiService service = RetrofitClient.getClientGoogleAPI().create(DirectionApiService.class);
        Call<DirectionsResponse> call = service.getDirections(
                originParam,
                destinationParam,
                "pessimistic",         // Traffic model
                "now",                // Departure time
                "AIzaSyDQ-1CftDTCT4YI7-d6iu8rISllmyO3CGU"   // Replace with your actual API key
        );

        call.enqueue(new retrofit2.Callback<DirectionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull retrofit2.Response<DirectionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DirectionsResponse.Route> routes = response.body().routes;
                    if (!routes.isEmpty()) {
                        String points = routes.get(0).overviewPolyline.points;
                        drawRoute(PolyUtil.decode(points));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void drawRoute(List<LatLng> decodedPath) {
        googleMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath)
                .width(10)
                .color(Color.BLUE));
    }

}