package com.org.visus.models;

import java.io.Serializable;

public class ActionUploadedFile implements Serializable {


    public int InvestigatorCaseActivityPhoto_ID;
    public int PhotoInvestigatorCaseActivityID;
    public String OriginalFileName;
    public String UniqueFileName;
    public String ActivityFilePath;

    public int getInvestigatorCaseActivityPhoto_ID() {
        return InvestigatorCaseActivityPhoto_ID;
    }

    public void setInvestigatorCaseActivityPhoto_ID(int investigatorCaseActivityPhoto_ID) {
        InvestigatorCaseActivityPhoto_ID = investigatorCaseActivityPhoto_ID;
    }

    public int getPhotoInvestigatorCaseActivityID() {
        return PhotoInvestigatorCaseActivityID;
    }

    public void setPhotoInvestigatorCaseActivityID(int photoInvestigatorCaseActivityID) {
        PhotoInvestigatorCaseActivityID = photoInvestigatorCaseActivityID;
    }

    public String getOriginalFileName() {
        return OriginalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        OriginalFileName = originalFileName;
    }

    public String getUniqueFileName() {
        return UniqueFileName;
    }

    public void setUniqueFileName(String uniqueFileName) {
        UniqueFileName = uniqueFileName;
    }

    public String getActivityFilePath() {
        return ActivityFilePath;
    }

    public void setActivityFilePath(String activityFilePath) {
        ActivityFilePath = activityFilePath;
    }
}
