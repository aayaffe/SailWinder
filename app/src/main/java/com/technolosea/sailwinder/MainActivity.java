package com.technolosea.sailwinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavyLocationManager navy_location_manager = new NavyLocationManager();
        if (navy_location_manager.checkGooglePlayServices(this))
        {
        }
    }
}
