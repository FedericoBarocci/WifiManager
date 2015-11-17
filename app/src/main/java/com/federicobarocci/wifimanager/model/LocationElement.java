package com.federicobarocci.wifimanager.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by federico on 17/11/15.
 */
public class LocationElement {
    private LatLng location;
    private double radius;

    public LocationElement(LatLng location, double radius) {
        this.location = location;
        this.radius = radius;
    }

    public LatLng getLocation() {
        return location;
    }

    public double getRadius() {
        return radius;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
