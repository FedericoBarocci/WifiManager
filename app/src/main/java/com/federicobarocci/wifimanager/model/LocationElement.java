package com.federicobarocci.wifimanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by federico on 17/11/15.
 */
public class LocationElement implements Parcelable {
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

    @Override
    public String toString() {
        return String.format("Latitude: %f\nLongitude: %f\nRadius: %f",
                getLocation().latitude, getLocation().longitude, getRadius());
    }

    /* Parcel section */
    protected LocationElement(Parcel in) {
        location = in.readParcelable(LatLng.class.getClassLoader());
        radius = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(location, flags);
        dest.writeDouble(radius);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LocationElement> CREATOR = new Parcelable.Creator<LocationElement>() {
        @Override
        public LocationElement createFromParcel(Parcel in) {
            return new LocationElement(in);
        }

        @Override
        public LocationElement[] newArray(int size) {
            return new LocationElement[size];
        }
    };
}
