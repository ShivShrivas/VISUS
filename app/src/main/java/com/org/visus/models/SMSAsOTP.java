package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SMSAsOTP implements Serializable {
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
    private List<SMSAsOTPData> data = new ArrayList<>();

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

    public List<SMSAsOTPData> getData() {
        return data;
    }

    public void setData(List<SMSAsOTPData> data) {
        this.data = data;
    }

    public class SMSAsOTPData {
        @SerializedName("OtpSmsForInvRegOnDeviceID")
        @Expose
        private Integer otpSmsForInvRegOnDeviceID;
        @SerializedName("OtpSMSText")
        @Expose
        private String otpSMSText;
        @SerializedName("RseultTextFromSMSSending")
        @Expose
        private String rseultTextFromSMSSending;
        @SerializedName("MobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("InvCode")
        @Expose
        private String invCode;
        @SerializedName("InsName")
        @Expose
        private String insName;
        @SerializedName("InvID")
        @Expose
        private Integer invID;
        @SerializedName("Otp")
        @Expose
        private String otp;
        @SerializedName("ExceptionIfAny")
        @Expose
        private String exceptionIfAny;
        @SerializedName("SMS_User")
        @Expose
        private String sMSUser;
        @SerializedName("SMSSendOnDateAndTime")
        @Expose
        private String sMSSendOnDateAndTime;
        @SerializedName("DeviceID")
        @Expose
        private Integer deviceID;
        @SerializedName("IsSmsOtpSend")
        @Expose
        private Boolean isSmsOtpSend;
        @SerializedName("DisplayMobNumber")
        @Expose
        private String displayMobNumber;

        public Integer getOtpSmsForInvRegOnDeviceID() {
            return otpSmsForInvRegOnDeviceID;
        }

        public void setOtpSmsForInvRegOnDeviceID(Integer otpSmsForInvRegOnDeviceID) {
            this.otpSmsForInvRegOnDeviceID = otpSmsForInvRegOnDeviceID;
        }

        public String getOtpSMSText() {
            return otpSMSText;
        }

        public void setOtpSMSText(String otpSMSText) {
            this.otpSMSText = otpSMSText;
        }

        public String getRseultTextFromSMSSending() {
            return rseultTextFromSMSSending;
        }

        public void setRseultTextFromSMSSending(String rseultTextFromSMSSending) {
            this.rseultTextFromSMSSending = rseultTextFromSMSSending;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getInvCode() {
            return invCode;
        }

        public void setInvCode(String invCode) {
            this.invCode = invCode;
        }

        public String getInsName() {
            return insName;
        }

        public void setInsName(String insName) {
            this.insName = insName;
        }

        public Integer getInvID() {
            return invID;
        }

        public void setInvID(Integer invID) {
            this.invID = invID;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getExceptionIfAny() {
            return exceptionIfAny;
        }

        public void setExceptionIfAny(String exceptionIfAny) {
            this.exceptionIfAny = exceptionIfAny;
        }

        public String getsMSUser() {
            return sMSUser;
        }

        public void setsMSUser(String sMSUser) {
            this.sMSUser = sMSUser;
        }

        public String getsMSSendOnDateAndTime() {
            return sMSSendOnDateAndTime;
        }

        public void setsMSSendOnDateAndTime(String sMSSendOnDateAndTime) {
            this.sMSSendOnDateAndTime = sMSSendOnDateAndTime;
        }

        public Integer getDeviceID() {
            return deviceID;
        }

        public void setDeviceID(Integer deviceID) {
            this.deviceID = deviceID;
        }

        public Boolean getSmsOtpSend() {
            return isSmsOtpSend;
        }

        public void setSmsOtpSend(Boolean smsOtpSend) {
            isSmsOtpSend = smsOtpSend;
        }

        public String getDisplayMobNumber() {
            return displayMobNumber;
        }

        public void setDisplayMobNumber(String displayMobNumber) {
            this.displayMobNumber = displayMobNumber;
        }

    }
}
