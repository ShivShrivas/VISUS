package com.org.visus.models;

import java.io.File;
import java.io.Serializable;

public class PostInvestigatorActionData implements Serializable {
    private String Token;
    private String OriginalFileName;
    private String ServiceTypeID;
    private String ServiceID;
    private String InvID;
    private String Comments;
    private String ActionID;
    private String Latitude;
    private String Longitude;
    private String CellAddress;
    private String ClientID;
    private String InvInsuranceRelID_SAVING;
    private File OriginalFile;

    public String getInvInsuranceRelID_SAVING() {
        return InvInsuranceRelID_SAVING;
    }

    public void setInvInsuranceRelID_SAVING(String invInsuranceRelID_SAVING) {
        InvInsuranceRelID_SAVING = invInsuranceRelID_SAVING;
    }


    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }


    private String isSyncedRequreActionsData;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getOriginalFileName() {
        return OriginalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        OriginalFileName = originalFileName;
    }

    public String getServiceTypeID() {
        return ServiceTypeID;
    }

    public void setServiceTypeID(String serviceTypeID) {
        ServiceTypeID = serviceTypeID;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getInvID() {
        return InvID;
    }

    public void setInvID(String invID) {
        InvID = invID;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getActionID() {
        return ActionID;
    }

    public void setActionID(String actionID) {
        ActionID = actionID;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getCellAddress() {
        return CellAddress;
    }

    public void setCellAddress(String cellAddress) {
        CellAddress = cellAddress;
    }

    public String getIsSyncedRequreActionsData() {
        return isSyncedRequreActionsData;
    }

    public void setIsSyncedRequreActionsData(String isSyncedRequreActionsData) {
        this.isSyncedRequreActionsData = isSyncedRequreActionsData;
    }

    public File getOriginalFile() {
        return OriginalFile;
    }

    public void setOriginalFile(File originalFile) {
        OriginalFile = originalFile;
    }
}
