package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MACTInsuCheckList implements Serializable {
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
    private List<MACTInsuCheckListData> data = new ArrayList<>();

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

    public List<MACTInsuCheckListData> getData() {
        return data;
    }

    public void setData(List<MACTInsuCheckListData> data) {
        this.data = data;
    }


    public class MACTInsuCheckListData implements Serializable {
        @SerializedName("InvMACTCheckListHead_ID")
        @Expose
        private Integer invMACTCheckListHeadID;
        @SerializedName("InvMACTCheckListHead_Text")
        @Expose
        private String invMACTCheckListHeadText;
        @SerializedName("P_InvMACTCheckListHeadID")
        @Expose
        private Integer pInvMACTCheckListHeadID;
        @SerializedName("P_InvMACTCheckListHeadText")
        @Expose
        private String pInvMACTCheckListHeadText;
        @SerializedName("IsMACTCheckListTextData")
        @Expose
        private Boolean isMACTCheckListTextData;
        @SerializedName("IsMACTCheckListBoolean")
        @Expose
        private Boolean isMACTCheckListBoolean;
        @SerializedName("IsInvMACTCheckListHead")
        @Expose
        private Boolean isInvMACTCheckListHead;
        @SerializedName("IsHasMultipleLine")
        @Expose
        private Boolean isHasMultipleLine;

        public Integer getInvMACTCheckListHeadID() {
            return invMACTCheckListHeadID;
        }

        public void setInvMACTCheckListHeadID(Integer invMACTCheckListHeadID) {
            this.invMACTCheckListHeadID = invMACTCheckListHeadID;
        }

        public String getInvMACTCheckListHeadText() {
            return invMACTCheckListHeadText;
        }

        public void setInvMACTCheckListHeadText(String invMACTCheckListHeadText) {
            this.invMACTCheckListHeadText = invMACTCheckListHeadText;
        }

        public Integer getpInvMACTCheckListHeadID() {
            return pInvMACTCheckListHeadID;
        }

        public void setpInvMACTCheckListHeadID(Integer pInvMACTCheckListHeadID) {
            this.pInvMACTCheckListHeadID = pInvMACTCheckListHeadID;
        }

        public String getpInvMACTCheckListHeadText() {
            return pInvMACTCheckListHeadText;
        }

        public void setpInvMACTCheckListHeadText(String pInvMACTCheckListHeadText) {
            this.pInvMACTCheckListHeadText = pInvMACTCheckListHeadText;
        }

        public Boolean getMACTCheckListTextData() {
            return isMACTCheckListTextData;
        }

        public void setMACTCheckListTextData(Boolean MACTCheckListTextData) {
            isMACTCheckListTextData = MACTCheckListTextData;
        }

        public Boolean getMACTCheckListBoolean() {
            return isMACTCheckListBoolean;
        }

        public void setMACTCheckListBoolean(Boolean MACTCheckListBoolean) {
            isMACTCheckListBoolean = MACTCheckListBoolean;
        }

        public Boolean getInvMACTCheckListHead() {
            return isInvMACTCheckListHead;
        }

        public void setInvMACTCheckListHead(Boolean invMACTCheckListHead) {
            isInvMACTCheckListHead = invMACTCheckListHead;
        }

        public Boolean getHasMultipleLine() {
            return isHasMultipleLine;
        }

        public void setHasMultipleLine(Boolean hasMultipleLine) {
            isHasMultipleLine = hasMultipleLine;
        }
    }
}
