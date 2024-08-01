package com.org.visus.models;

import java.io.Serializable;

public class DeviceInvLocation implements Serializable {
    private int DeviceLocation_ID;
    private int DeviceLocation_DeviceID;
    private int DeviceLocation_InvID;
    private String DeviceLocation_Latitude;
    private String DeviceLocation_Longitude;
    private String DeviceLocation_GeoAddress;
    private String DeviceLocation_SaveOnDate;
    private Boolean IsLatestDeviceLocation;
    private Boolean IsSavedDeviceLocation;
    private String ExceptionIfAny;

    public int getDeviceLocation_ID() {
        return DeviceLocation_ID;
    }

    public void setDeviceLocation_ID(int deviceLocation_ID) {
        DeviceLocation_ID = deviceLocation_ID;
    }

    public int getDeviceLocation_DeviceID() {
        return DeviceLocation_DeviceID;
    }

    public void setDeviceLocation_DeviceID(int deviceLocation_DeviceID) {
        DeviceLocation_DeviceID = deviceLocation_DeviceID;
    }

    public int getDeviceLocation_InvID() {
        return DeviceLocation_InvID;
    }

    public void setDeviceLocation_InvID(int deviceLocation_InvID) {
        DeviceLocation_InvID = deviceLocation_InvID;
    }

    public String getDeviceLocation_Latitude() {
        return DeviceLocation_Latitude;
    }

    public void setDeviceLocation_Latitude(String deviceLocation_Latitude) {
        DeviceLocation_Latitude = deviceLocation_Latitude;
    }

    public String getDeviceLocation_Longitude() {
        return DeviceLocation_Longitude;
    }

    public void setDeviceLocation_Longitude(String deviceLocation_Longitude) {
        DeviceLocation_Longitude = deviceLocation_Longitude;
    }

    public String getDeviceLocation_GeoAddress() {
        return DeviceLocation_GeoAddress;
    }

    public void setDeviceLocation_GeoAddress(String deviceLocation_GeoAddress) {
        DeviceLocation_GeoAddress = deviceLocation_GeoAddress;
    }

    public String getDeviceLocation_SaveOnDate() {
        return DeviceLocation_SaveOnDate;
    }

    public void setDeviceLocation_SaveOnDate(String deviceLocation_SaveOnDate) {
        DeviceLocation_SaveOnDate = deviceLocation_SaveOnDate;
    }

    public Boolean getIsLatestDeviceLocation() {
        return IsLatestDeviceLocation;
    }

    public void setIsLatestDeviceLocation(Boolean latestDeviceLocation) {
        IsLatestDeviceLocation = latestDeviceLocation;
    }

    public Boolean getIsSavedDeviceLocation() {
        return IsSavedDeviceLocation;
    }

    public void setIsSavedDeviceLocation(Boolean savedDeviceLocation) {
        IsSavedDeviceLocation = savedDeviceLocation;
    }

    public String getExceptionIfAny() {
        return ExceptionIfAny;
    }

    public void setExceptionIfAny(String exceptionIfAny) {
        ExceptionIfAny = exceptionIfAny;
    }
}
