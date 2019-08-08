package com.technolosea.sailwinder;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicLine;
import net.sf.geographiclib.GeodesicMask;

public class GeoCalc {
    private static Geodesic geod = Geodesic.WGS84;
    static public Double getDistance(Double lat1, Double lon1, Double lat2, Double lon2){
        GeodesicLine line = geod.InverseLine(lat1,lon1,lat2,lon2, GeodesicMask.DISTANCE_IN | GeodesicMask.LATITUDE | GeodesicMask. LONGITUDE);
        return line.Distance();
    }
    static public Double getAzimuth(Double lat1, Double lon1, Double lat2, Double lon2){
        GeodesicLine line = geod.InverseLine(lat1,lon1,lat2,lon2, GeodesicMask.DISTANCE_IN | GeodesicMask.LATITUDE | GeodesicMask. LONGITUDE);
        return line.Azimuth();
    }
    
    private double calcSlope(GeoPoint p1, GeoPoint p2)
    {
        return (p1.getLatitude()-p2.getLatitude())/(p1.getLongitude()-p2.getLongitude());
    }

    private boolean participantHasFinished(GeoPoint endPointLeft, GeoPoint endPointRight, GeoPoint participantPoint)
    {
        return calcSlope(endPointLeft, participantPoint) == calcSlope(participantPoint, endPointRight);
    }
}
