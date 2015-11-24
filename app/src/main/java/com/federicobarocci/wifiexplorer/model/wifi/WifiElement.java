package com.federicobarocci.wifiexplorer.model.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by federico on 11/11/15.
 */
public class WifiElement implements Parcelable {
    public static final int RSSI_LEVEL = 4;
    private static final String UNKNOW = "Unknow";

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

    /* Constructors */
    public WifiElement(String bbsid, String ssid, String capabilities, int frequency, int level) {
        this.bssid = bbsid;
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

    /* Standard Getters */
    public String getBSSID() {
        return bssid;
    }

    public String getSSID() {
        return ssid;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isLineOfSight() {
        return lineOfSight;
    }

    public int getLevel() {
        return level;
    }

    public String getFrequencyString() {
        return isLineOfSight() ? String.format("%d MHz", frequency) : UNKNOW;
    }

    public String getLevelString() {
        return isLineOfSight() ? String.format("%d dBm", level) : UNKNOW;
    }

    public String getSignalLevelString() {
        return isLineOfSight() ?
                String.format("%d/%d", getSignalLevel() + 1, RSSI_LEVEL) :
                String.format("0/%d", RSSI_LEVEL);
    }

    public String getDistanceString() {
        return isLineOfSight() ? String.format("%f m", calculateDistance()) : UNKNOW;
    }

    /* Standard Setters */
    /*public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public CharSequence getInfo() {
        return String.format("%s   %d dBm %d/%d", getCapabilities(), getLevel(), getSignalLevel(), RSSI_LEVEL);
    }*/

    /* Convenience Getters */
    public int getSignalLevel() {
        return WifiManager.calculateSignalLevel(getLevel(), RSSI_LEVEL);
    }

    public boolean isSecure() {
        return getCapabilities().contains("WPA");
    }

    public double calculateDistance(double levelInDb, double freqInMHz) {
        /*27.55*/
        double exp = (10 - (20 * Math.log10(freqInMHz)) + Math.abs(levelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    public double calculateDistance() {
        return calculateDistance(getLevel(), getFrequency());
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

    @SuppressWarnings("unused")
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
