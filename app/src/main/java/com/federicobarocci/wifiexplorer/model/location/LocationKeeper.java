package com.federicobarocci.wifiexplorer.model.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Federico
 */
public class LocationKeeper implements Parcelable {
    private static final int NUM_NEAR = 3;

    private List<LocationElement> nearList = new ArrayList<>(NUM_NEAR);
    private LocationElement far;
    private LocationElement center;

    public LocationKeeper(LocationElement locationElement) {
        this.nearList.add(locationElement);
        this.far = locationElement;
        this.center = locationElement;
    }

    public LocationElement getCenter() {
        return center;
    }

    public void addNear(LocationElement near) {
        nearList.add(near);

        if (nearList.size() > NUM_NEAR) {
            removeSmallestArea();
            //removeMaxRadius();
        }

        computeCenter();
    }

    private void removeSmallestArea() {
        double a[] = new double[4];
        a[0] = SphericalUtil.computeArea(testList(1,2,3)); //remove 0
        a[1] = SphericalUtil.computeArea(testList(0,2,3)); //remove 1
        a[2] = SphericalUtil.computeArea(testList(0,1,3)); //remove 2
        a[3] = SphericalUtil.computeArea(testList(0,1,2)); //remove 3

        int minIndex = 0;
        double minValue = a[0];
        for (int i = 1; i < 4; i++) {
            if (a[i] < minValue) {
                minIndex = i;
                minValue = a[i];
            }
        }

        nearList.remove(minIndex);
    }

    private List<LatLng> testList(int a, int b, int c) {
        List<LatLng> list = new ArrayList<>(NUM_NEAR);
        list.add(nearList.get(a).getLocation());
        list.add(nearList.get(b).getLocation());
        list.add(nearList.get(c).getLocation());
        return list;
    }

    private void computeCenter() {
        switch (nearList.size()) {
            case 1:
                computeCenter(nearList.get(0));
                break;

            case 2:
                computeCenter(nearList.get(0), nearList.get(1));
                break;

            case 3:
                computeCenter(nearList.get(0), nearList.get(1), nearList.get(2));
                break;
        }
    }

    private void computeCenter(LocationElement a) {
        center.setLocation(a.getLocation());
        center.setRadius(far.getRadius());
    }

    private void computeCenter(LocationElement a, LocationElement b) {
        center.setLocation(SphericalUtil.interpolate(a.getLocation(), b.getLocation(), 0.5));
        computeFar();
        center.setRadius(getMaxRadius());
    }

    private void computeCenter(LocationElement a, LocationElement b, LocationElement c) {
        center.setLocation(avgLocation(a.getLocation(), b.getLocation(), c.getLocation()));
        computeFar();
        center.setRadius(getMaxRadius());
    }

    private LatLng avgLocation(LatLng a, LatLng b, LatLng c) {
        final double lat = (a.latitude + b.latitude + c.latitude) / 3;
        final double lng = (a.longitude + b.longitude + c.longitude) / 3;
        return new LatLng(lat, lng);
    }

    private double getMaxRadius() {
        double maxRadius = Math.max(center.getRadius(), far.getRadius());

        for (LocationElement locationElement : nearList) {
            if (locationElement.getRadius() > maxRadius) {
                maxRadius = locationElement.getRadius();
            }
        }

        return maxRadius;
    }

    private void computeFar() {
        for (LocationElement element : nearList) {
            this.far = maxDistanceFromCenter(far, element);
        }
    }

    private LocationElement maxDistanceFromCenter(LocationElement a, LocationElement b) {
        double maxA = SphericalUtil.computeDistanceBetween(center.getLocation(), a.getLocation());
        double maxB = SphericalUtil.computeDistanceBetween(center.getLocation(), b.getLocation());

        return maxA > maxB ? a : b;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("[ Near locations ]\n\n");
        for (LocationElement element : nearList) {
            s.append(element.toString()).append("\n\n");
        }

        s.append("\n[ Far location ]\n\n");
        s.append(far.toString()).append("\n");

        s.append("\n\n[ Center location ]\n\n");
        s.append(center.toString());

        return s.toString();
    }

    /* Parcel section */
    protected LocationKeeper(Parcel in) {
        nearList = new ArrayList<>();
        in.readList(nearList, null);
        far = in.readParcelable(LocationElement.class.getClassLoader());
        center = in.readParcelable(LocationElement.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(nearList);
        dest.writeParcelable(far, flags);
        dest.writeParcelable(center, flags);
    }

    public static final Parcelable.Creator<LocationKeeper> CREATOR = new Parcelable.Creator<LocationKeeper>() {
        @Override
        public LocationKeeper createFromParcel(Parcel in) {
            return new LocationKeeper(in);
        }

        @Override
        public LocationKeeper[] newArray(int size) {
            return new LocationKeeper[size];
        }
    };
}
