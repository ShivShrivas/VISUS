package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Investigator implements Serializable {
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
    private List<InvestigatorData> data = new ArrayList<>();

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

    public List<InvestigatorData> getData() {
        return data;
    }

    public void setData(List<InvestigatorData> data) {
        this.data = data;
    }


    public class InvestigatorData {
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
