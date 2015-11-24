package com.federicobarocci.wifiexplorer.model.location.util;

/**
 * Created by federico on 17/11/15.
 */
public class Vector3 {
    private final double x;
    private final double y;
    private final double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static Vector3 diff(Vector3 a, Vector3 b) {
        return new Vector3(
                a.x - b.x,
                a.y - b.y,
                a.z - b.z
        );
    }

    public static double norm(Vector3 a) {
        return Math.sqrt(dot(a, a));
    }

    public static double dot(Vector3 a, Vector3 b) {
        return (a.x * b.x) + (a.y * b.y) + (a.z * b.z);
    }

    public static Vector3 div(Vector3 a, double value) {
        return new Vector3(
                a.x/value,
                a.y/value,
                a.z/value
        );
    }

    public static Vector3 mul(double value, Vector3 a) {
        return new Vector3(
                a.x * value,
                a.y * value,
                a.z * value
        );
    }

    public static Vector3 cross(Vector3 a, Vector3 b) {
        return new Vector3(
                (a.y * b.z) - (a.z * b.y),
                (a.z * b.x) - (a.x * b.z),
                (a.x * b.y) - (a.y * b.x)
        );
    }

    public static Vector3 add(Vector3 a, Vector3 b) {
        return new Vector3(
                a.x + b.x,
                a.y + b.y,
                a.z + b.z
        );
    }
}
