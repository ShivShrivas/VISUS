package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

    public DeviceInfo() {
    }

    public DeviceInfo(String RELEASE, String DEVICE, String MODEL, String PRODUCT, String BRAND, String DISPLAY, String CPU_ABI, String CPU_ABI2, String UNKNOWN, String HARDWARE, String ID, String MANUFACTURER, String SERIAL, String USER, String HOST, String DEVICE_ID, String DEVICE_ServerID) {
        this.RELEASE = RELEASE;
        this.DEVICE = DEVICE;
        this.MODEL = MODEL;
        this.PRODUCT = PRODUCT;
        this.BRAND = BRAND;
        this.DISPLAY = DISPLAY;
        this.CPU_ABI = CPU_ABI;
        this.CPU_ABI2 = CPU_ABI2;
        this.UNKNOWN = UNKNOWN;
        this.HARDWARE = HARDWARE;
        this.ID = ID;
        this.MANUFACTURER = MANUFACTURER;
        this.SERIAL = SERIAL;
        this.USER = USER;
        this.HOST = HOST;
        this.DEVICE_ID = DEVICE_ID;
        this.DEVICE_ServerID = DEVICE_ServerID;
    }

    @SerializedName("RELEASE")
    @Expose
    private String RELEASE;
    @SerializedName("DEVICE")
    @Expose
    private String DEVICE;
    @SerializedName("MODEL")
    @Expose
    private String MODEL;
    @SerializedName("PRODUCT")
    @Expose
    private String PRODUCT;
    @SerializedName("BRAND")
    @Expose
    private String BRAND;
    @SerializedName("DISPLAY")
    @Expose
    private String DISPLAY;
    @SerializedName("CPU_ABI")
    @Expose
    private String CPU_ABI;
    @SerializedName("CPU_ABI2")
    @Expose
    private String CPU_ABI2;
    @SerializedName("UNKNOWN")
    @Expose
    private String UNKNOWN;
    @SerializedName("HARDWARE")
    @Expose
    private String HARDWARE;
    @SerializedName("ID")
    @Expose
    private String ID;
    @SerializedName("MANUFACTURER")
    @Expose
    private String MANUFACTURER;

    @SerializedName("SERIAL")
    @Expose
    private String SERIAL;

    @SerializedName("USER")
    @Expose
    private String USER;
    @SerializedName("HOST")
    @Expose
    private String HOST;

    @SerializedName("DEVICE_ID")
    @Expose
    private String DEVICE_ID;
    @SerializedName("DEVICE_ClientID")
    @Expose
    private Integer DEVICE_ClientID;
    @SerializedName("DEVICE_ServerID")
    @Expose
    private String DEVICE_ServerID;
    @SerializedName("IsDeviceVerified")
    @Expose
    private Boolean IsDeviceVerified;
    @SerializedName("DeviceVerifiedOn")
    @Expose
    private String DeviceVerifiedOn;
    @SerializedName("VerifiedByUserID")
    @Expose
    private Integer VerifiedByUserID;
    @SerializedName("VerifiedByUserName")
    @Expose
    private String VerifiedByUserName;

    public Boolean getDeviceVerified() {
        return IsDeviceVerified;
    }

    public void setDeviceVerified(Boolean deviceVerified) {
        IsDeviceVerified = deviceVerified;
    }

    public String getDeviceVerifiedOn() {
        return DeviceVerifiedOn;
    }

    public void setDeviceVerifiedOn(String deviceVerifiedOn) {
        DeviceVerifiedOn = deviceVerifiedOn;
    }

    public Integer getVerifiedByUserID() {
        return VerifiedByUserID;
    }

    public void setVerifiedByUserID(Integer verifiedByUserID) {
        VerifiedByUserID = verifiedByUserID;
    }

    public String getVerifiedByUserName() {
        return VerifiedByUserName;
    }

    public void setVerifiedByUserName(String verifiedByUserName) {
        VerifiedByUserName = verifiedByUserName;
    }


    public String getRELEASE() {
        return RELEASE;
    }

    public void setRELEASE(String RELEASE) {
        this.RELEASE = RELEASE;
    }

    public String getDEVICE() {
        return DEVICE;
    }

    public void setDEVICE(String DEVICE) {
        this.DEVICE = DEVICE;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getPRODUCT() {
        return PRODUCT;
    }

    public void setPRODUCT(String PRODUCT) {
        this.PRODUCT = PRODUCT;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getDISPLAY() {
        return DISPLAY;
    }

    public void setDISPLAY(String DISPLAY) {
        this.DISPLAY = DISPLAY;
    }

    public String getCPU_ABI() {
        return CPU_ABI;
    }

    public Integer getDEVICE_ClientID() {
        return DEVICE_ClientID;
    }

    public void setDEVICE_ClientID(Integer DEVICE_ClientID) {
        this.DEVICE_ClientID = DEVICE_ClientID;
    }

    public void setCPU_ABI(String CPU_ABI) {
        this.CPU_ABI = CPU_ABI;
    }

    public String getCPU_ABI2() {
        return CPU_ABI2;
    }

    public void setCPU_ABI2(String CPU_ABI2) {
        this.CPU_ABI2 = CPU_ABI2;
    }

    public String getUNKNOWN() {
        return UNKNOWN;
    }

    public void setUNKNOWN(String UNKNOWN) {
        this.UNKNOWN = UNKNOWN;
    }

    public String getHARDWARE() {
        return HARDWARE;
    }

    public void setHARDWARE(String HARDWARE) {
        this.HARDWARE = HARDWARE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMANUFACTURER() {
        return MANUFACTURER;
    }

    public void setMANUFACTURER(String MANUFACTURER) {
        this.MANUFACTURER = MANUFACTURER;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public String getDEVICE_ServerID() {
        return DEVICE_ServerID;
    }

    public void setDEVICE_ServerID(String DEVICE_ServerID) {
        this.DEVICE_ServerID = DEVICE_ServerID;
    }
}
