package com.org.visus.models;

public class ExceptionInfoRequest {
    private int appException_ID;
    private String appException_OnServerDate;
    private String appException_OnAppDate;
    private int appException_DeviceID;
    private int appException_InvID;
    private String appException_ActivityClassName;
    private String appException_MethodName;
    private String appException_ExceptionText;
    private String developerComment;
    private Object ifAnyApiException; // Can be adjusted based on actual data type
    private boolean isExceptionDataSaved;

    public int getAppException_ID() {
        return appException_ID;
    }

    public void setAppException_ID(int appException_ID) {
        this.appException_ID = appException_ID;
    }

    public String getAppException_OnServerDate() {
        return appException_OnServerDate;
    }

    public void setAppException_OnServerDate(String appException_OnServerDate) {
        this.appException_OnServerDate = appException_OnServerDate;
    }

    public String getAppException_OnAppDate() {
        return appException_OnAppDate;
    }

    public void setAppException_OnAppDate(String appException_OnAppDate) {
        this.appException_OnAppDate = appException_OnAppDate;
    }

    public int getAppException_DeviceID() {
        return appException_DeviceID;
    }

    public void setAppException_DeviceID(int appException_DeviceID) {
        this.appException_DeviceID = appException_DeviceID;
    }

    public int getAppException_InvID() {
        return appException_InvID;
    }

    public void setAppException_InvID(int appException_InvID) {
        this.appException_InvID = appException_InvID;
    }

    public String getAppException_ActivityClassName() {
        return appException_ActivityClassName;
    }

    public void setAppException_ActivityClassName(String appException_ActivityClassName) {
        this.appException_ActivityClassName = appException_ActivityClassName;
    }

    public String getAppException_MethodName() {
        return appException_MethodName;
    }

    public void setAppException_MethodName(String appException_MethodName) {
        this.appException_MethodName = appException_MethodName;
    }

    public String getAppException_ExceptionText() {
        return appException_ExceptionText;
    }

    public void setAppException_ExceptionText(String appException_ExceptionText) {
        this.appException_ExceptionText = appException_ExceptionText;
    }

    public String getDeveloperComment() {
        return developerComment;
    }

    public void setDeveloperComment(String developerComment) {
        this.developerComment = developerComment;
    }

    public Object getIfAnyApiException() {
        return ifAnyApiException;
    }

    public void setIfAnyApiException(Object ifAnyApiException) {
        this.ifAnyApiException = ifAnyApiException;
    }

    public boolean isExceptionDataSaved() {
        return isExceptionDataSaved;
    }

    public void setExceptionDataSaved(boolean exceptionDataSaved) {
        isExceptionDataSaved = exceptionDataSaved;
    }
}
