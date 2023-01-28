package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveInvestigatorAction implements Serializable {
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
    private List<SaveInvestigatorActionData> data = new ArrayList<>();

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

    public List<SaveInvestigatorActionData> getData() {
        return data;
    }

    public void setData(List<SaveInvestigatorActionData> data) {
        this.data = data;
    }

    public class SaveInvestigatorActionData {
        @SerializedName("InvestigatorCaseActivity_ID")
        @Expose
        private Integer investigatorCaseActivityID;
        @SerializedName("InvestigatorCaseActivity_CaseInsuranceID")
        @Expose
        private Integer investigatorCaseActivityCaseInsuranceID;
        @SerializedName("InvestigatorCaseActivity_InvID")
        @Expose
        private Integer investigatorCaseActivityInvID;
        @SerializedName("InvestigatorRequiredActivityID")
        @Expose
        private Integer investigatorRequiredActivityID;
        @SerializedName("ObjInvReqActivity")
        @Expose
        private String objInvReqActivity;
        @SerializedName("OriginalFileName")
        @Expose
        private String originalFileName;
        @SerializedName("UniqueFileName")
        @Expose
        private String uniqueFileName;
        @SerializedName("ActivityFilePath")
        @Expose
        private String activityFilePath;
        @SerializedName("FileSubmittionOnDate")
        @Expose
        private String fileSubmittionOnDate;
        @SerializedName("VisusServicesID")
        @Expose
        private Integer visusServicesID;
        @SerializedName("VisusServicesText")
        @Expose
        private String visusServicesText;
        @SerializedName("InvestigatorObj")
        @Expose
        private String investigatorObj;
        @SerializedName("InsuranceAssignedOnDate")
        @Expose
        private String insuranceAssignedOnDate;
        @SerializedName("InsuranceSubmittedOnDate")
        @Expose
        private String insuranceSubmittedOnDate;
        @SerializedName("TATForInvestigator")
        @Expose
        private Integer tATForInvestigator;
        @SerializedName("ClaimNumber")
        @Expose
        private String claimNumber;
        @SerializedName("PolicyNumber")
        @Expose
        private String policyNumber;
        @SerializedName("InsuranceCompanyName")
        @Expose
        private String insuranceCompanyName;
        @SerializedName("CompanyName")
        @Expose
        private String companyName;
        @SerializedName("ExceptionIfAny")
        @Expose
        private String exceptionIfAny;
        @SerializedName("InvestigatorComments")
        @Expose
        private String investigatorComments;
        @SerializedName("LatitudeAtClickingPhoto")
        @Expose
        private String latitudeAtClickingPhoto;
        @SerializedName("LongitudeAtClickingPhoto")
        @Expose
        private String longitudeAtClickingPhoto;
        @SerializedName("AddressAtClickingPhoto")
        @Expose
        private String addressAtClickingPhoto;

        public Integer getInvestigatorCaseActivityID() {
            return investigatorCaseActivityID;
        }

        public void setInvestigatorCaseActivityID(Integer investigatorCaseActivityID) {
            this.investigatorCaseActivityID = investigatorCaseActivityID;
        }

        public Integer getInvestigatorCaseActivityCaseInsuranceID() {
            return investigatorCaseActivityCaseInsuranceID;
        }

        public void setInvestigatorCaseActivityCaseInsuranceID(Integer investigatorCaseActivityCaseInsuranceID) {
            this.investigatorCaseActivityCaseInsuranceID = investigatorCaseActivityCaseInsuranceID;
        }

        public Integer getInvestigatorCaseActivityInvID() {
            return investigatorCaseActivityInvID;
        }

        public void setInvestigatorCaseActivityInvID(Integer investigatorCaseActivityInvID) {
            this.investigatorCaseActivityInvID = investigatorCaseActivityInvID;
        }

        public Integer getInvestigatorRequiredActivityID() {
            return investigatorRequiredActivityID;
        }

        public void setInvestigatorRequiredActivityID(Integer investigatorRequiredActivityID) {
            this.investigatorRequiredActivityID = investigatorRequiredActivityID;
        }

        public String getObjInvReqActivity() {
            return objInvReqActivity;
        }

        public void setObjInvReqActivity(String objInvReqActivity) {
            this.objInvReqActivity = objInvReqActivity;
        }

        public String getOriginalFileName() {
            return originalFileName;
        }

        public void setOriginalFileName(String originalFileName) {
            this.originalFileName = originalFileName;
        }

        public String getUniqueFileName() {
            return uniqueFileName;
        }

        public void setUniqueFileName(String uniqueFileName) {
            this.uniqueFileName = uniqueFileName;
        }

        public String getActivityFilePath() {
            return activityFilePath;
        }

        public void setActivityFilePath(String activityFilePath) {
            this.activityFilePath = activityFilePath;
        }

        public String getFileSubmittionOnDate() {
            return fileSubmittionOnDate;
        }

        public void setFileSubmittionOnDate(String fileSubmittionOnDate) {
            this.fileSubmittionOnDate = fileSubmittionOnDate;
        }

        public Integer getVisusServicesID() {
            return visusServicesID;
        }

        public void setVisusServicesID(Integer visusServicesID) {
            this.visusServicesID = visusServicesID;
        }

        public String getVisusServicesText() {
            return visusServicesText;
        }

        public void setVisusServicesText(String visusServicesText) {
            this.visusServicesText = visusServicesText;
        }

        public String getInvestigatorObj() {
            return investigatorObj;
        }

        public void setInvestigatorObj(String investigatorObj) {
            this.investigatorObj = investigatorObj;
        }

        public String getInsuranceAssignedOnDate() {
            return insuranceAssignedOnDate;
        }

        public void setInsuranceAssignedOnDate(String insuranceAssignedOnDate) {
            this.insuranceAssignedOnDate = insuranceAssignedOnDate;
        }

        public String getInsuranceSubmittedOnDate() {
            return insuranceSubmittedOnDate;
        }

        public void setInsuranceSubmittedOnDate(String insuranceSubmittedOnDate) {
            this.insuranceSubmittedOnDate = insuranceSubmittedOnDate;
        }

        public Integer gettATForInvestigator() {
            return tATForInvestigator;
        }

        public void settATForInvestigator(Integer tATForInvestigator) {
            this.tATForInvestigator = tATForInvestigator;
        }

        public String getClaimNumber() {
            return claimNumber;
        }

        public void setClaimNumber(String claimNumber) {
            this.claimNumber = claimNumber;
        }

        public String getPolicyNumber() {
            return policyNumber;
        }

        public void setPolicyNumber(String policyNumber) {
            this.policyNumber = policyNumber;
        }

        public String getInsuranceCompanyName() {
            return insuranceCompanyName;
        }

        public void setInsuranceCompanyName(String insuranceCompanyName) {
            this.insuranceCompanyName = insuranceCompanyName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getExceptionIfAny() {
            return exceptionIfAny;
        }

        public void setExceptionIfAny(String exceptionIfAny) {
            this.exceptionIfAny = exceptionIfAny;
        }

        public String getInvestigatorComments() {
            return investigatorComments;
        }

        public void setInvestigatorComments(String investigatorComments) {
            this.investigatorComments = investigatorComments;
        }

        public String getLatitudeAtClickingPhoto() {
            return latitudeAtClickingPhoto;
        }

        public void setLatitudeAtClickingPhoto(String latitudeAtClickingPhoto) {
            this.latitudeAtClickingPhoto = latitudeAtClickingPhoto;
        }

        public String getLongitudeAtClickingPhoto() {
            return longitudeAtClickingPhoto;
        }

        public void setLongitudeAtClickingPhoto(String longitudeAtClickingPhoto) {
            this.longitudeAtClickingPhoto = longitudeAtClickingPhoto;
        }

        public String getAddressAtClickingPhoto() {
            return addressAtClickingPhoto;
        }

        public void setAddressAtClickingPhoto(String addressAtClickingPhoto) {
            this.addressAtClickingPhoto = addressAtClickingPhoto;
        }
    }
}
