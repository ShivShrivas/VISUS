package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InvReqActivityFile implements Serializable {
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
    private List<InvReqActivityFileData> data = new ArrayList<>();

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

    public List<InvReqActivityFileData> getData() {
        return data;
    }

    public void setData(List<InvReqActivityFileData> data) {
        this.data = data;
    }

    public class InvReqActivityFileData implements Serializable {
        @SerializedName("InvestigatorCaseActivity_ID")
        @Expose
        private Integer investigatorCaseActivityID;
        @SerializedName("InvestigatorCaseActivity_InvID")
        @Expose
        private Integer investigatorCaseActivityInvID;
        @SerializedName("InvestigatorRequiredActivityID")
        @Expose
        private Integer investigatorRequiredActivityID;
        @SerializedName("ObjInvReqActivity")
        @Expose
        private ObjInvReqActivity objInvReqActivity;
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
        @SerializedName("InvestigatorCaseActivity_CaseInsuranceID")
        @Expose
        private Integer investigatorCaseActivityCaseInsuranceID;

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

        public ObjInvReqActivity getObjInvReqActivity() {
            return objInvReqActivity;
        }

        public void setObjInvReqActivity(ObjInvReqActivity objInvReqActivity) {
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

    public class ObjInvReqActivity implements Serializable {
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
