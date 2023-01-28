package com.org.visus.models.requset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GI_OD implements Serializable {
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
    @SerializedName("lstInvGiODCheckList")
    @Expose
    private List<GI_ODData> lstInvGiODCheckList = null;
    @SerializedName("isGiODCheckListDataSaved")
    @Expose
    private Boolean isGiODCheckListDataSaved;
    @SerializedName("exceptionIfAny")
    @Expose
    private String exceptionIfAny;

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

    public List<GI_ODData> getLstInvGiODCheckList() {
        return lstInvGiODCheckList;
    }

    public void setLstInvGiODCheckList(List<GI_ODData> lstInvGiODCheckList) {
        this.lstInvGiODCheckList = lstInvGiODCheckList;
    }

    public Boolean getGiODCheckListDataSaved() {
        return isGiODCheckListDataSaved;
    }

    public void setGiODCheckListDataSaved(Boolean giODCheckListDataSaved) {
        isGiODCheckListDataSaved = giODCheckListDataSaved;
    }

    public String getExceptionIfAny() {
        return exceptionIfAny;
    }

    public void setExceptionIfAny(String exceptionIfAny) {
        this.exceptionIfAny = exceptionIfAny;
    }


    public class GI_ODData {
        @SerializedName("investigatorGiODCheckList_ID")
        @Expose
        private Integer investigatorGiODCheckListID;
        @SerializedName("investigatorGiODCheckList_InvID")
        @Expose
        private Integer investigatorGiODCheckListInvID;
        @SerializedName("investigatorGiODCheckList_GenInsuID")
        @Expose
        private Integer investigatorGiODCheckListGenInsuID;
        @SerializedName("investigatorODVisitTypeID")
        @Expose
        private Integer investigatorODVisitTypeID;
        @SerializedName("investigatorODVisitDocID")
        @Expose
        private Integer investigatorODVisitDocID;
        @SerializedName("investigatorGiODCheckListSubmittedOnDate")
        @Expose
        private String investigatorGiODCheckListSubmittedOnDate;
        @SerializedName("investigatorGiODCheckListSubmittedByUserID")
        @Expose
        private Integer investigatorGiODCheckListSubmittedByUserID;
        @SerializedName("investigatorGiODCheckListEntryOnDate")
        @Expose
        private String investigatorGiODCheckListEntryOnDate;
        @SerializedName("isGIInvCheckListStatus")
        @Expose
        private Boolean isGIInvCheckListStatus;
        @SerializedName("giInvCheckListReason")
        @Expose
        private String giInvCheckListReason;
        @SerializedName("IsHoldCase")
        @Expose
        private Boolean IsHoldCase;

        public Boolean getHoldCase() {
            return IsHoldCase;
        }

        public void setHoldCase(Boolean holdCase) {
            IsHoldCase = holdCase;
        }


        public Integer getInvestigatorGiODCheckListID() {
            return investigatorGiODCheckListID;
        }

        public void setInvestigatorGiODCheckListID(Integer investigatorGiODCheckListID) {
            this.investigatorGiODCheckListID = investigatorGiODCheckListID;
        }

        public Integer getInvestigatorGiODCheckListInvID() {
            return investigatorGiODCheckListInvID;
        }

        public void setInvestigatorGiODCheckListInvID(Integer investigatorGiODCheckListInvID) {
            this.investigatorGiODCheckListInvID = investigatorGiODCheckListInvID;
        }

        public Integer getInvestigatorGiODCheckListGenInsuID() {
            return investigatorGiODCheckListGenInsuID;
        }

        public void setInvestigatorGiODCheckListGenInsuID(Integer investigatorGiODCheckListGenInsuID) {
            this.investigatorGiODCheckListGenInsuID = investigatorGiODCheckListGenInsuID;
        }

        public Integer getInvestigatorODVisitTypeID() {
            return investigatorODVisitTypeID;
        }

        public void setInvestigatorODVisitTypeID(Integer investigatorODVisitTypeID) {
            this.investigatorODVisitTypeID = investigatorODVisitTypeID;
        }

        public Integer getInvestigatorODVisitDocID() {
            return investigatorODVisitDocID;
        }

        public void setInvestigatorODVisitDocID(Integer investigatorODVisitDocID) {
            this.investigatorODVisitDocID = investigatorODVisitDocID;
        }

        public String getInvestigatorGiODCheckListSubmittedOnDate() {
            return investigatorGiODCheckListSubmittedOnDate;
        }

        public void setInvestigatorGiODCheckListSubmittedOnDate(String investigatorGiODCheckListSubmittedOnDate) {
            this.investigatorGiODCheckListSubmittedOnDate = investigatorGiODCheckListSubmittedOnDate;
        }

        public Integer getInvestigatorGiODCheckListSubmittedByUserID() {
            return investigatorGiODCheckListSubmittedByUserID;
        }

        public void setInvestigatorGiODCheckListSubmittedByUserID(Integer investigatorGiODCheckListSubmittedByUserID) {
            this.investigatorGiODCheckListSubmittedByUserID = investigatorGiODCheckListSubmittedByUserID;
        }

        public String getInvestigatorGiODCheckListEntryOnDate() {
            return investigatorGiODCheckListEntryOnDate;
        }

        public void setInvestigatorGiODCheckListEntryOnDate(String investigatorGiODCheckListEntryOnDate) {
            this.investigatorGiODCheckListEntryOnDate = investigatorGiODCheckListEntryOnDate;
        }

        public Boolean getGIInvCheckListStatus() {
            return isGIInvCheckListStatus;
        }

        public void setGIInvCheckListStatus(Boolean GIInvCheckListStatus) {
            isGIInvCheckListStatus = GIInvCheckListStatus;
        }

        public String getGiInvCheckListReason() {
            return giInvCheckListReason;
        }

        public void setGiInvCheckListReason(String giInvCheckListReason) {
            this.giInvCheckListReason = giInvCheckListReason;
        }


    }
}
