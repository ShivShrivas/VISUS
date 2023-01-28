package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetServices implements Serializable {
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
    private List<GetServicesData> data = new ArrayList<>();

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

    public List<GetServicesData> getData() {
        return data;
    }

    public void setData(List<GetServicesData> data) {
        this.data = data;
    }


    public class GetServicesData {
        @SerializedName("VisusServicesID")
        @Expose
        private Integer visusServicesID;
        @SerializedName("VisusServicesText")
        @Expose
        private String visusServicesText;
        @SerializedName("VisusServicesDesc")
        @Expose
        private String visusServicesDesc;
        @SerializedName("IsVisusActiveServices")
        @Expose
        private Boolean isVisusActiveServices;

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

        public String getVisusServicesDesc() {
            return visusServicesDesc;
        }

        public void setVisusServicesDesc(String visusServicesDesc) {
            this.visusServicesDesc = visusServicesDesc;
        }

        public Boolean getVisusActiveServices() {
            return isVisusActiveServices;
        }

        public void setVisusActiveServices(Boolean visusActiveServices) {
            isVisusActiveServices = visusActiveServices;
        }


    }

}
