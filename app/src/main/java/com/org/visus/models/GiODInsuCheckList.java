package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GiODInsuCheckList implements Serializable {
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
    private List<GiODInsuCheckListData> data = new ArrayList<>();

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

    public List<GiODInsuCheckListData> getData() {
        return data;
    }

    public void setData(List<GiODInsuCheckListData> data) {
        this.data = data;
    }


    public class GiODInsuCheckListData {
        @SerializedName("InvestigatorODVisitDocument_ID")
        @Expose
        private Integer investigatorODVisitDocumentID;
        @SerializedName("InvestigatorODVisitTypeID")
        @Expose
        private Integer investigatorODVisitTypeID;
        @SerializedName("InvestigatorODVisitType_Text")
        @Expose
        private String investigatorODVisitTypeText;
        @SerializedName("P_InvestigatorODVisitDocumentID")
        @Expose
        private Integer pInvestigatorODVisitDocumentID;
        @SerializedName("P_InvestigatorODVisitType_Text")
        @Expose
        private String pInvestigatorODVisitTypeText;
        @SerializedName("InvestigatorODVisitDocument_Text")
        @Expose
        private String investigatorODVisitDocumentText;
        @SerializedName("IsActiveInvestigatorODVisitDocument")
        @Expose
        private Boolean isActiveInvestigatorODVisitDocument;
        @SerializedName("IsTextEntry")
        @Expose
        private Boolean isTextEntry;

        public Integer getInvestigatorODVisitDocumentID() {
            return investigatorODVisitDocumentID;
        }

        public void setInvestigatorODVisitDocumentID(Integer investigatorODVisitDocumentID) {
            this.investigatorODVisitDocumentID = investigatorODVisitDocumentID;
        }

        public Integer getInvestigatorODVisitTypeID() {
            return investigatorODVisitTypeID;
        }

        public void setInvestigatorODVisitTypeID(Integer investigatorODVisitTypeID) {
            this.investigatorODVisitTypeID = investigatorODVisitTypeID;
        }

        public String getInvestigatorODVisitTypeText() {
            return investigatorODVisitTypeText;
        }

        public void setInvestigatorODVisitTypeText(String investigatorODVisitTypeText) {
            this.investigatorODVisitTypeText = investigatorODVisitTypeText;
        }

        public Integer getpInvestigatorODVisitDocumentID() {
            return pInvestigatorODVisitDocumentID;
        }

        public void setpInvestigatorODVisitDocumentID(Integer pInvestigatorODVisitDocumentID) {
            this.pInvestigatorODVisitDocumentID = pInvestigatorODVisitDocumentID;
        }

        public String getpInvestigatorODVisitTypeText() {
            return pInvestigatorODVisitTypeText;
        }

        public void setpInvestigatorODVisitTypeText(String pInvestigatorODVisitTypeText) {
            this.pInvestigatorODVisitTypeText = pInvestigatorODVisitTypeText;
        }

        public String getInvestigatorODVisitDocumentText() {
            return investigatorODVisitDocumentText;
        }

        public void setInvestigatorODVisitDocumentText(String investigatorODVisitDocumentText) {
            this.investigatorODVisitDocumentText = investigatorODVisitDocumentText;
        }

        public Boolean getActiveInvestigatorODVisitDocument() {
            return isActiveInvestigatorODVisitDocument;
        }

        public void setActiveInvestigatorODVisitDocument(Boolean activeInvestigatorODVisitDocument) {
            isActiveInvestigatorODVisitDocument = activeInvestigatorODVisitDocument;
        }

        public Boolean getTextEntry() {
            return isTextEntry;
        }

        public void setTextEntry(Boolean textEntry) {
            isTextEntry = textEntry;
        }


    }
}
