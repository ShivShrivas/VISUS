package com.org.visus.models.requset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GI_Theft implements Serializable {
    @SerializedName("isGiTheftCheckListDataSaved")
    @Expose
    private Boolean isGiTheftCheckListDataSaved;
    @SerializedName("exceptionIfAny")
    @Expose
    private String exceptionIfAny;
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
    @SerializedName("lstInvGiTheftCheckList")
    @Expose
    private List<GI_TheftData> lstInvGiTheftCheckList = new ArrayList<>();
    @SerializedName("GiTheft_InvInsuranceRelID")
    @Expose
    private Integer GiTheft_InvInsuranceRelID;

    public Integer getGiTheft_InvInsuranceRelID() {
        return GiTheft_InvInsuranceRelID;
    }

    public void setGiTheft_InvInsuranceRelID(Integer giTheft_InvInsuranceRelID) {
        GiTheft_InvInsuranceRelID = giTheft_InvInsuranceRelID;
    }


    public Boolean getGiTheftCheckListDataSaved() {
        return isGiTheftCheckListDataSaved;
    }

    public void setGiTheftCheckListDataSaved(Boolean giTheftCheckListDataSaved) {
        isGiTheftCheckListDataSaved = giTheftCheckListDataSaved;
    }

    public String getExceptionIfAny() {
        return exceptionIfAny;
    }

    public void setExceptionIfAny(String exceptionIfAny) {
        this.exceptionIfAny = exceptionIfAny;
    }

    public String getInvestigatorSubmittionDateTime() {
        return investigatorSubmittionDateTime;
    }

    public void setInvestigatorSubmittionDateTime(String investigatorSubmittionDateTime) {
        this.investigatorSubmittionDateTime = investigatorSubmittionDateTime;
    }

    public Integer getTat() {
        return tat;
    }

    public void setTat(Integer tat) {
        this.tat = tat;
    }

    public Integer getInvestigatorID() {
        return investigatorID;
    }

    public void setInvestigatorID(Integer investigatorID) {
        this.investigatorID = investigatorID;
    }

    public Integer getGeneralInsuranceID() {
        return generalInsuranceID;
    }

    public void setGeneralInsuranceID(Integer generalInsuranceID) {
        this.generalInsuranceID = generalInsuranceID;
    }

    public List<GI_TheftData> getLstInvGiTheftCheckList() {
        return lstInvGiTheftCheckList;
    }

    public void setLstInvGiTheftCheckList(List<GI_TheftData> lstInvGiTheftCheckList) {
        this.lstInvGiTheftCheckList = lstInvGiTheftCheckList;
    }


    public static class GI_TheftData {
        @SerializedName("invGiTheftCheckList_ID")
        @Expose
        private Integer invGiTheftCheckListID;
        @SerializedName("invGiTheftCheckList_InvID")
        @Expose
        private Integer invGiTheftCheckListInvID;
        @SerializedName("invGiTheftCheckList_GenInsuID")
        @Expose
        private Integer invGiTheftCheckListGenInsuID;
        @SerializedName("invGiTheftCheckListSubmittedOnDate")
        @Expose
        private String invGiTheftCheckListSubmittedOnDate;
        @SerializedName("invGiTheftCheckListHeadID")
        @Expose
        private Integer invGiTheftCheckListHeadID;
        @SerializedName("investigatorRemark")
        @Expose
        private String investigatorRemark;
        @SerializedName("entryOnDate")
        @Expose
        private String entryOnDate;
        @SerializedName("entryByUserID")
        @Expose
        private Integer entryByUserID;
        @SerializedName("entryByUserName")
        @Expose
        private String entryByUserName;

        @SerializedName("isHoldCase")
        @Expose
        private Boolean isHoldCase;

        public Boolean getHoldCase() {
            return isHoldCase;
        }

        public void setHoldCase(Boolean holdCase) {
            isHoldCase = holdCase;
        }

        public Integer getInvGiTheftCheckListID() {
            return invGiTheftCheckListID;
        }

        public void setInvGiTheftCheckListID(Integer invGiTheftCheckListID) {
            this.invGiTheftCheckListID = invGiTheftCheckListID;
        }

        public Integer getInvGiTheftCheckListInvID() {
            return invGiTheftCheckListInvID;
        }

        public void setInvGiTheftCheckListInvID(Integer invGiTheftCheckListInvID) {
            this.invGiTheftCheckListInvID = invGiTheftCheckListInvID;
        }

        public Integer getInvGiTheftCheckListGenInsuID() {
            return invGiTheftCheckListGenInsuID;
        }

        public void setInvGiTheftCheckListGenInsuID(Integer invGiTheftCheckListGenInsuID) {
            this.invGiTheftCheckListGenInsuID = invGiTheftCheckListGenInsuID;
        }

        public String getInvGiTheftCheckListSubmittedOnDate() {
            return invGiTheftCheckListSubmittedOnDate;
        }

        public void setInvGiTheftCheckListSubmittedOnDate(String invGiTheftCheckListSubmittedOnDate) {
            this.invGiTheftCheckListSubmittedOnDate = invGiTheftCheckListSubmittedOnDate;
        }

        public Integer getInvGiTheftCheckListHeadID() {
            return invGiTheftCheckListHeadID;
        }

        public void setInvGiTheftCheckListHeadID(Integer invGiTheftCheckListHeadID) {
            this.invGiTheftCheckListHeadID = invGiTheftCheckListHeadID;
        }

        public String getInvestigatorRemark() {
            return investigatorRemark;
        }

        public void setInvestigatorRemark(String investigatorRemark) {
            this.investigatorRemark = investigatorRemark;
        }

        public String getEntryOnDate() {
            return entryOnDate;
        }

        public void setEntryOnDate(String entryOnDate) {
            this.entryOnDate = entryOnDate;
        }

        public Integer getEntryByUserID() {
            return entryByUserID;
        }

        public void setEntryByUserID(Integer entryByUserID) {
            this.entryByUserID = entryByUserID;
        }

        public String getEntryByUserName() {
            return entryByUserName;
        }

        public void setEntryByUserName(String entryByUserName) {
            this.entryByUserName = entryByUserName;
        }
    }
}
