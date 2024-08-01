package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TotalCases implements Serializable {
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
    private List<TotalCasesData> data = new ArrayList<>();

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

    public List<TotalCasesData> getData() {
        return data;
    }

    public void setData(List<TotalCasesData> data) {
        this.data = data;
    }


    public static class TotalCasesData {
        @SerializedName("InvestigationUsedForID")
        @Expose
        private Integer investigationUsedForID;
        @SerializedName("InvestigationUsedForText")
        @Expose
        private String investigationUsedForText;
        @SerializedName("IsActiveInvestigationUsedFor")
        @Expose
        private Boolean isActiveInvestigationUsedFor;
        @SerializedName("InsvestigatorID")
        @Expose
        private Integer insvestigatorID;
        @SerializedName("InsvestigatorCode")
        @Expose
        private String insvestigatorCode;
        @SerializedName("InsvestigatorName")
        @Expose
        private String insvestigatorName;
        @SerializedName("TotalCases")
        @Expose
        private int totalCases;
        @SerializedName("TotalSubmittedCases")
        @Expose
        private int totalSubmittedCases;
        @SerializedName("TotalPendingCases")
        @Expose
        private int totalPendingCases;

        public Integer getInvestigationUsedForID() {
            return investigationUsedForID;
        }

        public void setInvestigationUsedForID(Integer investigationUsedForID) {
            this.investigationUsedForID = investigationUsedForID;
        }

        public String getInvestigationUsedForText() {
            return investigationUsedForText;
        }

        public void setInvestigationUsedForText(String investigationUsedForText) {
            this.investigationUsedForText = investigationUsedForText;
        }

        public Boolean getActiveInvestigationUsedFor() {
            return isActiveInvestigationUsedFor;
        }

        public void setActiveInvestigationUsedFor(Boolean activeInvestigationUsedFor) {
            isActiveInvestigationUsedFor = activeInvestigationUsedFor;
        }

        public Integer getInsvestigatorID() {
            return insvestigatorID;
        }

        public void setInsvestigatorID(Integer insvestigatorID) {
            this.insvestigatorID = insvestigatorID;
        }

        public String getInsvestigatorCode() {
            return insvestigatorCode;
        }

        public void setInsvestigatorCode(String insvestigatorCode) {
            this.insvestigatorCode = insvestigatorCode;
        }

        public String getInsvestigatorName() {
            return insvestigatorName;
        }

        public void setInsvestigatorName(String insvestigatorName) {
            this.insvestigatorName = insvestigatorName;
        }

        public int getTotalCases() {
            return totalCases;
        }

        public void setTotalCases(int totalCases) {
            this.totalCases = totalCases;
        }

        public int getTotalSubmittedCases() {
            return totalSubmittedCases;
        }

        public void setTotalSubmittedCases(int totalSubmittedCases) {
            this.totalSubmittedCases = totalSubmittedCases;
        }

        public int getTotalPendingCases() {
            return totalPendingCases;
        }

        public void setTotalPendingCases(int totalPendingCases) {
            this.totalPendingCases = totalPendingCases;
        }
    }

}
