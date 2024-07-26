package com.org.visus.models.requset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MACT implements Serializable {
    @SerializedName("investigatorSubmittionDate")
    @Expose
    private String investigatorSubmittionDate;
    @SerializedName("tat")
    @Expose
    private Integer tat;
    @SerializedName("investigatorID")
    @Expose
    private Integer investigatorID;
    @SerializedName("mactid")
    @Expose
    private Integer mactid;
    @SerializedName("lstInvMACTCheckList")
    @Expose
    private List<MACTData> lstInvMACTCheckList = new ArrayList<>();
    @SerializedName("isMACTCheckListDataSaved")
    @Expose
    private Boolean isMACTCheckListDataSaved;
    @SerializedName("exceptionIfAny")
    @Expose
    private String exceptionIfAny;
    @SerializedName("MACT_InvInsuranceRelID")
    @Expose
    private Integer MACT_InvInsuranceRelID;

    public Integer getMACT_InvInsuranceRelID() {
        return MACT_InvInsuranceRelID;
    }

    public void setMACT_InvInsuranceRelID(Integer MACT_InvInsuranceRelID) {
        this.MACT_InvInsuranceRelID = MACT_InvInsuranceRelID;
    }


    public String getInvestigatorSubmittionDate() {
        return investigatorSubmittionDate;
    }

    public void setInvestigatorSubmittionDate(String investigatorSubmittionDate) {
        this.investigatorSubmittionDate = investigatorSubmittionDate;
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

    public Integer getMactid() {
        return mactid;
    }

    public void setMactid(Integer mactid) {
        this.mactid = mactid;
    }

    public List<MACTData> getLstInvMACTCheckList() {
        return lstInvMACTCheckList;
    }

    public void setLstInvMACTCheckList(List<MACTData> lstInvMACTCheckList) {
        this.lstInvMACTCheckList = lstInvMACTCheckList;
    }

    public Boolean getMACTCheckListDataSaved() {
        return isMACTCheckListDataSaved;
    }

    public void setMACTCheckListDataSaved(Boolean MACTCheckListDataSaved) {
        isMACTCheckListDataSaved = MACTCheckListDataSaved;
    }

    public String getExceptionIfAny() {
        return exceptionIfAny;
    }

    public void setExceptionIfAny(String exceptionIfAny) {
        this.exceptionIfAny = exceptionIfAny;
    }

    public static class MACTData implements Serializable {
        @SerializedName("invMACTCheckList_Id")
        @Expose
        private Integer invMACTCheckListId;
        @SerializedName("invMACTCheckList_mactID")
        @Expose
        private Integer invMACTCheckListMactID;
        @SerializedName("invMACTCheckList_InvID")
        @Expose
        private Integer invMACTCheckListInvID;
        @SerializedName("investigatorSubmittionDateTime")
        @Expose
        private String investigatorSubmittionDateTime;
        @SerializedName("invMACTCheckListHeadID")
        @Expose
        private Integer invMACTCheckListHeadID;
        @SerializedName("invMACTCheckListStatusData")
        @Expose
        private Boolean invMACTCheckListStatusData;
        @SerializedName("invMACTCheckListTextData")
        @Expose
        private String invMACTCheckListTextData;
        @SerializedName("entryOnDate")
        @Expose
        private String entryOnDate;
        @SerializedName("entryByUserID")
        @Expose
        private Integer entryByUserID;
        @SerializedName("entryByUserName")
        @Expose
        private String entryByUserName;
        @SerializedName("IsHoldCase")
        @Expose
        private Boolean IsHoldCase;

        public Boolean getHoldCase() {
            return IsHoldCase;
        }

        public void setHoldCase(Boolean holdCase) {
            IsHoldCase = holdCase;
        }


        public Integer getInvMACTCheckListId() {
            return invMACTCheckListId;
        }

        public void setInvMACTCheckListId(Integer invMACTCheckListId) {
            this.invMACTCheckListId = invMACTCheckListId;
        }

        public Integer getInvMACTCheckListMactID() {
            return invMACTCheckListMactID;
        }

        public void setInvMACTCheckListMactID(Integer invMACTCheckListMactID) {
            this.invMACTCheckListMactID = invMACTCheckListMactID;
        }

        public Integer getInvMACTCheckListInvID() {
            return invMACTCheckListInvID;
        }

        public void setInvMACTCheckListInvID(Integer invMACTCheckListInvID) {
            this.invMACTCheckListInvID = invMACTCheckListInvID;
        }

        public String getInvestigatorSubmittionDateTime() {
            return investigatorSubmittionDateTime;
        }

        public void setInvestigatorSubmittionDateTime(String investigatorSubmittionDateTime) {
            this.investigatorSubmittionDateTime = investigatorSubmittionDateTime;
        }

        public Integer getInvMACTCheckListHeadID() {
            return invMACTCheckListHeadID;
        }

        public void setInvMACTCheckListHeadID(Integer invMACTCheckListHeadID) {
            this.invMACTCheckListHeadID = invMACTCheckListHeadID;
        }

        public Boolean getInvMACTCheckListStatusData() {
            return invMACTCheckListStatusData;
        }

        public void setInvMACTCheckListStatusData(Boolean invMACTCheckListStatusData) {
            this.invMACTCheckListStatusData = invMACTCheckListStatusData;
        }

        public String getInvMACTCheckListTextData() {
            return invMACTCheckListTextData;
        }

        public void setInvMACTCheckListTextData(String invMACTCheckListTextData) {
            this.invMACTCheckListTextData = invMACTCheckListTextData;
        }

        public Object getEntryOnDate() {
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
