package com.org.visus.models.requset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GI_PA implements Serializable {
    @SerializedName("isGiPACheckListDataSaved")
    @Expose
    private Boolean isGiPACheckListDataSaved;
    @SerializedName("exceptionIfAny")
    @Expose
    private Object exceptionIfAny;
    @SerializedName("investigatorSubmittionDateTime")
    @Expose
    private String investigatorSubmittionDateTime;
    @SerializedName("tat")
    @Expose
    private Integer tat;
    @SerializedName("investigatorID")
    @Expose
    private Integer investigatorID;
    @SerializedName("generalInsuranceID")
    @Expose
    private Integer generalInsuranceID;
    @SerializedName("lstInvGiPACheckList")
    @Expose
    private List<GI_PAData> lstInvGiPACheckList = new ArrayList<>();

    public class GI_PAData implements Serializable {
        @SerializedName("invGiPACheckList_ID")
        @Expose
        private Integer invGiPACheckListID;
        @SerializedName("invGiPACheckListHeadID")
        @Expose
        private Integer invGiPACheckListHeadID;
        @SerializedName("invGiPACheckList_GeneralInsuID")
        @Expose
        private Integer invGiPACheckListGeneralInsuID;
        @SerializedName("invGiPACheckList_InvID")
        @Expose
        private Integer invGiPACheckListInvID;
        @SerializedName("invGiPACheckListSubmittedOnDateTime")
        @Expose
        private String invGiPACheckListSubmittedOnDateTime;
        @SerializedName("isCheckListHeadDataAvailable")
        @Expose
        private Boolean isCheckListHeadDataAvailable;
        @SerializedName("invGiPACheckList_Remark")
        @Expose
        private String invGiPACheckListRemark;
        @SerializedName("invGiPACheckList_Yes")
        @Expose
        private String invGiPACheckListYes;
        @SerializedName("invGiPACheckList_No")
        @Expose
        private String invGiPACheckListNo;
        @SerializedName("entryByUserID")
        @Expose
        private Integer entryByUserID;
        @SerializedName("entryOnDate")
        @Expose
        private String entryOnDate;
        @SerializedName("isHoldCase")
        @Expose
        private Boolean isHoldCase;

        public Integer getInvGiPACheckListID() {
            return invGiPACheckListID;
        }

        public void setInvGiPACheckListID(Integer invGiPACheckListID) {
            this.invGiPACheckListID = invGiPACheckListID;
        }

        public Integer getInvGiPACheckListHeadID() {
            return invGiPACheckListHeadID;
        }

        public void setInvGiPACheckListHeadID(Integer invGiPACheckListHeadID) {
            this.invGiPACheckListHeadID = invGiPACheckListHeadID;
        }

        public Integer getInvGiPACheckListGeneralInsuID() {
            return invGiPACheckListGeneralInsuID;
        }

        public void setInvGiPACheckListGeneralInsuID(Integer invGiPACheckListGeneralInsuID) {
            this.invGiPACheckListGeneralInsuID = invGiPACheckListGeneralInsuID;
        }

        public Integer getInvGiPACheckListInvID() {
            return invGiPACheckListInvID;
        }

        public void setInvGiPACheckListInvID(Integer invGiPACheckListInvID) {
            this.invGiPACheckListInvID = invGiPACheckListInvID;
        }

        public String getInvGiPACheckListSubmittedOnDateTime() {
            return invGiPACheckListSubmittedOnDateTime;
        }

        public void setInvGiPACheckListSubmittedOnDateTime(String invGiPACheckListSubmittedOnDateTime) {
            this.invGiPACheckListSubmittedOnDateTime = invGiPACheckListSubmittedOnDateTime;
        }

        public Boolean getCheckListHeadDataAvailable() {
            return isCheckListHeadDataAvailable;
        }

        public void setCheckListHeadDataAvailable(Boolean checkListHeadDataAvailable) {
            isCheckListHeadDataAvailable = checkListHeadDataAvailable;
        }

        public String getInvGiPACheckListRemark() {
            return invGiPACheckListRemark;
        }

        public void setInvGiPACheckListRemark(String invGiPACheckListRemark) {
            this.invGiPACheckListRemark = invGiPACheckListRemark;
        }

        public String getInvGiPACheckListYes() {
            return invGiPACheckListYes;
        }

        public void setInvGiPACheckListYes(String invGiPACheckListYes) {
            this.invGiPACheckListYes = invGiPACheckListYes;
        }

        public String getInvGiPACheckListNo() {
            return invGiPACheckListNo;
        }

        public void setInvGiPACheckListNo(String invGiPACheckListNo) {
            this.invGiPACheckListNo = invGiPACheckListNo;
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

        public Boolean getHoldCase() {
            return isHoldCase;
        }

        public void setHoldCase(Boolean holdCase) {
            isHoldCase = holdCase;
        }


    }
}
