package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GiTheftInsuCheckList implements Serializable {
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
    private List<GiTheftInsuCheckListData> data = new ArrayList<>();

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

    public List<GiTheftInsuCheckListData> getData() {
        return data;
    }

    public void setData(List<GiTheftInsuCheckListData> data) {
        this.data = data;
    }


    public class GiTheftInsuCheckListData implements Serializable {
        @SerializedName("InvGiTheftCheckListHead_ID")
        @Expose
        private Integer invGiTheftCheckListHeadID;
        @SerializedName("InvGiTheftCheckListHead_Text")
        @Expose
        private String invGiTheftCheckListHeadText;
        @SerializedName("IsActiveInvGiTheftCheckListHead")
        @Expose
        private Boolean isActiveInvGiTheftCheckListHead;

        public Integer getInvGiTheftCheckListHeadID() {
            return invGiTheftCheckListHeadID;
        }

        public void setInvGiTheftCheckListHeadID(Integer invGiTheftCheckListHeadID) {
            this.invGiTheftCheckListHeadID = invGiTheftCheckListHeadID;
        }

        public String getInvGiTheftCheckListHeadText() {
            return invGiTheftCheckListHeadText;
        }

        public void setInvGiTheftCheckListHeadText(String invGiTheftCheckListHeadText) {
            this.invGiTheftCheckListHeadText = invGiTheftCheckListHeadText;
        }

        public Boolean getActiveInvGiTheftCheckListHead() {
            return isActiveInvGiTheftCheckListHead;
        }

        public void setActiveInvGiTheftCheckListHead(Boolean activeInvGiTheftCheckListHead) {
            isActiveInvGiTheftCheckListHead = activeInvGiTheftCheckListHead;
        }


    }
}
