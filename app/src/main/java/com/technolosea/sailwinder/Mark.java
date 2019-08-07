package com.technolosea.sailwinder;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

class Mark {
    public GeoPoint coordinate1;
    public GeoPoint coordinate2;
    public GateType gateType;
    public Long order;
    public Timestamp time;

}
