package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SaveInvestigatorActionOnlyData implements Serializable {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private List<InvestigatorActionData> data = new ArrayList<>();

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<InvestigatorActionData> getData() {
        return data;
    }

    public void setData(List<InvestigatorActionData> data) {
        this.data = data;
    }

    public class InvestigatorActionData {

        private String InvestigatorActionDataServerID;
        private String ServiceTypeID;
        private String ServiceID;
        private String InvID;
        private String ActionID;
        private String Comments;
        private String Latitude;
        private String Longitude;
        private String CellAddress;
        private String ClientID;
        private String InvInsuranceRelID;
        private String IsSavedInvActionData;
        private String ExceptionIfAny;

        public String getInvestigatorActionDataServerID() {
            return InvestigatorActionDataServerID;
        }

        public void setInvestigatorActionDataServerID(String investigatorActionDataServerID) {
            InvestigatorActionDataServerID = investigatorActionDataServerID;
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

        public String getActionID() {
            return ActionID;
        }

        public void setActionID(String actionID) {
            ActionID = actionID;
        }

        public String getComments() {
            return Comments;
        }

        public void setComments(String comments) {
            Comments = comments;
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

        public String getClientID() {
            return ClientID;
        }

        public void setClientID(String clientID) {
            ClientID = clientID;
        }

        public String getInvInsuranceRelID() {
            return InvInsuranceRelID;
        }

        public void setInvInsuranceRelID(String invInsuranceRelID) {
            InvInsuranceRelID = invInsuranceRelID;
        }

        public String getSavedInvActionData() {
            return IsSavedInvActionData;
        }

        public void setSavedInvActionData(String savedInvActionData) {
            IsSavedInvActionData = savedInvActionData;
        }

        public String getExceptionIfAny() {
            return ExceptionIfAny;
        }

        public void setExceptionIfAny(String exceptionIfAny) {
            ExceptionIfAny = exceptionIfAny;
        }
    }
}