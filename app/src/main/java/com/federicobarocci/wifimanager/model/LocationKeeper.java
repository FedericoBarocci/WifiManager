package com.federicobarocci.wifimanager.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by federico on 17/11/15.
 */
public class LocationKeeper {
    private static final int NUM_NEAR = 3;

    private List<LocationElement> nearList = new ArrayList<>(NUM_NEAR);
    private LocationElement far;
    private LocationElement center;

    public LocationKeeper(LocationElement locationElement) {
        this.nearList.add(locationElement);
        this.far = locationElement;
        this.center = locationElement;
    }

    public void computeFar() {
        for (LocationElement element : nearList) {
            this.far = maxDistanceFromCenter(far, element);
        }
    }

    public LocationElement getFar() {
        return far;
    }

    public LocationElement getCenter() {
        return center;
    }

    public boolean addNear(LocationElement near) {
        nearList.add(near);

        if (nearList.size() > NUM_NEAR) {
            removeMaxRadius();
            return true;
        }

        return false;
    }

    private LocationElement maxDistanceFromCenter(LocationElement a, LocationElement b) {
        double maxA = SphericalUtil.computeDistanceBetween(center.getLocation(), a.getLocation());
        double maxB = SphericalUtil.computeDistanceBetween(center.getLocation(), b.getLocation());

        return maxA > maxB ? a : b;
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
        center.setRadius(SphericalUtil.computeDistanceBetween(center.getLocation(), far.getLocation()));
    }

    private void computeCenter(LocationElement a, LocationElement b, LocationElement c) {
        center.setLocation(TrilaterationUtil.compute(a, b, c));
        computeFar();
        center.setRadius(SphericalUtil.computeDistanceBetween(center.getLocation(), far.getLocation()));
    }

    private void removeMaxRadius() {
        double maxRadius = nearList.get(0).getRadius();
        int maxRadiusElement = 0;

        for (int i = 1; i < nearList.size(); i++) {
            if (nearList.get(1).getRadius() > maxRadius) {
                maxRadius = nearList.get(1).getRadius();
                maxRadiusElement = i;
            }
        }

        nearList.remove(maxRadiusElement);
    }
}
