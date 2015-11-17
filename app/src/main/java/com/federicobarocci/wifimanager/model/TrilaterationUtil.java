package com.federicobarocci.wifimanager.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by federico on 17/11/15.
 */
public class TrilaterationUtil {
    private static final double EARTH_R = 63710000;

    public static LatLng compute(LocationElement a, LocationElement b, LocationElement c) {
        double latA = a.getLocation().latitude;
        double latB = b.getLocation().latitude;
        double latC = c.getLocation().latitude;

        double longA = a.getLocation().longitude;
        double longB = b.getLocation().longitude;
        double longC = c.getLocation().longitude;

        double rA = a.getRadius();
        double rB = b.getRadius();
        double rC = c.getRadius();

        double xA = EARTH_R * Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(longA));
        double xB = EARTH_R * Math.cos(Math.toRadians(latB)) * Math.cos(Math.toRadians(longB));
        double xC = EARTH_R * Math.cos(Math.toRadians(latC)) * Math.cos(Math.toRadians(longC));

        double yA = EARTH_R * Math.cos(Math.toRadians(latA)) * Math.sin(Math.toRadians(longA));
        double yB = EARTH_R * Math.cos(Math.toRadians(latB)) * Math.sin(Math.toRadians(longB));
        double yC = EARTH_R * Math.cos(Math.toRadians(latC)) * Math.sin(Math.toRadians(longC));

        double zA = EARTH_R * Math.sin(Math.toRadians(latA));
        double zB = EARTH_R * Math.sin(Math.toRadians(latB));
        double zC = EARTH_R * Math.sin(Math.toRadians(latC));

        Vector3 P1 = Vector3.newVector(xA, yA, zA);
        Vector3 P2 = Vector3.newVector(xB, yB, zB);
        Vector3 P3 = Vector3.newVector(xC, yC, zC);

        Vector3 ex = Vector3.div(Vector3.diff(P2, P1), Vector3.norm(Vector3.diff(P2, P1)));
        double i = Vector3.dot(ex, Vector3.diff(P3, P1));
        Vector3 diff = Vector3.diff(Vector3.diff(P3, P1), Vector3.mul(i, ex));
        Vector3 ey = Vector3.div(diff, Vector3.norm(diff));
        Vector3 ez = Vector3.cross(ex, ey);
        double d = Vector3.norm(Vector3.diff(P2, P1));
        double j = Vector3.dot(ey, Vector3.diff(P3, P1));

        double x = (Math.pow(rA, 2) - Math.pow(rB, 2) + Math.pow(d, 2))/(2 * d);
        double y = ((Math.pow(rA, 2) - Math.pow(rC, 2) + Math.pow(i, 2) + Math.pow(j, 2))/(2 * j)) - ((i/j) * x);
        double z = Math.sqrt(Math.pow(rA, 2) - Math.pow(x, 2) - Math.pow(y, 2));

        Vector3 triPt = Vector3.add(Vector3.add(P1, Vector3.mul(x, ex)), Vector3.add(Vector3.mul(y, ey), Vector3.mul(z, ez)));

        double latitude = Math.toDegrees(Math.asin(triPt.getZ() / EARTH_R));
        double longitude = Math.toDegrees(Math.atan2(triPt.getY(), triPt.getX()));

        return new LatLng(latitude, longitude);
    }
}
