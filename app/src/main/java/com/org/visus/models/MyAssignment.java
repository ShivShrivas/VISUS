package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyAssignment implements Serializable {
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
    private List<MyAssignmentData> data = new ArrayList<>();

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

    public List<MyAssignmentData> getData() {
        return data;
    }

    public void setData(List<MyAssignmentData> data) {
        this.data = data;
    }

    public class MyAssignmentData implements Serializable {
        @SerializedName("InsuranceDataID")
        @Expose
        private Integer insuranceDataID;
        @SerializedName("VisusServicesID")
        @Expose
        private Integer visusServicesID;
        @SerializedName("VisusServicesText")
        @Expose
        private String visusServicesText;
        @SerializedName("InvestigatorObj")
        @Expose
        private InvestigatorObj investigatorObj;
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
        ///////////////////////////////////Add 2 Fields
        @SerializedName("ProductID")
        @Expose
        private Integer ProductID;
        @SerializedName("ProductName")
        @Expose
        private String ProductName;
        @SerializedName("CaseTitle")
        @Expose
        private String CaseTitle;
        @SerializedName("DateOfAccident")
        @Expose
        private String DateOfAccident;

        public String getCaseTitle() {
            return CaseTitle;
        }

        public void setCaseTitle(String caseTitle) {
            CaseTitle = caseTitle;
        }

        public String getDateOfAccident() {
            return DateOfAccident;
        }

        public void setDateOfAccident(String dateOfAccident) {
            DateOfAccident = dateOfAccident;
        }


        public Integer getProductID() {
            return ProductID;
        }

        public void setProductID(Integer productID) {
            ProductID = productID;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }


        public Integer getInsuranceDataID() {
            return insuranceDataID;
        }

        public void setInsuranceDataID(Integer insuranceDataID) {
            this.insuranceDataID = insuranceDataID;
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

        public InvestigatorObj getInvestigatorObj() {
            return investigatorObj;
        }

        public void setInvestigatorObj(InvestigatorObj investigatorObj) {
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
    }

    public class InvestigatorObj implements Serializable {
        @SerializedName("INV_ID")
        @Expose
        private Integer invId;
        @SerializedName("INV_Name")
        @Expose
        private String iNVName;
        @SerializedName("ISActive")
        @Expose
        private String iSActive;
        @SerializedName("INV_Father_Name")
        @Expose
        private String iNVFatherName;
        @SerializedName("INV_Contact_Number1")
        @Expose
        private String iNVContactNumber1;
        @SerializedName("INV_Contact_Number2")
        @Expose
        private String iNVContactNumber2;
        @SerializedName("INV_code")
        @Expose
        private String iNVCode;
        @SerializedName("JoiningDate")
        @Expose
        private String joiningDate;
        @SerializedName("ReliveDate")
        @Expose
        private String reliveDate;
        @SerializedName("DOB")
        @Expose
        private String dob;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("PAN_Number")
        @Expose
        private String pANNumber;

        public Integer getInvId() {
            return invId;
        }

        public void setInvId(Integer invId) {
            this.invId = invId;
        }

        public String getiNVName() {
            return iNVName;
        }

        public void setiNVName(String iNVName) {
            this.iNVName = iNVName;
        }

        public String getiSActive() {
            return iSActive;
        }

        public void setiSActive(String iSActive) {
            this.iSActive = iSActive;
        }

        public String getiNVFatherName() {
            return iNVFatherName;
        }

        public void setiNVFatherName(String iNVFatherName) {
            this.iNVFatherName = iNVFatherName;
        }

        public String getiNVContactNumber1() {
            return iNVContactNumber1;
        }

        public void setiNVContactNumber1(String iNVContactNumber1) {
            this.iNVContactNumber1 = iNVContactNumber1;
        }

        public String getiNVContactNumber2() {
            return iNVContactNumber2;
        }

        public void setiNVContactNumber2(String iNVContactNumber2) {
            this.iNVContactNumber2 = iNVContactNumber2;
        }

        public String getiNVCode() {
            return iNVCode;
        }

        public void setiNVCode(String iNVCode) {
            this.iNVCode = iNVCode;
        }

        public String getJoiningDate() {
            return joiningDate;
        }

        public void setJoiningDate(String joiningDate) {
            this.joiningDate = joiningDate;
        }

        public String getReliveDate() {
            return reliveDate;
        }

        public void setReliveDate(String reliveDate) {
            this.reliveDate = reliveDate;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getpANNumber() {
            return pANNumber;
        }

        public void setpANNumber(String pANNumber) {
            this.pANNumber = pANNumber;
        }
    }
}
