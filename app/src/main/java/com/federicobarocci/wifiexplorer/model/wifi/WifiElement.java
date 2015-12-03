package com.federicobarocci.wifiexplorer.model.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Parcel;
import android.os.Parcelable;

import com.federicobarocci.wifiexplorer.ui.util.WifiColorGMap;

/**
 * Created by Federico
 */
public class WifiElement implements Parcelable {
    public static final int RSSI_LEVEL = 4;
    private static final String UNKNOWN = "Unknown";

    private final String bssid;

    private String ssid;
    private String capabilities;
    private int frequency;
    private int level;
    private boolean lineOfSight;

    public WifiElement(String bssid, String ssid, String capabilities) {
        this.bssid = bssid;
        this.ssid = ssid;
        this.capabilities = capabilities;
        this.lineOfSight = false;
    }

    public WifiElement(String bssid, String ssid, String capabilities, int frequency, int level) {
        this.bssid = bssid;
        this.ssid = ssid;
        this.capabilities = capabilities;
        this.frequency = frequency;
        this.level = level;
        this.lineOfSight = true;
    }

    public WifiElement(ScanResult scanResult) {
        this.bssid = scanResult.BSSID;
        this.ssid = scanResult.SSID;
        this.capabilities = scanResult.capabilities;
        this.frequency = scanResult.frequency;
        this.level = scanResult.level;
        this.lineOfSight = true;
    }

    public void invalidate() {
        lineOfSight = false;
    }

    public String getBSSID() {
        return bssid;
    }

    public String getSSID() {
        return ssid;
    }

    public String getCapabilities() {
        return capabilities;
    }


    public boolean isLineOfSight() {
        return lineOfSight;
    }

    public int getdBm() {
        return lineOfSight ? level : Integer.MIN_VALUE;
    }

    public String getFrequencyString() {
        return lineOfSight ? String.format("%d MHz", frequency) : UNKNOWN;
    }

    public String getLevelString() {
        return lineOfSight ? String.format("%d dBm", level) : UNKNOWN;
    }

    public String getSignalLevelString() {
        return lineOfSight ?
                String.format("%d/%d", getSignalLevel() + 1, RSSI_LEVEL) :
                String.format("0/%d", RSSI_LEVEL);
    }

    public String getDistanceString() {
        return lineOfSight ? String.format("%f m", calculateDistance()) : UNKNOWN;
    }

    public int getSignalLevel() {
        return WifiManager.calculateSignalLevel(level, RSSI_LEVEL);
    }

    public boolean isSecure() {
        return capabilities.contains("WPA");
    }

    private double calculateDistance(double levelInDb, double freqInMHz) {
        final double const_FSPL = 10.0; /*27.55*/
        double exp = (const_FSPL - (20 * Math.log10(freqInMHz)) + Math.abs(levelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    public double calculateDistance() {
        return calculateDistance(level, frequency);
    }

    public int getBoldColor() {
        return isSecure() ? WifiColorGMap.getRedBold() : WifiColorGMap.getGreenBold();
    }

    public int getLightColor() {
        return isSecure() ? WifiColorGMap.getRedLight() : WifiColorGMap.getGreenLight();
    }

    /* Parcel section */
    protected WifiElement(Parcel in) {
        bssid = in.readString();
        ssid = in.readString();
        capabilities = in.readString();
        frequency = in.readInt();
        level = in.readInt();
        lineOfSight = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bssid);
        dest.writeString(ssid);
        dest.writeString(capabilities);
        dest.writeInt(frequency);
        dest.writeInt(level);
        dest.writeByte((byte) (lineOfSight ? 1 : 0));
    }

    public static final Parcelable.Creator<WifiElement> CREATOR = new Parcelable.Creator<WifiElement>() {
        @Override
        public WifiElement createFromParcel(Parcel in) {
            return new WifiElement(in);
        }

        @Override
        public WifiElement[] newArray(int size) {
            return new WifiElement[size];
        }
    };
}
