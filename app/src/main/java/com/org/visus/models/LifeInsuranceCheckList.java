package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LifeInsuranceCheckList implements Serializable {
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
    private List<LifeInsuranceCheckListData> data = new ArrayList<>();

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

    public List<LifeInsuranceCheckListData> getData() {
        return data;
    }

    public void setData(List<LifeInsuranceCheckListData> data) {
        this.data = data;
    }

    public class LifeInsuranceCheckListData implements Serializable {
        @SerializedName("TAT")
        @Expose
        private Integer tat;
        @SerializedName("InvestigationAssignDate")
        @Expose
        private String investigationAssignDate;
        @SerializedName("InvestigatorLiCheckList_ID")
        @Expose
        private Integer investigatorLiCheckListID;
        @SerializedName("InvestigatorLiCheckList_InvID")
        @Expose
        private Integer investigatorLiCheckListInvID;
        @SerializedName("InvestigatorCode")
        @Expose
        private String investigatorCode;
        @SerializedName("InvestigatorName")
        @Expose
        private String investigatorName;
        @SerializedName("InvestigatorContactNo")
        @Expose
        private String investigatorContactNo;
        @SerializedName("InvestigatorLiCheckList_LiID")
        @Expose
        private Integer investigatorLiCheckListLiID;
        @SerializedName("InvestigatorLiCheckListSubmittedDate")
        @Expose
        private String investigatorLiCheckListSubmittedDate;
        @SerializedName("InvestigatorLiCheckListSubmittedByUserID")
        @Expose
        private Integer investigatorLiCheckListSubmittedByUserID;
        @SerializedName("InvestigatorLiCheckListSubmittedByUserName")
        @Expose
        private String investigatorLiCheckListSubmittedByUserName;
        @SerializedName("InvestigatorLiCheckListEntryDate")
        @Expose
        private String investigatorLiCheckListEntryDate;
        @SerializedName("Insured_Name")
        @Expose
        private String insuredName;
        @SerializedName("Insured_FatherOrHusbandName")
        @Expose
        private String insuredFatherOrHusbandName;
        @SerializedName("Insured_Age")
        @Expose
        private Integer insuredAge;
        @SerializedName("Insured_Company")
        @Expose
        private String insuredCompany;
        @SerializedName("Claimant_Name")
        @Expose
        private String claimantName;
        @SerializedName("Claimant_Age")
        @Expose
        private Integer claimantAge;
        @SerializedName("IsOccupationDetailsConfirmed")
        @Expose
        private Boolean isOccupationDetailsConfirmed;
        @SerializedName("Occupation_Details")
        @Expose
        private String occupationDetails;
        @SerializedName("IsAgeConfirmed")
        @Expose
        private Boolean isAgeConfirmed;
        @SerializedName("AboutAgeConfirmed")
        @Expose
        private String aboutAgeConfirmed;
        @SerializedName("LstInvLifeInsuCheckListNeighbours")
        @Expose
        private List<InvLifeInsuCheckListNeighbours> lstInvLifeInsuCheckListNeighbours = new ArrayList<>();
        @SerializedName("AnganbadiName")
        @Expose
        private String anganbadiName;
        @SerializedName("AnganbadiContactNumber")
        @Expose
        private String anganbadiContactNumber;
        @SerializedName("AshaBahuName")
        @Expose
        private String ashaBahuName;
        @SerializedName("AshaBahuContactNo")
        @Expose
        private String ashaBahuContactNo;
        @SerializedName("GramPradhanName")
        @Expose
        private String gramPradhanName;
        @SerializedName("GramPradhanContactNo")
        @Expose
        private String gramPradhanContactNo;
        @SerializedName("SabhasadWardName")
        @Expose
        private String sabhasadWardName;
        @SerializedName("SabhasadWardContactNo")
        @Expose
        private String sabhasadWardContactNo;
        @SerializedName("VillageSecretaryName")
        @Expose
        private String villageSecretaryName;
        @SerializedName("VillageSecretaryContactNo")
        @Expose
        private String villageSecretaryContactNo;
        @SerializedName("NagarNigamAuthorityName")
        @Expose
        private String nagarNigamAuthorityName;
        @SerializedName("NagarNigamAuthorityContactNo")
        @Expose
        private String nagarNigamAuthorityContactNo;
        @SerializedName("LstInvLifeInsuCheckListMedicalStore")
        @Expose
        private List<InvLifeInsuCheckListMedicalStore> lstInvLifeInsuCheckListMedicalStore = new ArrayList<>();
        @SerializedName("PHC_CHC_DistrictHospitalAddress")
        @Expose
        private String pHCCHCDistrictHospitalAddress;
        @SerializedName("PHC_CHC_DistrictHospitalAddressContactNo")
        @Expose
        private String pHCCHCDistrictHospitalAddressContactNo;
        @SerializedName("LstInvLifeInsuCheckListHospitalCheck")
        @Expose
        private List<InvLifeInsuCheckListHospitalCheck> lstInvLifeInsuCheckListHospitalCheck = new ArrayList<>();
        @SerializedName("PoliceStationCheck")
        @Expose
        private String policeStationCheck;
        @SerializedName("CremationPlaceDetails")
        @Expose
        private String cremationPlaceDetails;
        @SerializedName("FamilyMemberDetails")
        @Expose
        private String familyMemberDetails;
        @SerializedName("NarrationOfTheCase")
        @Expose
        private String narrationOfTheCase;
        @SerializedName("ExceptionInSaving")
        @Expose
        private String exceptionInSaving;
        @SerializedName("IsLiCheckListDataSaved")
        @Expose
        private Boolean isLiCheckListDataSaved;
        @SerializedName("Li_InvInsuranceRelID")
        @Expose
        private Integer Li_InvInsuranceRelID;

        @SerializedName("isHoldCase")
        @Expose
        private Boolean isHoldCase;

        public Boolean getHoldCase() {
            return isHoldCase;
        }

        public void setHoldCase(Boolean holdCase) {
            isHoldCase = holdCase;
        }

        public Integer getLi_InvInsuranceRelID() {
            return Li_InvInsuranceRelID;
        }

        public void setLi_InvInsuranceRelID(Integer li_InvInsuranceRelID) {
            Li_InvInsuranceRelID = li_InvInsuranceRelID;
        }


        public Integer getTat() {
            return tat;
        }

        public void setTat(Integer tat) {
            this.tat = tat;
        }

        public String getInvestigationAssignDate() {
            return investigationAssignDate;
        }

        public void setInvestigationAssignDate(String investigationAssignDate) {
            this.investigationAssignDate = investigationAssignDate;
        }

        public Integer getInvestigatorLiCheckListID() {
            return investigatorLiCheckListID;
        }

        public void setInvestigatorLiCheckListID(Integer investigatorLiCheckListID) {
            this.investigatorLiCheckListID = investigatorLiCheckListID;
        }

        public Integer getInvestigatorLiCheckListInvID() {
            return investigatorLiCheckListInvID;
        }

        public void setInvestigatorLiCheckListInvID(Integer investigatorLiCheckListInvID) {
            this.investigatorLiCheckListInvID = investigatorLiCheckListInvID;
        }

        public String getInvestigatorCode() {
            return investigatorCode;
        }

        public void setInvestigatorCode(String investigatorCode) {
            this.investigatorCode = investigatorCode;
        }

        public String getInvestigatorName() {
            return investigatorName;
        }

        public void setInvestigatorName(String investigatorName) {
            this.investigatorName = investigatorName;
        }

        public String getInvestigatorContactNo() {
            return investigatorContactNo;
        }

        public void setInvestigatorContactNo(String investigatorContactNo) {
            this.investigatorContactNo = investigatorContactNo;
        }

        public Integer getInvestigatorLiCheckListLiID() {
            return investigatorLiCheckListLiID;
        }

        public void setInvestigatorLiCheckListLiID(Integer investigatorLiCheckListLiID) {
            this.investigatorLiCheckListLiID = investigatorLiCheckListLiID;
        }

        public String getInvestigatorLiCheckListSubmittedDate() {
            return investigatorLiCheckListSubmittedDate;
        }

        public void setInvestigatorLiCheckListSubmittedDate(String investigatorLiCheckListSubmittedDate) {
            this.investigatorLiCheckListSubmittedDate = investigatorLiCheckListSubmittedDate;
        }

        public Integer getInvestigatorLiCheckListSubmittedByUserID() {
            return investigatorLiCheckListSubmittedByUserID;
        }

        public void setInvestigatorLiCheckListSubmittedByUserID(Integer investigatorLiCheckListSubmittedByUserID) {
            this.investigatorLiCheckListSubmittedByUserID = investigatorLiCheckListSubmittedByUserID;
        }

        public String getInvestigatorLiCheckListSubmittedByUserName() {
            return investigatorLiCheckListSubmittedByUserName;
        }

        public void setInvestigatorLiCheckListSubmittedByUserName(String investigatorLiCheckListSubmittedByUserName) {
            this.investigatorLiCheckListSubmittedByUserName = investigatorLiCheckListSubmittedByUserName;
        }

        public String getInvestigatorLiCheckListEntryDate() {
            return investigatorLiCheckListEntryDate;
        }

        public void setInvestigatorLiCheckListEntryDate(String investigatorLiCheckListEntryDate) {
            this.investigatorLiCheckListEntryDate = investigatorLiCheckListEntryDate;
        }

        public String getInsuredName() {
            return insuredName;
        }

        public void setInsuredName(String insuredName) {
            this.insuredName = insuredName;
        }

        public String getInsuredFatherOrHusbandName() {
            return insuredFatherOrHusbandName;
        }

        public void setInsuredFatherOrHusbandName(String insuredFatherOrHusbandName) {
            this.insuredFatherOrHusbandName = insuredFatherOrHusbandName;
        }

        public Integer getInsuredAge() {
            return insuredAge;
        }

        public void setInsuredAge(Integer insuredAge) {
            this.insuredAge = insuredAge;
        }

        public String getInsuredCompany() {
            return insuredCompany;
        }

        public void setInsuredCompany(String insuredCompany) {
            this.insuredCompany = insuredCompany;
        }

        public String getClaimantName() {
            return claimantName;
        }

        public void setClaimantName(String claimantName) {
            this.claimantName = claimantName;
        }

        public Integer getClaimantAge() {
            return claimantAge;
        }

        public void setClaimantAge(Integer claimantAge) {
            this.claimantAge = claimantAge;
        }

        public Boolean getOccupationDetailsConfirmed() {
            return isOccupationDetailsConfirmed;
        }

        public void setOccupationDetailsConfirmed(Boolean occupationDetailsConfirmed) {
            isOccupationDetailsConfirmed = occupationDetailsConfirmed;
        }

        public String getOccupationDetails() {
            return occupationDetails;
        }

        public void setOccupationDetails(String occupationDetails) {
            this.occupationDetails = occupationDetails;
        }

        public Boolean getAgeConfirmed() {
            return isAgeConfirmed;
        }

        public void setAgeConfirmed(Boolean ageConfirmed) {
            isAgeConfirmed = ageConfirmed;
        }

        public String getAboutAgeConfirmed() {
            return aboutAgeConfirmed;
        }

        public void setAboutAgeConfirmed(String aboutAgeConfirmed) {
            this.aboutAgeConfirmed = aboutAgeConfirmed;
        }

        public List<InvLifeInsuCheckListNeighbours> getLstInvLifeInsuCheckListNeighbours() {
            return lstInvLifeInsuCheckListNeighbours;
        }

        public void setLstInvLifeInsuCheckListNeighbours(List<InvLifeInsuCheckListNeighbours> lstInvLifeInsuCheckListNeighbours) {
            this.lstInvLifeInsuCheckListNeighbours = lstInvLifeInsuCheckListNeighbours;
        }

        public String getAnganbadiName() {
            return anganbadiName;
        }

        public void setAnganbadiName(String anganbadiName) {
            this.anganbadiName = anganbadiName;
        }

        public String getAnganbadiContactNumber() {
            return anganbadiContactNumber;
        }

        public void setAnganbadiContactNumber(String anganbadiContactNumber) {
            this.anganbadiContactNumber = anganbadiContactNumber;
        }

        public String getAshaBahuName() {
            return ashaBahuName;
        }

        public void setAshaBahuName(String ashaBahuName) {
            this.ashaBahuName = ashaBahuName;
        }

        public String getAshaBahuContactNo() {
            return ashaBahuContactNo;
        }

        public void setAshaBahuContactNo(String ashaBahuContactNo) {
            this.ashaBahuContactNo = ashaBahuContactNo;
        }

        public String getGramPradhanName() {
            return gramPradhanName;
        }

        public void setGramPradhanName(String gramPradhanName) {
            this.gramPradhanName = gramPradhanName;
        }

        public String getGramPradhanContactNo() {
            return gramPradhanContactNo;
        }

        public void setGramPradhanContactNo(String gramPradhanContactNo) {
            this.gramPradhanContactNo = gramPradhanContactNo;
        }

        public String getSabhasadWardName() {
            return sabhasadWardName;
        }

        public void setSabhasadWardName(String sabhasadWardName) {
            this.sabhasadWardName = sabhasadWardName;
        }

        public String getSabhasadWardContactNo() {
            return sabhasadWardContactNo;
        }

        public void setSabhasadWardContactNo(String sabhasadWardContactNo) {
            this.sabhasadWardContactNo = sabhasadWardContactNo;
        }

        public String getVillageSecretaryName() {
            return villageSecretaryName;
        }

        public void setVillageSecretaryName(String villageSecretaryName) {
            this.villageSecretaryName = villageSecretaryName;
        }

        public String getVillageSecretaryContactNo() {
            return villageSecretaryContactNo;
        }

        public void setVillageSecretaryContactNo(String villageSecretaryContactNo) {
            this.villageSecretaryContactNo = villageSecretaryContactNo;
        }

        public String getNagarNigamAuthorityName() {
            return nagarNigamAuthorityName;
        }

        public void setNagarNigamAuthorityName(String nagarNigamAuthorityName) {
            this.nagarNigamAuthorityName = nagarNigamAuthorityName;
        }

        public String getNagarNigamAuthorityContactNo() {
            return nagarNigamAuthorityContactNo;
        }

        public void setNagarNigamAuthorityContactNo(String nagarNigamAuthorityContactNo) {
            this.nagarNigamAuthorityContactNo = nagarNigamAuthorityContactNo;
        }

        public List<InvLifeInsuCheckListMedicalStore> getLstInvLifeInsuCheckListMedicalStore() {
            return lstInvLifeInsuCheckListMedicalStore;
        }

        public void setLstInvLifeInsuCheckListMedicalStore(List<InvLifeInsuCheckListMedicalStore> lstInvLifeInsuCheckListMedicalStore) {
            this.lstInvLifeInsuCheckListMedicalStore = lstInvLifeInsuCheckListMedicalStore;
        }

        public String getpHCCHCDistrictHospitalAddress() {
            return pHCCHCDistrictHospitalAddress;
        }

        public void setpHCCHCDistrictHospitalAddress(String pHCCHCDistrictHospitalAddress) {
            this.pHCCHCDistrictHospitalAddress = pHCCHCDistrictHospitalAddress;
        }

        public String getpHCCHCDistrictHospitalAddressContactNo() {
            return pHCCHCDistrictHospitalAddressContactNo;
        }

        public void setpHCCHCDistrictHospitalAddressContactNo(String pHCCHCDistrictHospitalAddressContactNo) {
            this.pHCCHCDistrictHospitalAddressContactNo = pHCCHCDistrictHospitalAddressContactNo;
        }

        public List<InvLifeInsuCheckListHospitalCheck> getLstInvLifeInsuCheckListHospitalCheck() {
            return lstInvLifeInsuCheckListHospitalCheck;
        }

        public void setLstInvLifeInsuCheckListHospitalCheck(List<InvLifeInsuCheckListHospitalCheck> lstInvLifeInsuCheckListHospitalCheck) {
            this.lstInvLifeInsuCheckListHospitalCheck = lstInvLifeInsuCheckListHospitalCheck;
        }

        public String getPoliceStationCheck() {
            return policeStationCheck;
        }

        public void setPoliceStationCheck(String policeStationCheck) {
            this.policeStationCheck = policeStationCheck;
        }

        public String getCremationPlaceDetails() {
            return cremationPlaceDetails;
        }

        public void setCremationPlaceDetails(String cremationPlaceDetails) {
            this.cremationPlaceDetails = cremationPlaceDetails;
        }

        public String getFamilyMemberDetails() {
            return familyMemberDetails;
        }

        public void setFamilyMemberDetails(String familyMemberDetails) {
            this.familyMemberDetails = familyMemberDetails;
        }

        public String getNarrationOfTheCase() {
            return narrationOfTheCase;
        }

        public void setNarrationOfTheCase(String narrationOfTheCase) {
            this.narrationOfTheCase = narrationOfTheCase;
        }

        public String getExceptionInSaving() {
            return exceptionInSaving;
        }

        public void setExceptionInSaving(String exceptionInSaving) {
            this.exceptionInSaving = exceptionInSaving;
        }

        public Boolean getLiCheckListDataSaved() {
            return isLiCheckListDataSaved;
        }

        public void setLiCheckListDataSaved(Boolean liCheckListDataSaved) {
            isLiCheckListDataSaved = liCheckListDataSaved;
        }
    }

    public static class InvLifeInsuCheckListHospitalCheck implements Serializable {
        @SerializedName("InvLifeInsuCheckListHospitalCheck_ID")
        @Expose
        private Integer InvLifeInsuCheckListHospitalCheck_ID;
        @SerializedName("InvLifeInsuCheckListHospitalCheck_ChkListID")
        @Expose
        private Integer InvLifeInsuCheckListHospitalCheck_ChkListID;
        @SerializedName("InvLifeInsuCheckListHospitalCheck_InvId")
        @Expose
        private Integer InvLifeInsuCheckListHospitalCheck_InvId;
        @SerializedName("InvLifeInsuCheckListHospitalCheck_LiID")
        @Expose
        private Integer InvLifeInsuCheckListHospitalCheck_LiID;
        @SerializedName("HospitalCheckNameAddress")
        @Expose
        private String HospitalCheckNameAddress;
        @SerializedName("HospitalCheckNameContactNo")
        @Expose
        private String HospitalCheckNameContactNo;
        @SerializedName("EntryOnDate")
        @Expose
        private String EntryOnDate;
        @SerializedName("EntryByUserID")
        @Expose
        private Integer EntryByUserID;
        @SerializedName("EntryByUserName")
        @Expose
        private String EntryByUserName;

        public Integer getInvLifeInsuCheckListHospitalCheck_ID() {
            return InvLifeInsuCheckListHospitalCheck_ID;
        }

        public void setInvLifeInsuCheckListHospitalCheck_ID(Integer invLifeInsuCheckListHospitalCheck_ID) {
            InvLifeInsuCheckListHospitalCheck_ID = invLifeInsuCheckListHospitalCheck_ID;
        }

        public Integer getInvLifeInsuCheckListHospitalCheck_ChkListID() {
            return InvLifeInsuCheckListHospitalCheck_ChkListID;
        }

        public void setInvLifeInsuCheckListHospitalCheck_ChkListID(Integer invLifeInsuCheckListHospitalCheck_ChkListID) {
            InvLifeInsuCheckListHospitalCheck_ChkListID = invLifeInsuCheckListHospitalCheck_ChkListID;
        }

        public Integer getInvLifeInsuCheckListHospitalCheck_InvId() {
            return InvLifeInsuCheckListHospitalCheck_InvId;
        }

        public void setInvLifeInsuCheckListHospitalCheck_InvId(Integer invLifeInsuCheckListHospitalCheck_InvId) {
            InvLifeInsuCheckListHospitalCheck_InvId = invLifeInsuCheckListHospitalCheck_InvId;
        }

        public Integer getInvLifeInsuCheckListHospitalCheck_LiID() {
            return InvLifeInsuCheckListHospitalCheck_LiID;
        }

        public void setInvLifeInsuCheckListHospitalCheck_LiID(Integer invLifeInsuCheckListHospitalCheck_LiID) {
            InvLifeInsuCheckListHospitalCheck_LiID = invLifeInsuCheckListHospitalCheck_LiID;
        }

        public String getHospitalCheckNameAddress() {
            return HospitalCheckNameAddress;
        }

        public void setHospitalCheckNameAddress(String hospitalCheckNameAddress) {
            HospitalCheckNameAddress = hospitalCheckNameAddress;
        }

        public String getHospitalCheckNameContactNo() {
            return HospitalCheckNameContactNo;
        }

        public void setHospitalCheckNameContactNo(String hospitalCheckNameContactNo) {
            HospitalCheckNameContactNo = hospitalCheckNameContactNo;
        }

        public String getEntryOnDate() {
            return EntryOnDate;
        }

        public void setEntryOnDate(String entryOnDate) {
            EntryOnDate = entryOnDate;
        }

        public Integer getEntryByUserID() {
            return EntryByUserID;
        }

        public void setEntryByUserID(Integer entryByUserID) {
            EntryByUserID = entryByUserID;
        }

        public String getEntryByUserName() {
            return EntryByUserName;
        }

        public void setEntryByUserName(String entryByUserName) {
            EntryByUserName = entryByUserName;
        }
    }

    public static class InvLifeInsuCheckListMedicalStore implements Serializable {
        @SerializedName("InvLifeInsuCheckListMedicalStore_ID")
        @Expose
        private Integer InvLifeInsuCheckListMedicalStore_ID;
        @SerializedName("InvLifeInsuCheckListMedicalStore_CheckListID")
        @Expose
        private Integer InvLifeInsuCheckListMedicalStore_CheckListID;
        @SerializedName("InvLifeInsuCheckListMedicalStore_InvID")
        @Expose
        private Integer InvLifeInsuCheckListMedicalStore_InvID;
        @SerializedName("InvLifeInsuCheckListMedicalStore_LiId")
        @Expose
        private Integer InvLifeInsuCheckListMedicalStore_LiId;
        @SerializedName("MedicalStoreCheckName")
        @Expose
        private String MedicalStoreCheckName;
        @SerializedName("MedicalStoreCheckAddress")
        @Expose
        private String MedicalStoreCheckAddress;
        @SerializedName("EntryOnDate")
        @Expose
        private String EntryOnDate;
        @SerializedName("EntryByUserID")
        @Expose
        private Integer EntryByUserID;
        @SerializedName("EntryByUserName")
        @Expose
        private String EntryByUserName;

        public Integer getInvLifeInsuCheckListMedicalStore_ID() {
            return InvLifeInsuCheckListMedicalStore_ID;
        }

        public void setInvLifeInsuCheckListMedicalStore_ID(Integer invLifeInsuCheckListMedicalStore_ID) {
            InvLifeInsuCheckListMedicalStore_ID = invLifeInsuCheckListMedicalStore_ID;
        }

        public Integer getInvLifeInsuCheckListMedicalStore_CheckListID() {
            return InvLifeInsuCheckListMedicalStore_CheckListID;
        }

        public void setInvLifeInsuCheckListMedicalStore_CheckListID(Integer invLifeInsuCheckListMedicalStore_CheckListID) {
            InvLifeInsuCheckListMedicalStore_CheckListID = invLifeInsuCheckListMedicalStore_CheckListID;
        }

        public Integer getInvLifeInsuCheckListMedicalStore_InvID() {
            return InvLifeInsuCheckListMedicalStore_InvID;
        }

        public void setInvLifeInsuCheckListMedicalStore_InvID(Integer invLifeInsuCheckListMedicalStore_InvID) {
            InvLifeInsuCheckListMedicalStore_InvID = invLifeInsuCheckListMedicalStore_InvID;
        }

        public Integer getInvLifeInsuCheckListMedicalStore_LiId() {
            return InvLifeInsuCheckListMedicalStore_LiId;
        }

        public void setInvLifeInsuCheckListMedicalStore_LiId(Integer invLifeInsuCheckListMedicalStore_LiId) {
            InvLifeInsuCheckListMedicalStore_LiId = invLifeInsuCheckListMedicalStore_LiId;
        }

        public String getMedicalStoreCheckName() {
            return MedicalStoreCheckName;
        }

        public void setMedicalStoreCheckName(String medicalStoreCheckName) {
            MedicalStoreCheckName = medicalStoreCheckName;
        }

        public String getMedicalStoreCheckAddress() {
            return MedicalStoreCheckAddress;
        }

        public void setMedicalStoreCheckAddress(String medicalStoreCheckAddress) {
            MedicalStoreCheckAddress = medicalStoreCheckAddress;
        }

        public String getEntryOnDate() {
            return EntryOnDate;
        }

        public void setEntryOnDate(String entryOnDate) {
            EntryOnDate = entryOnDate;
        }

        public Integer getEntryByUserID() {
            return EntryByUserID;
        }

        public void setEntryByUserID(Integer entryByUserID) {
            EntryByUserID = entryByUserID;
        }

        public String getEntryByUserName() {
            return EntryByUserName;
        }

        public void setEntryByUserName(String entryByUserName) {
            EntryByUserName = entryByUserName;
        }
    }

    public static class InvLifeInsuCheckListNeighbours implements Serializable {
        @SerializedName("InvLifeInsuCheckListNeighbours_ID")
        @Expose
        private Integer InvLifeInsuCheckListNeighbours_ID;
        @SerializedName("NeighboursInvestigatorLiCheckList_ID")
        @Expose
        private Integer NeighboursInvestigatorLiCheckList_ID;
        @SerializedName("InvLifeInsuCheckListNeighbours_InvID")
        @Expose
        private Integer InvLifeInsuCheckListNeighbours_InvID;
        @SerializedName("InvLifeInsuCheckListNeighbours_LiID")
        @Expose
        private Integer InvLifeInsuCheckListNeighbours_LiID;
        @SerializedName("Neighbours_Name")
        @Expose
        private String Neighbours_Name;
        @SerializedName("Neighbours_ContactNumber")
        @Expose
        private String Neighbours_ContactNumber;
        @SerializedName("EntryOnDate")
        @Expose
        private String EntryOnDate;
        @SerializedName("EntryByUserID")
        @Expose
        private Integer EntryByUserID;
        @SerializedName("EntryByUserName")
        @Expose
        private String EntryByUserName;

        public Integer getInvLifeInsuCheckListNeighbours_ID() {
            return InvLifeInsuCheckListNeighbours_ID;
        }

        public void setInvLifeInsuCheckListNeighbours_ID(Integer invLifeInsuCheckListNeighbours_ID) {
            InvLifeInsuCheckListNeighbours_ID = invLifeInsuCheckListNeighbours_ID;
        }

        public Integer getNeighboursInvestigatorLiCheckList_ID() {
            return NeighboursInvestigatorLiCheckList_ID;
        }

        public void setNeighboursInvestigatorLiCheckList_ID(Integer neighboursInvestigatorLiCheckList_ID) {
            NeighboursInvestigatorLiCheckList_ID = neighboursInvestigatorLiCheckList_ID;
        }

        public Integer getInvLifeInsuCheckListNeighbours_InvID() {
            return InvLifeInsuCheckListNeighbours_InvID;
        }

        public void setInvLifeInsuCheckListNeighbours_InvID(Integer invLifeInsuCheckListNeighbours_InvID) {
            InvLifeInsuCheckListNeighbours_InvID = invLifeInsuCheckListNeighbours_InvID;
        }

        public Integer getInvLifeInsuCheckListNeighbours_LiID() {
            return InvLifeInsuCheckListNeighbours_LiID;
        }

        public void setInvLifeInsuCheckListNeighbours_LiID(Integer invLifeInsuCheckListNeighbours_LiID) {
            InvLifeInsuCheckListNeighbours_LiID = invLifeInsuCheckListNeighbours_LiID;
        }

        public String getNeighbours_Name() {
            return Neighbours_Name;
        }

        public void setNeighbours_Name(String neighbours_Name) {
            Neighbours_Name = neighbours_Name;
        }

        public String getNeighbours_ContactNumber() {
            return Neighbours_ContactNumber;
        }

        public void setNeighbours_ContactNumber(String neighbours_ContactNumber) {
            Neighbours_ContactNumber = neighbours_ContactNumber;
        }

        public String getEntryOnDate() {
            return EntryOnDate;
        }

        public void setEntryOnDate(String entryOnDate) {
            EntryOnDate = entryOnDate;
        }

        public Integer getEntryByUserID() {
            return EntryByUserID;
        }

        public void setEntryByUserID(Integer entryByUserID) {
            EntryByUserID = entryByUserID;
        }

        public String getEntryByUserName() {
            return EntryByUserName;
        }

        public void setEntryByUserName(String entryByUserName) {
            EntryByUserName = entryByUserName;
        }

    }
}
