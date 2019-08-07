package com.technolosea.sailwinder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DbLayer dbLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbLayer = new DbLayer();
        dbLayer.addTrackPoint("amit", new GeoPoint(0, 0), new Timestamp(new Date()));

        dbLayer.updateMarks();
    }
}
