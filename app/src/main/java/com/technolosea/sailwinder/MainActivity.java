package com.technolosea.sailwinder;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;

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
                    List<Mark> marks = dbLayer.getMarks();
                    lastLocation = location;
                    if (marks.size()>0) {
                        Mark nextMark = marks.get(0);
                        ((TextView)findViewById(R.id.rngTextView)).setText(String.valueOf(Math.round(GeoCalc.getDistance(location.getLatitude(),location.getLongitude(),nextMark.coordinate1.getLatitude(),nextMark.coordinate1.getLongitude()))));
                        ((TextView)findViewById(R.id.ditTextView)).setText(String.valueOf(Math.round(GeoCalc.getAzimuth(location.getLatitude(),location.getLongitude(),nextMark.coordinate1.getLatitude(),nextMark.coordinate1.getLongitude()))));
                    }

                    dbLayer.addTrackPoint(userId, new GeoPoint(location.getLatitude(),location.getLongitude()),new Timestamp(new Date()));

                    Log.d(TAG,location.toString());
                }
            }
        };
    }

    protected displayEndingStatus(int endingNumber)
    {
        Toast toast;
        if (endingNumber <= 3)
            toast = Toast.makeText(this,
                "Congratulations! you won a prize.",
                Toast.LENGTH_SHORT);
        else
        {
            toast = Toast.makeText(this,
                "You lost. Better luck next time.",
                Toast.LENGTH_SHORT);
        }
        toast.show();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();

        endingMak = DbLayer.getEndingMark(); 
        Map<String, Object> user_track = DbLayer.getTrackByUserId(userId);
        boolean is_participant_done = GeoCalc.participantHasFinished(endingMak.coordinate1, endingMark.coordinate2, user_track.get("coordinate"));
        // TOOD: need to add here current number of finished participants and update the if condition (that checks is_participant_done) accordingly
        if (is_participant_done)
        {
            // update number of finished participants
            // print if the user is a winner (1st/2nd/3rd place) or not (at least 4th place) 
        }
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
