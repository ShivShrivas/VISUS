package com.org.visus.activity.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VISUS_SQLiteHelper extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String dbName = "visus.db";
    public static String tblMyService = "tblMyService";
    public static String tblTotalCases = "tblTotalCases";
    public static String tblTotalCases_Hold = "tblTotalCases_Hold";


    public static String tblMyAssignmentData = "tblMyAssignmentData";
    public static String tblMyAssignmentData_Hold = "tblMyAssignmentData_Hold";


    public static String tblInvestigatorObj = "tblInvestigatorObj";
    public static String tblRequreActionsData = "tblRequreActionsData";
    public static String tblPostInvestigatorActionData = "tblPostInvestigatorActionData";
    public static String tblPostInvestigatorActionDataPhoto = "tblPostInvestigatorActionDataPhoto";


    public static String common_id = "common_id";
    public static String VisusServicesID = "VisusServicesID";
    public static String VisusServicesText = "VisusServicesText";
    public static String VisusServicesDesc = "VisusServicesDesc";
    public static String IsVisusActiveServices = "IsVisusActiveServices";


    public static String InvestigationUsedForID = "InvestigationUsedForID";
    public static String InvestigationUsedForText = "InvestigationUsedForText";
    public static String IsActiveInvestigationUsedFor = "IsActiveInvestigationUsedFor";
    public static String InsvestigatorID = "InsvestigatorID";
    public static String InsvestigatorCode = "InsvestigatorCode";
    public static String InsvestigatorName = "InsvestigatorName";
    public static String TotalCase = "TotalCase";
    public static String TotalSubmittedCases = "TotalSubmittedCases";
    public static String TotalPendingCases = "TotalPendingCases";

    public static String InsuranceDataID = "InsuranceDataID";
    public static String VisusServicesId = "VisusServicesID";
    public static String VisusServicesText_My_AssignMent = "VisusServicesText_My_AssignMent";
    public static String InsuranceAssignedOnDate = "InsuranceAssignedOnDate";
    public static String InsuranceSubmittedOnDate = "InsuranceSubmittedOnDate";
    public static String TATForInvestigator = "TATForInvestigator";
    public static String ClaimNumber = "ClaimNumber";
    public static String PolicyNumber = "PolicyNumber";
    public static String InsuranceCompanyName = "InsuranceCompanyName";
    public static String CompanyName = "CompanyName";
    public static String ProductID = "ProductID";
    public static String ProductName = "ProductName";
    public static String CaseTitle = "CaseTitle";
    public static String DateOfAccident = "DateOfAccident";
    public static String InvInsuranceRelID = "InvInsuranceRelID";
    public static String ProductSubCategory = "ProductSubCategory";

    public static String INV_ID = "INV_ID";
    public static String INV_Name = "INV_Name";
    public static String ISActive = "ISActive";
    public static String INV_Father_Name = "INV_Father_Name";
    public static String INV_Contact_Number1 = "INV_Contact_Number1";
    public static String INV_Contact_Number2 = "INV_Contact_Number2";
    public static String INV_code = "INV_code";
    public static String JoiningDate = "JoiningDate";
    public static String ReliveDate = "ReliveDate";
    public static String DOB = "DOB";
    public static String Email = "Email";
    public static String PAN_Number = "PAN_Number";


    public static String InvestigatorReqActivity_ID = "InvestigatorReqActivity_ID";
    public static String InsuranceClaimTypeID = "InsuranceClaimTypeID";
    public static String InsuranceClaimTypeText = "InsuranceClaimTypeText";
    public static String InvestigatorReqActivity_Text = "InvestigatorReqActivity_Text";
    public static String IsSelfieRequired = "IsSelfieRequired";
    public static String IsCompulsory = "IsCompulsory";
    public static String IsPhotoRequired = "IsPhotoRequired";
    public static String MinimumPhotoRequired = "MinimumPhotoRequired";
    public static String MaximumPhotoRequired = "MaximumPhotoRequired";
    public static String IsVedioFileRequired = "IsVedioFileRequired";
    public static String MinumumVedioFileRequired = "MinumumVedioFileRequired";
    public static String MaximumVedioFileRequired = "MaximumVedioFileRequired";
    public static String MaxmumSizeOfVedioFileInMB = "MaxmumSizeOfVedioFileInMB";
    public static String IsActiveInvestigatorReqActivity = "IsActiveInvestigatorReqActivity";

    public static String Token = "Token";
    public static String OriginalFileName = "OriginalFileName";
    public static String ServiceTypeID = "ServiceTypeID";
    public static String ServiceID = "ServiceID";
    public static String InvID = "InvID";
    public static String Comments = "Comments";
    public static String ActionID = "ActionID";
    public static String Latitude = "Latitude";
    public static String Longitude = "Longitude";
    public static String CellAddress = "CellAddress";
    public static String InvInsuranceRelID_SAVING = "InvInsuranceRelID_SAVING";
    public static String isSyncedRequreActionsData = "isSyncedRequreActionsData";

    public static String InsuredOrClaimentName = "InsuredOrClaimentName";
    public static String FileAttachmentCountByInvestigator = "FileAttachmentCountByInvestigator";


    private final String create_tblPostInvestigatorActionData = "create table " + tblPostInvestigatorActionData + " (" + common_id + " integer primary key autoincrement ," + OriginalFileName + " text," + ServiceTypeID + " text," + ServiceID + " text," + InvID + " text," + Comments + " text," + ActionID + " text," + Latitude + " text," + Longitude + " text," + CellAddress + " text," + Token + " text," + InvInsuranceRelID_SAVING + " text," + isSyncedRequreActionsData + " text );";

    private final String create_tblRequreActionsData = "create table " + tblRequreActionsData + " (" + common_id + " integer primary key autoincrement ," + InvestigatorReqActivity_ID + " text," + InsuranceClaimTypeID + " text," + InsuranceClaimTypeText + " text," + InvestigatorReqActivity_Text + " text," + IsSelfieRequired + " text," + IsCompulsory + " text," + IsPhotoRequired + " text," + MinimumPhotoRequired + " text," + MaximumPhotoRequired + " text," + IsVedioFileRequired + " text," + MinumumVedioFileRequired + " text," + MaximumVedioFileRequired + " text," + MaxmumSizeOfVedioFileInMB + " text," + IsActiveInvestigatorReqActivity + " text );";


    private final String create_tblMyAssignmentData = "create table " + tblMyAssignmentData + " (" + common_id + " integer primary key autoincrement ," + InsuranceDataID + " text," + VisusServicesId + " text," + VisusServicesText_My_AssignMent + " text," + InsuranceAssignedOnDate + " text," + InsuranceSubmittedOnDate + " text," + TATForInvestigator + " text," + ClaimNumber + " text," + PolicyNumber + " text," + InsuranceCompanyName + " text," + CompanyName + " text," + ProductID + " text," + ProductName + " text," + CaseTitle + " text," + DateOfAccident + " text," + InvInsuranceRelID + " text," + ProductSubCategory + " text," + InsuredOrClaimentName + " text," + FileAttachmentCountByInvestigator + " text );";


    private final String create_tblMyAssignmentData_Hold = "create table " + tblMyAssignmentData_Hold + " (" + common_id + " integer primary key autoincrement ," + InsuranceDataID + " text," + VisusServicesId + " text," + VisusServicesText_My_AssignMent + " text," + InsuranceAssignedOnDate + " text," + InsuranceSubmittedOnDate + " text," + TATForInvestigator + " text," + ClaimNumber + " text," + PolicyNumber + " text," + InsuranceCompanyName + " text," + CompanyName + " text," + ProductID + " text," + ProductName + " text," + CaseTitle + " text," + DateOfAccident + " text," + InvInsuranceRelID + " text," + ProductSubCategory + " text," + InsuredOrClaimentName + " text," + FileAttachmentCountByInvestigator + " text );";


    private final String create_tblInvestigatorObj = "create table " + tblInvestigatorObj + " (" + common_id + " integer primary key autoincrement ," + INV_ID + " text," + INV_Name + " text," + ISActive + " text," + INV_Father_Name + " text," + INV_Contact_Number1 + " text," + INV_Contact_Number2 + " text," + INV_code + " text," + JoiningDate + " text," + ReliveDate + " text," + DOB + " text," + Email + " text," + PAN_Number + " text );";

    private final String create_tblMyServices = "create table " + tblMyService + " (" + common_id + " integer primary key autoincrement ," + VisusServicesID + " integer," + VisusServicesText + " text," + VisusServicesDesc + " text," + IsVisusActiveServices + " boolean );";

    private final String create_tblMyTotalCase = "create table " + tblTotalCases + " (" + common_id + " integer primary key autoincrement ," + InvestigationUsedForID + " integer," + InvestigationUsedForText + " text," + IsActiveInvestigationUsedFor + " text," + InsvestigatorID + " text," + InsvestigatorCode + " text," + InsvestigatorName + " text," + TotalCase + " text," + TotalSubmittedCases + " text," + TotalPendingCases + " text );";


    private final String create_tblMyTotalCase_Hold = "create table " + tblTotalCases_Hold + " (" + common_id + " integer primary key autoincrement ," + InvestigationUsedForID + " integer," + InvestigationUsedForText + " text," + IsActiveInvestigationUsedFor + " text," + InsvestigatorID + " text," + InsvestigatorCode + " text," + InsvestigatorName + " text," + TotalCase + " text," + TotalSubmittedCases + " text," + TotalPendingCases + " text );";

    public static String InvestigatorCaseActivityPhotoServerID = "InvestigatorCaseActivityPhotoServerID";
    public static String InvestigatorCaseActivity_ID = "InvestigatorCaseActivity_ID";
    public static String InvestigatorCaseActivity_CaseInsuranceID = "InvestigatorCaseActivity_CaseInsuranceID";
    public static String InvestigatorCaseActivity_InvID = "InvestigatorCaseActivity_InvID";
    public static String InvestigatorRequiredActivityID = "InvestigatorRequiredActivityID";
    public static String ObjInvReqActivity = "ObjInvReqActivity";
    public static String UniqueFileName = "UniqueFileName";
    public static String ActivityFilePath = "ActivityFilePath";
    public static String FileSubmittionOnDate = "FileSubmittionOnDate";
    public static String InvestigatorObj = "InvestigatorObj";
    public static String ExceptionIfAny = "ExceptionIfAny";
    public static String InvestigatorComments = "InvestigatorComments";
    public static String LatitudeAtClickingPhoto = "LatitudeAtClickingPhoto";
    public static String LongitudeAtClickingPhoto = "LongitudeAtClickingPhoto";
    public static String AddressAtClickingPhoto = "AddressAtClickingPhoto";
    public static String InvestigatorCaseActivity_ClientD = "InvestigatorCaseActivity_ClientD";


    private final String create_tblPostInvestigatorActionDataPhoto = "create table " + tblPostInvestigatorActionDataPhoto + " (" + common_id + " integer primary key autoincrement ,"
            + InvestigatorCaseActivity_ClientD + " text,"
            + InvestigatorCaseActivityPhotoServerID + " text,"
            + InvestigatorCaseActivity_ID + " text,"
            + InvestigatorCaseActivity_CaseInsuranceID + " text,"
            + InvestigatorCaseActivity_InvID + " text,"
            + InvestigatorRequiredActivityID + " text,"
            + ObjInvReqActivity + " text,"
            + OriginalFileName + " text,"
            + UniqueFileName + " text,"
            + ActivityFilePath + " text,"
            + FileSubmittionOnDate + " text,"
            + VisusServicesID + " text,"
            + VisusServicesText + " text,"
            + InvestigatorObj + " text,"
            + InsuranceAssignedOnDate + " text,"
            + InsuranceSubmittedOnDate + " text,"
            + TATForInvestigator + " text,"
            + ClaimNumber + " text,"
            + PolicyNumber + " text,"
            + InsuranceCompanyName + " text,"
            + CompanyName + " text,"
            + ExceptionIfAny + " text,"
            + InvestigatorComments + " " + "text,"
            + LatitudeAtClickingPhoto + " text,"
            + LongitudeAtClickingPhoto + " text,"
            + AddressAtClickingPhoto + " text,"
            + InvInsuranceRelID + " text,"
            + Token + " text,"
            + isSyncedRequreActionsData + " text );";


    public static String DeviceLocation_ID = "DeviceLocation_ID";
    public static String DeviceLocation_DeviceID = "DeviceLocation_DeviceID";
    public static String DeviceLocation_InvID = "DeviceLocation_InvID";
    public static String DeviceLocation_Latitude = "DeviceLocation_Latitude";
    public static String DeviceLocation_Longitude = "DeviceLocation_Longitude";
    public static String DeviceLocation_GeoAddress = "DeviceLocation_GeoAddress";
    public static String DeviceLocation_SaveOnDate = "DeviceLocation_SaveOnDate";
    public static String IsLatestDeviceLocation = "IsLatestDeviceLocation";

    public static String IsSavedDeviceLocation = "IsSavedDeviceLocation";

    public static String tbl_DeviceInvLocation = "tbl_DeviceInvLocation";
    public static String isSyncedDeviceInvLocation = "isSyncedDeviceInvLocation";


    private final String create_tbl_DeviceInvLocation = "create table " + tbl_DeviceInvLocation + " (" + common_id + " integer primary key autoincrement ," +
            DeviceLocation_ID + " text,"
            + DeviceLocation_DeviceID + " text,"
            + DeviceLocation_InvID + " text,"
            + DeviceLocation_Latitude + " text,"
            + DeviceLocation_Longitude + " text,"
            + DeviceLocation_GeoAddress + " text,"
            + DeviceLocation_SaveOnDate + " text,"
            + IsLatestDeviceLocation + " text,"
            + IsSavedDeviceLocation + " text,"
            + ExceptionIfAny + " text,"
            + isSyncedDeviceInvLocation + " text );";


    public VISUS_SQLiteHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_tblMyTotalCase);
        sqLiteDatabase.execSQL(create_tblMyServices);
        sqLiteDatabase.execSQL(create_tblMyAssignmentData);
        sqLiteDatabase.execSQL(create_tblInvestigatorObj);
        sqLiteDatabase.execSQL(create_tblRequreActionsData);
        sqLiteDatabase.execSQL(create_tblPostInvestigatorActionData);
        sqLiteDatabase.execSQL(create_tbl_DeviceInvLocation);
        sqLiteDatabase.execSQL(create_tblMyAssignmentData_Hold);
        sqLiteDatabase.execSQL(create_tblMyTotalCase_Hold);
        sqLiteDatabase.execSQL(create_tblPostInvestigatorActionDataPhoto);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
