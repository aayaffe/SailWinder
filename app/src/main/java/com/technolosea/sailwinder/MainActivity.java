package com.technolosea.sailwinder;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;


    DbLayer dbLayer;
    private Location lastLocation;
    private String userId = "amit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        dbLayer = new DbLayer();
        dbLayer.updateMarks();


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    lastLocation = location;
                    dbLayer.addTrackPoint(userId, new GeoPoint(location.getLatitude(),location.getLongitude()),new Timestamp(new Date()));
                    Log.d(TAG,location.toString());
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();

    }

    LocationRequest locationRequest;
    int UPDATE_INTERVAL = 5000;
    int FASTEST_INTERVAL = 5000;

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null /* Looper */);
    }

    public void updateNameButtonOnClick(View v){
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();
        if (!name.isEmpty()){
            userId = name;
        }
    }
}
