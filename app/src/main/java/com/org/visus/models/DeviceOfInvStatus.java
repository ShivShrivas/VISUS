package com.org.visus.models;

import java.io.Serializable;

public class DeviceOfInvStatus implements Serializable {
    public int DeviceID;
    public int InvestigatorID;
    public Boolean DeviceStatus;
    public String DeviceStatusOnDate;

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int deviceID) {
        DeviceID = deviceID;
    }

    public int getInvestigatorID() {
        return InvestigatorID;
    }

    public void setInvestigatorID(int investigatorID) {
        InvestigatorID = investigatorID;
    }

    public Boolean getDeviceStatus() {
        return DeviceStatus;
    }

    public void setDeviceStatus(Boolean deviceStatus) {
        DeviceStatus = deviceStatus;
    }

    public String getDeviceStatusOnDate() {
        return DeviceStatusOnDate;
    }

    public void setDeviceStatusOnDate(String deviceStatusOnDate) {
        DeviceStatusOnDate = deviceStatusOnDate;
    }
}
