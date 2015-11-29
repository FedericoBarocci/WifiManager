package com.federicobarocci.wifiexplorer.model.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.federicobarocci.wifiexplorer.model.location.util.TrilaterationUtil;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by federico on 17/11/15.
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
            removeMaxRadius();
        }

        computeCenter();
    }

    private void removeMaxRadius() {
        double maxRadius = 0;
        int indexMaxRadius = 0;

        for (int i = 0; i < nearList.size(); i++) {
            if (nearList.get(i).getRadius() > maxRadius) {
                maxRadius = nearList.get(i).getRadius();
                indexMaxRadius = i;
            }
        }

        nearList.remove(indexMaxRadius);
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
        center.setLocation(nearList.get(0).getLocation());
        center.setRadius(far.getRadius());
    }

    private void computeCenter(LocationElement a, LocationElement b) {
        center.setLocation(SphericalUtil.interpolate(a.getLocation(), b.getLocation(), 0.5));
        computeFar();
        center.setRadius(getMaxRadius()); /*SphericalUtil.computeDistanceBetween(center.getLocation(), far.getLocation()));*/
    }

    private void computeCenter(LocationElement a, LocationElement b, LocationElement c) {
        center.setLocation(TrilaterationUtil.compute(a, b, c));
        computeFar();
        center.setRadius(getMaxRadius()); /*SphericalUtil.computeDistanceBetween(center.getLocation(), far.getLocation()));*/
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
            s.append(element.toString() + "\n\n");
        }

        s.append("\n[ Far location ]\n\n");
        s.append(far.toString() + "\n");

        s.append("\n\n[ Center location ]\n\n");
        s.append(center.toString());

        return s.toString();
    }

    /* Parcel section */
    protected LocationKeeper(Parcel in) {
        nearList = new ArrayList<LocationElement>();
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

    @SuppressWarnings("unused")
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
