package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GiPAInsuCheckList implements Serializable {
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
    private List<GiPAInsuCheckListData> data = new ArrayList<>();

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

    public List<GiPAInsuCheckListData> getData() {
        return data;
    }

    public void setData(List<GiPAInsuCheckListData> data) {
        this.data = data;
    }


    public class GiPAInsuCheckListData implements Serializable {
        @SerializedName("InvGiPACheckListHead_ID")
        @Expose
        private Integer invGiPACheckListHeadID;
        @SerializedName("InvGiPACheckListTypeID")
        @Expose
        private Integer invGiPACheckListTypeID;
        @SerializedName("GiPACheckListType_EText")
        @Expose
        private String giPACheckListTypeEText;
        @SerializedName("InvGiPACheckListHead_Text")
        @Expose
        private String invGiPACheckListHeadText;
        @SerializedName("IsActiveInvGiPACheckListHead")
        @Expose
        private Boolean isActiveInvGiPACheckListHead;
        @SerializedName("GiPA_InvInsuranceRelID")
        @Expose
        private String GiPA_InvInsuranceRelID;

        public String getGiPA_InvInsuranceRelID() {
            return GiPA_InvInsuranceRelID;
        }

        public void setGiPA_InvInsuranceRelID(String giPA_InvInsuranceRelID) {
            GiPA_InvInsuranceRelID = giPA_InvInsuranceRelID;
        }


        public Integer getInvGiPACheckListHeadID() {
            return invGiPACheckListHeadID;
        }

        public void setInvGiPACheckListHeadID(Integer invGiPACheckListHeadID) {
            this.invGiPACheckListHeadID = invGiPACheckListHeadID;
        }

        public Integer getInvGiPACheckListTypeID() {
            return invGiPACheckListTypeID;
        }

        public void setInvGiPACheckListTypeID(Integer invGiPACheckListTypeID) {
            this.invGiPACheckListTypeID = invGiPACheckListTypeID;
        }

        public String getGiPACheckListTypeEText() {
            return giPACheckListTypeEText;
        }

        public void setGiPACheckListTypeEText(String giPACheckListTypeEText) {
            this.giPACheckListTypeEText = giPACheckListTypeEText;
        }

        public String getInvGiPACheckListHeadText() {
            return invGiPACheckListHeadText;
        }

        public void setInvGiPACheckListHeadText(String invGiPACheckListHeadText) {
            this.invGiPACheckListHeadText = invGiPACheckListHeadText;
        }

        public Boolean getActiveInvGiPACheckListHead() {
            return isActiveInvGiPACheckListHead;
        }

        public void setActiveInvGiPACheckListHead(Boolean activeInvGiPACheckListHead) {
            isActiveInvGiPACheckListHead = activeInvGiPACheckListHead;
        }


    }
}
