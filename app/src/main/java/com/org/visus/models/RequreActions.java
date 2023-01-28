package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequreActions implements Serializable {
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
    private List<RequreActionsData> data = new ArrayList<>();

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

    public List<RequreActionsData> getData() {
        return data;
    }

    public void setData(List<RequreActionsData> data) {
        this.data = data;
    }


    public class RequreActionsData implements Serializable {
        @SerializedName("InvestigatorReqActivity_ID")
        @Expose
        private Integer investigatorReqActivityID;
        @SerializedName("InsuranceClaimTypeID")
        @Expose
        private Integer insuranceClaimTypeID;
        @SerializedName("InsuranceClaimTypeText")
        @Expose
        private String insuranceClaimTypeText;
        @SerializedName("InvestigatorReqActivity_Text")
        @Expose
        private String investigatorReqActivityText;
        @SerializedName("IsSelfieRequired")
        @Expose
        private Boolean isSelfieRequired;
        @SerializedName("IsCompulsory")
        @Expose
        private Boolean isCompulsory;
        @SerializedName("IsPhotoRequired")
        @Expose
        private Boolean isPhotoRequired;
        @SerializedName("MinimumPhotoRequired")
        @Expose
        private Integer minimumPhotoRequired;
        @SerializedName("MaximumPhotoRequired")
        @Expose
        private Integer maximumPhotoRequired;
        @SerializedName("IsVedioFileRequired")
        @Expose
        private Boolean isVedioFileRequired;
        @SerializedName("MinumumVedioFileRequired")
        @Expose
        private Integer minumumVedioFileRequired;
        @SerializedName("MaximumVedioFileRequired")
        @Expose
        private Integer maximumVedioFileRequired;
        @SerializedName("MaxmumSizeOfVedioFileInMB")
        @Expose
        private Integer maxmumSizeOfVedioFileInMB;
        @SerializedName("IsActiveInvestigatorReqActivity")
        @Expose
        private Boolean isActiveInvestigatorReqActivity;
        @SerializedName("EntryByUserID")
        @Expose
        private Integer entryByUserID;
        @SerializedName("EntryOnDate")
        @Expose
        private String entryOnDate;
        @SerializedName("ModifiedByUserID")
        @Expose
        private Integer modifiedByUserID;
        @SerializedName("ModifiedOnDate")
        @Expose
        private String modifiedOnDate;

        public Integer getInvestigatorReqActivityID() {
            return investigatorReqActivityID;
        }

        public void setInvestigatorReqActivityID(Integer investigatorReqActivityID) {
            this.investigatorReqActivityID = investigatorReqActivityID;
        }

        public Integer getInsuranceClaimTypeID() {
            return insuranceClaimTypeID;
        }

        public void setInsuranceClaimTypeID(Integer insuranceClaimTypeID) {
            this.insuranceClaimTypeID = insuranceClaimTypeID;
        }

        public String getInsuranceClaimTypeText() {
            return insuranceClaimTypeText;
        }

        public void setInsuranceClaimTypeText(String insuranceClaimTypeText) {
            this.insuranceClaimTypeText = insuranceClaimTypeText;
        }

        public String getInvestigatorReqActivityText() {
            return investigatorReqActivityText;
        }

        public void setInvestigatorReqActivityText(String investigatorReqActivityText) {
            this.investigatorReqActivityText = investigatorReqActivityText;
        }

        public Boolean getSelfieRequired() {
            return isSelfieRequired;
        }

        public void setSelfieRequired(Boolean selfieRequired) {
            isSelfieRequired = selfieRequired;
        }

        public Boolean getCompulsory() {
            return isCompulsory;
        }

        public void setCompulsory(Boolean compulsory) {
            isCompulsory = compulsory;
        }

        public Boolean getPhotoRequired() {
            return isPhotoRequired;
        }

        public void setPhotoRequired(Boolean photoRequired) {
            isPhotoRequired = photoRequired;
        }

        public Integer getMinimumPhotoRequired() {
            return minimumPhotoRequired;
        }

        public void setMinimumPhotoRequired(Integer minimumPhotoRequired) {
            this.minimumPhotoRequired = minimumPhotoRequired;
        }

        public Integer getMaximumPhotoRequired() {
            return maximumPhotoRequired;
        }

        public void setMaximumPhotoRequired(Integer maximumPhotoRequired) {
            this.maximumPhotoRequired = maximumPhotoRequired;
        }

        public Boolean getVedioFileRequired() {
            return isVedioFileRequired;
        }

        public void setVedioFileRequired(Boolean vedioFileRequired) {
            isVedioFileRequired = vedioFileRequired;
        }

        public Integer getMinumumVedioFileRequired() {
            return minumumVedioFileRequired;
        }

        public void setMinumumVedioFileRequired(Integer minumumVedioFileRequired) {
            this.minumumVedioFileRequired = minumumVedioFileRequired;
        }

        public Integer getMaximumVedioFileRequired() {
            return maximumVedioFileRequired;
        }

        public void setMaximumVedioFileRequired(Integer maximumVedioFileRequired) {
            this.maximumVedioFileRequired = maximumVedioFileRequired;
        }

        public Integer getMaxmumSizeOfVedioFileInMB() {
            return maxmumSizeOfVedioFileInMB;
        }

        public void setMaxmumSizeOfVedioFileInMB(Integer maxmumSizeOfVedioFileInMB) {
            this.maxmumSizeOfVedioFileInMB = maxmumSizeOfVedioFileInMB;
        }

        public Boolean getActiveInvestigatorReqActivity() {
            return isActiveInvestigatorReqActivity;
        }

        public void setActiveInvestigatorReqActivity(Boolean activeInvestigatorReqActivity) {
            isActiveInvestigatorReqActivity = activeInvestigatorReqActivity;
        }

        public Integer getEntryByUserID() {
            return entryByUserID;
        }

        public void setEntryByUserID(Integer entryByUserID) {
            this.entryByUserID = entryByUserID;
        }

        public String getEntryOnDate() {
            return entryOnDate;
        }

        public void setEntryOnDate(String entryOnDate) {
            this.entryOnDate = entryOnDate;
        }

        public Integer getModifiedByUserID() {
            return modifiedByUserID;
        }

        public void setModifiedByUserID(Integer modifiedByUserID) {
            this.modifiedByUserID = modifiedByUserID;
        }

        public String getModifiedOnDate() {
            return modifiedOnDate;
        }

        public void setModifiedOnDate(String modifiedOnDate) {
            this.modifiedOnDate = modifiedOnDate;
        }
    }
}
