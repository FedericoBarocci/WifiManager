package com.federicobarocci.wifimanager.model;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.Pair;

/**
 * Created by federico on 11/11/15.
 */
public class WifiElement implements Parcelable {

    private final String bssid;

    private String ssid;
    private String capabilities;
    private int frequency;
    private int level;

    public static Pair<String, WifiElement> create(ScanResult scanResult) {
        return new Pair<String, WifiElement> (scanResult.BSSID, new WifiElement(scanResult));
    }

    public static Pair<String, WifiElement> create(String bbsid, String ssid, String capabilities, int frequency, int level) {
        return new Pair<String, WifiElement> (bbsid, new WifiElement(bbsid, ssid, capabilities, frequency, level));
    }

    public WifiElement(String bbsid, String ssid, String capabilities, int frequency, int level) {
        this.bssid = bbsid;
        this.ssid = ssid;
        this.capabilities = capabilities;
        this.frequency = frequency;
        this.level = level;
    }

    public WifiElement(ScanResult scanResult) {
        this.bssid = scanResult.BSSID;
        this.ssid = scanResult.SSID;
        this.capabilities = scanResult.capabilities;
        this.frequency = scanResult.frequency;
        this.level = scanResult.level;
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

    public int getFrequency() {
        return frequency;
    }

    public int getLevel() {
        return level;
    }

    public void setCapabilities(String capabilities) {
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

    public String getTitle(int position) {
        return String.format("%s (%s)", getSSID(), getBSSID());
    }

    public CharSequence getInfo(int position) {
        return getCapabilities() + "   " + getLevel() + " dBm";
    }

    protected WifiElement(Parcel in) {
        bssid = in.readString();
        ssid = in.readString();
        capabilities = in.readString();
        frequency = in.readInt();
        level = in.readInt();
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
