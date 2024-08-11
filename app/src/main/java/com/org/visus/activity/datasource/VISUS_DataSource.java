package com.org.visus.activity.datasource;

import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.InsvestigatorCode;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.InsvestigatorID;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.InsvestigatorName;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.InvestigationUsedForID;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.InvestigationUsedForText;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.IsActiveInvestigationUsedFor;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.IsVisusActiveServices;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.TotalCase;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.TotalPendingCases;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.TotalSubmittedCases;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.VisusServicesDesc;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.VisusServicesID;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.VisusServicesText;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.tblMyAssignmentData;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.tblMyAssignmentData_Hold;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.tblMyService;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.tblTotalCases;
import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.tblTotalCases_Hold;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.org.visus.activity.sqlite.VISUS_SQLiteHelper;
import com.org.visus.models.DeviceInvLocation;
import com.org.visus.models.GetServices;

import com.org.visus.models.MyAssignment;

import com.org.visus.models.RequreActions;
import com.org.visus.models.SaveInvestigatorAction;
import com.org.visus.models.SaveInvestigatorActionOnlyData;
import com.org.visus.models.TotalCases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VISUS_DataSource {
    public VISUS_SQLiteHelper visus_sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public VISUS_DataSource(Context context) {
        this.context = context;
        visus_sqLiteHelper = new VISUS_SQLiteHelper(context);
    }

    public void open() {
        sqLiteDatabase = visus_sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }

    public int delete_tblMyServices() {
        return sqLiteDatabase.delete(tblMyService, null, null);
    }

    public long insertMyServices(String visusServicesID, String visusServicesText, String visusServicesDesc, String isVisusActiveServices) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VisusServicesID, visusServicesID);
        values.put(VisusServicesText, visusServicesText);
        values.put(VisusServicesDesc, visusServicesDesc);
        values.put(IsVisusActiveServices, isVisusActiveServices);
        i = sqLiteDatabase.insert(tblMyService, null, values);
        return i;
    }

    @SuppressLint("Range")
    public List<GetServices.GetServicesData> getMyServises() {
        List<GetServices.GetServicesData> list = new ArrayList<>();
        GetServices.GetServicesData servicesData;
        String sql = "select * From " + tblMyService;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            servicesData = new GetServices.GetServicesData();
            servicesData.setVisusServicesID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VisusServicesID))));
            servicesData.setVisusServicesText(cursor.getString(cursor.getColumnIndex(VisusServicesText)));
            servicesData.setVisusServicesDesc(cursor.getString(cursor.getColumnIndex(VisusServicesDesc)));
            servicesData.setVisusActiveServices(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(IsVisusActiveServices))));
            list.add(servicesData);
            cursor.moveToNext();
        }
        return list;
    }

    public int delete_tblToalCases() {
        return sqLiteDatabase.delete(tblTotalCases, null, null);
    }

    public int delete_tblToalCases_Hold() {
        return sqLiteDatabase.delete(tblTotalCases_Hold, null, null);
    }


    public long insertToalCases(TotalCases.TotalCasesData totalCasesData) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(InvestigationUsedForID, totalCasesData.getInvestigationUsedForID().toString());
        values.put(InvestigationUsedForText, totalCasesData.getInvestigationUsedForText());
        values.put(IsActiveInvestigationUsedFor, totalCasesData.getActiveInvestigationUsedFor().toString());
        values.put(InsvestigatorID, totalCasesData.getInsvestigatorID().toString());
        values.put(InsvestigatorCode, totalCasesData.getInsvestigatorCode());
        values.put(InsvestigatorName, totalCasesData.getInsvestigatorName());
        values.put(TotalCase, totalCasesData.getTotalCases() + "");
        values.put(TotalSubmittedCases, totalCasesData.getTotalSubmittedCases() + "");
        values.put(TotalPendingCases, totalCasesData.getTotalPendingCases() + "");
        i = sqLiteDatabase.insert(tblTotalCases, null, values);
        return i;
    }


    public long insertToalCases_Hold(TotalCases.TotalCasesData totalCasesData) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(InvestigationUsedForID, totalCasesData.getInvestigationUsedForID().toString());
        values.put(InvestigationUsedForText, totalCasesData.getInvestigationUsedForText());
        values.put(IsActiveInvestigationUsedFor, totalCasesData.getActiveInvestigationUsedFor().toString());
        values.put(InsvestigatorID, totalCasesData.getInsvestigatorID().toString());
        values.put(InsvestigatorCode, totalCasesData.getInsvestigatorCode());
        values.put(InsvestigatorName, totalCasesData.getInsvestigatorName());
        values.put(TotalCase, totalCasesData.getTotalCases() + "");
        values.put(TotalSubmittedCases, totalCasesData.getTotalSubmittedCases() + "");
        values.put(TotalPendingCases, totalCasesData.getTotalPendingCases() + "");
        i = sqLiteDatabase.insert(tblTotalCases_Hold, null, values);
        return i;
    }


    @SuppressLint("Range")
    public List<TotalCases.TotalCasesData> getToalCasesAccordingToServiceID(String visusServiceID) {
        List<TotalCases.TotalCasesData> list = new ArrayList<>();
        TotalCases.TotalCasesData totalCasesData;
        String sql = "select * From " + tblTotalCases + " WHERE InvestigationUsedForID = " + visusServiceID;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            totalCasesData = new TotalCases.TotalCasesData();
            totalCasesData.setInvestigationUsedForID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InvestigationUsedForID))));
            totalCasesData.setInvestigationUsedForText(cursor.getString(cursor.getColumnIndex(InvestigationUsedForText)));
            totalCasesData.setActiveInvestigationUsedFor(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(IsActiveInvestigationUsedFor))));
            totalCasesData.setInsvestigatorID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InsvestigatorID))));
            totalCasesData.setInsvestigatorCode(cursor.getString(cursor.getColumnIndex(InsvestigatorCode)));
            totalCasesData.setInsvestigatorName(cursor.getString(cursor.getColumnIndex(InsvestigatorName)));
            totalCasesData.setTotalCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalCase))));
            totalCasesData.setTotalSubmittedCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalSubmittedCases))));
            totalCasesData.setTotalPendingCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalPendingCases))));
            list.add(totalCasesData);
            cursor.moveToNext();
        }
        return list;
    }

    @SuppressLint("Range")
    public List<TotalCases.TotalCasesData> getToalCasesAccordingToServiceID_Hold(String visusServiceID) {
        List<TotalCases.TotalCasesData> list = new ArrayList<>();
        TotalCases.TotalCasesData totalCasesData;
        String sql = "select * From " + tblTotalCases_Hold + " WHERE InvestigationUsedForID = " + visusServiceID;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            totalCasesData = new TotalCases.TotalCasesData();
            totalCasesData.setInvestigationUsedForID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InvestigationUsedForID))));
            totalCasesData.setInvestigationUsedForText(cursor.getString(cursor.getColumnIndex(InvestigationUsedForText)));
            totalCasesData.setActiveInvestigationUsedFor(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(IsActiveInvestigationUsedFor))));
            totalCasesData.setInsvestigatorID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InsvestigatorID))));
            totalCasesData.setInsvestigatorCode(cursor.getString(cursor.getColumnIndex(InsvestigatorCode)));
            totalCasesData.setInsvestigatorName(cursor.getString(cursor.getColumnIndex(InsvestigatorName)));
            totalCasesData.setTotalCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalCase))));
            totalCasesData.setTotalSubmittedCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalSubmittedCases))));
            totalCasesData.setTotalPendingCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalPendingCases))));
            list.add(totalCasesData);
            cursor.moveToNext();
        }
        return list;
    }

    @SuppressLint("Range")
    public List<TotalCases.TotalCasesData> getToalCases() {
        List<TotalCases.TotalCasesData> list = new ArrayList<>();
        TotalCases.TotalCasesData totalCasesData;
        String sql = "select * From " + tblTotalCases;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            totalCasesData = new TotalCases.TotalCasesData();
            totalCasesData.setInvestigationUsedForID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InvestigationUsedForID))));
            totalCasesData.setInvestigationUsedForText(cursor.getString(cursor.getColumnIndex(InvestigationUsedForText)));
            totalCasesData.setActiveInvestigationUsedFor(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(IsActiveInvestigationUsedFor))));
            totalCasesData.setInsvestigatorID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InsvestigatorID))));
            totalCasesData.setInsvestigatorCode(cursor.getString(cursor.getColumnIndex(InsvestigatorCode)));
            totalCasesData.setInsvestigatorName(cursor.getString(cursor.getColumnIndex(InsvestigatorName)));
            totalCasesData.setTotalCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalCase))));
            totalCasesData.setTotalSubmittedCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalSubmittedCases))));
            totalCasesData.setTotalPendingCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalPendingCases))));
            list.add(totalCasesData);
            cursor.moveToNext();
        }
        return list;
    }

    @SuppressLint("Range")
    public List<TotalCases.TotalCasesData> getToalCases_Hold() {
        List<TotalCases.TotalCasesData> list = new ArrayList<>();
        TotalCases.TotalCasesData totalCasesData;
        String sql = "select * From " + tblTotalCases_Hold;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            totalCasesData = new TotalCases.TotalCasesData();
            totalCasesData.setInvestigationUsedForID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InvestigationUsedForID))));
            totalCasesData.setInvestigationUsedForText(cursor.getString(cursor.getColumnIndex(InvestigationUsedForText)));
            totalCasesData.setActiveInvestigationUsedFor(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(IsActiveInvestigationUsedFor))));
            totalCasesData.setInsvestigatorID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(InsvestigatorID))));
            totalCasesData.setInsvestigatorCode(cursor.getString(cursor.getColumnIndex(InsvestigatorCode)));
            totalCasesData.setInsvestigatorName(cursor.getString(cursor.getColumnIndex(InsvestigatorName)));
            totalCasesData.setTotalCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalCase))));
            totalCasesData.setTotalSubmittedCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalSubmittedCases))));
            totalCasesData.setTotalPendingCases(Integer.valueOf(cursor.getString(cursor.getColumnIndex(TotalPendingCases))));
            list.add(totalCasesData);
            cursor.moveToNext();
        }
        return list;
    }


    public int delete_tblMyAssignmentData() {
        return sqLiteDatabase.delete(tblMyAssignmentData, null, null);
    }

    public int delete_tblMyAssignmentData_Hold() {
        return sqLiteDatabase.delete(tblMyAssignmentData_Hold, null, null);
    }


    public long inserttblMyAssignmentData(MyAssignment.MyAssignmentData myAssignmentData) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.InsuranceDataID, myAssignmentData.getInsuranceDataID() != null ? myAssignmentData.getInsuranceDataID().toString() : "");
        values.put(VISUS_SQLiteHelper.VisusServicesId, myAssignmentData.getVisusServicesID() != null ? myAssignmentData.getVisusServicesID().toString() : "");
        values.put(VISUS_SQLiteHelper.VisusServicesText_My_AssignMent, myAssignmentData.getVisusServicesText() != null ? myAssignmentData.getVisusServicesText() : "");
        values.put(VISUS_SQLiteHelper.InsuranceAssignedOnDate, myAssignmentData.getInsuranceAssignedOnDate() != null ? myAssignmentData.getInsuranceAssignedOnDate() : "");
        values.put(VISUS_SQLiteHelper.InsuranceSubmittedOnDate, myAssignmentData.getInsuranceSubmittedOnDate() != null ? myAssignmentData.getInsuranceSubmittedOnDate() : "");
        values.put(VISUS_SQLiteHelper.TATForInvestigator, myAssignmentData.gettATForInvestigator() != null ? myAssignmentData.gettATForInvestigator().toString() : "");
        values.put(VISUS_SQLiteHelper.ClaimNumber, myAssignmentData.getClaimNumber() != null ? myAssignmentData.getClaimNumber() : "");
        values.put(VISUS_SQLiteHelper.PolicyNumber, myAssignmentData.getPolicyNumber() != null ? myAssignmentData.getPolicyNumber() : "");
        values.put(VISUS_SQLiteHelper.InsuranceCompanyName, myAssignmentData.getInsuranceCompanyName() != null ? myAssignmentData.getInsuranceCompanyName() : "");
        values.put(VISUS_SQLiteHelper.CompanyName, myAssignmentData.getCompanyName() != null ? myAssignmentData.getCompanyName() : "");
        values.put(VISUS_SQLiteHelper.ProductID, myAssignmentData.getProductID() != null ? myAssignmentData.getProductID().toString() : "");
        values.put(VISUS_SQLiteHelper.ProductName, myAssignmentData.getProductName() != null ? myAssignmentData.getProductName() : "");
        values.put(VISUS_SQLiteHelper.CaseTitle, myAssignmentData.getCaseTitle() != null ? myAssignmentData.getCaseTitle() : "");
        values.put(VISUS_SQLiteHelper.DateOfAccident, myAssignmentData.getDateOfAccident() != null ? myAssignmentData.getDateOfAccident() : "");
        values.put(VISUS_SQLiteHelper.InvInsuranceRelID, myAssignmentData.getInvInsuranceRelID() != null ? myAssignmentData.getInvInsuranceRelID() : "");
        values.put(VISUS_SQLiteHelper.ProductSubCategory, myAssignmentData.getProductSubCategory() != null ? myAssignmentData.getProductSubCategory() : "");

        values.put(VISUS_SQLiteHelper.InsuredOrClaimentName, myAssignmentData.getInsuredOrClaimentName() != null ? myAssignmentData.getInsuredOrClaimentName() : "");
        values.put(VISUS_SQLiteHelper.FileAttachmentCountByInvestigator, myAssignmentData.getFileAttachmentCountByInvestigator() != null ? myAssignmentData.getFileAttachmentCountByInvestigator() : "");

        insertInvestigatorObj(myAssignmentData.getInvestigatorObj());
        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tblMyAssignmentData, null, values);
        return i;
    }


    public long inserttblMyAssignmentHoldData(MyAssignment.MyAssignmentData myAssignmentData) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.InsuranceDataID, myAssignmentData.getInsuranceDataID() != null ? myAssignmentData.getInsuranceDataID().toString() : "");
        values.put(VISUS_SQLiteHelper.VisusServicesId, myAssignmentData.getVisusServicesID() != null ? myAssignmentData.getVisusServicesID().toString() : "");
        values.put(VISUS_SQLiteHelper.VisusServicesText_My_AssignMent, myAssignmentData.getVisusServicesText() != null ? myAssignmentData.getVisusServicesText() : "");
        values.put(VISUS_SQLiteHelper.InsuranceAssignedOnDate, myAssignmentData.getInsuranceAssignedOnDate() != null ? myAssignmentData.getInsuranceAssignedOnDate() : "");
        values.put(VISUS_SQLiteHelper.InsuranceSubmittedOnDate, myAssignmentData.getInsuranceSubmittedOnDate() != null ? myAssignmentData.getInsuranceSubmittedOnDate() : "");
        values.put(VISUS_SQLiteHelper.TATForInvestigator, myAssignmentData.gettATForInvestigator() != null ? myAssignmentData.gettATForInvestigator().toString() : "");
        values.put(VISUS_SQLiteHelper.ClaimNumber, myAssignmentData.getClaimNumber() != null ? myAssignmentData.getClaimNumber() : "");
        values.put(VISUS_SQLiteHelper.PolicyNumber, myAssignmentData.getPolicyNumber() != null ? myAssignmentData.getPolicyNumber() : "");
        values.put(VISUS_SQLiteHelper.InsuranceCompanyName, myAssignmentData.getInsuranceCompanyName() != null ? myAssignmentData.getInsuranceCompanyName() : "");
        values.put(VISUS_SQLiteHelper.CompanyName, myAssignmentData.getCompanyName() != null ? myAssignmentData.getCompanyName() : "");
        values.put(VISUS_SQLiteHelper.ProductID, myAssignmentData.getProductID() != null ? myAssignmentData.getProductID().toString() : "");
        values.put(VISUS_SQLiteHelper.ProductName, myAssignmentData.getProductName() != null ? myAssignmentData.getProductName() : "");
        values.put(VISUS_SQLiteHelper.CaseTitle, myAssignmentData.getCaseTitle() != null ? myAssignmentData.getCaseTitle() : "");
        values.put(VISUS_SQLiteHelper.DateOfAccident, myAssignmentData.getDateOfAccident() != null ? myAssignmentData.getDateOfAccident() : "");
        values.put(VISUS_SQLiteHelper.InvInsuranceRelID, myAssignmentData.getInvInsuranceRelID() != null ? myAssignmentData.getInvInsuranceRelID() : "");
        values.put(VISUS_SQLiteHelper.ProductSubCategory, myAssignmentData.getProductSubCategory() != null ? myAssignmentData.getProductSubCategory() : "");

        values.put(VISUS_SQLiteHelper.InsuredOrClaimentName, myAssignmentData.getInsuredOrClaimentName() != null ? myAssignmentData.getInsuredOrClaimentName() : "");
        values.put(VISUS_SQLiteHelper.FileAttachmentCountByInvestigator, myAssignmentData.getFileAttachmentCountByInvestigator() != null ? myAssignmentData.getFileAttachmentCountByInvestigator() : "");

        insertInvestigatorObj(myAssignmentData.getInvestigatorObj());
        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tblMyAssignmentData_Hold, null, values);
        return i;
    }


    public int delete_tblInvestigatorObj() {
        return sqLiteDatabase.delete(VISUS_SQLiteHelper.tblInvestigatorObj, null, null);
    }

    public long insertInvestigatorObj(MyAssignment.InvestigatorObj investigatorObj) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.INV_ID, investigatorObj.getInvId() != null ? investigatorObj.getInvId().toString() : "");
        values.put(VISUS_SQLiteHelper.INV_Name, investigatorObj.getiNVName() != null ? investigatorObj.getiNVName() : "");
        values.put(VISUS_SQLiteHelper.ISActive, investigatorObj.getiSActive() != null ? investigatorObj.getiSActive() : "");
        values.put(VISUS_SQLiteHelper.INV_Father_Name, investigatorObj.getiNVFatherName() != null ? investigatorObj.getiNVFatherName() : "");
        values.put(VISUS_SQLiteHelper.INV_Contact_Number1, investigatorObj.getiNVContactNumber1() != null ? investigatorObj.getiNVContactNumber1() : "");
        values.put(VISUS_SQLiteHelper.INV_Contact_Number2, investigatorObj.getiNVContactNumber2() != null ? investigatorObj.getiNVContactNumber2() : "");
        values.put(VISUS_SQLiteHelper.INV_code, investigatorObj.getiNVCode() != null ? investigatorObj.getiNVCode() : "");
        values.put(VISUS_SQLiteHelper.JoiningDate, investigatorObj.getJoiningDate() != null ? investigatorObj.getJoiningDate() : "");
        values.put(VISUS_SQLiteHelper.ReliveDate, investigatorObj.getReliveDate() != null ? investigatorObj.getReliveDate() : "");
        values.put(VISUS_SQLiteHelper.DOB, investigatorObj.getDob() != null ? investigatorObj.getDob() : "");
        values.put(VISUS_SQLiteHelper.Email, investigatorObj.getEmail() != null ? investigatorObj.getEmail() : "");
        values.put(VISUS_SQLiteHelper.PAN_Number, investigatorObj.getpANNumber() != null ? investigatorObj.getpANNumber() : "");
        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tblInvestigatorObj, null, values);
        return i;
    }

    @SuppressLint("Range")
    public List<MyAssignment.MyAssignmentData> getDataMyAssignment(String serviceID) {
        List<MyAssignment.MyAssignmentData> list = new ArrayList<>();
        MyAssignment.MyAssignmentData myAssignmentData;
        String sql = "select * From " + tblMyAssignmentData + " WHERE " + VISUS_SQLiteHelper.VisusServicesId + " = " + serviceID;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            myAssignmentData = new MyAssignment.MyAssignmentData();
            myAssignmentData.setInsuranceDataID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceDataID))));
            myAssignmentData.setVisusServicesID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.VisusServicesId))));
            myAssignmentData.setVisusServicesText(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.VisusServicesText_My_AssignMent)));
            myAssignmentData.setInsuranceAssignedOnDate(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceAssignedOnDate)));
            myAssignmentData.setInsuranceSubmittedOnDate(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceSubmittedOnDate)));
            myAssignmentData.settATForInvestigator(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.TATForInvestigator))));
            myAssignmentData.setClaimNumber(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ClaimNumber)));
            myAssignmentData.setPolicyNumber(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.PolicyNumber)));
            myAssignmentData.setInsuranceCompanyName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceCompanyName)));
            myAssignmentData.setCompanyName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.CompanyName)));
            myAssignmentData.setProductID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ProductID))));
            myAssignmentData.setProductName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ProductName)));
            myAssignmentData.setCaseTitle(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.CaseTitle)));
            myAssignmentData.setDateOfAccident(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DateOfAccident)));
            myAssignmentData.setInvInsuranceRelID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvInsuranceRelID)));
            myAssignmentData.setProductSubCategory(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ProductSubCategory)));
            myAssignmentData.setInsuredOrClaimentName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuredOrClaimentName)));
            myAssignmentData.setFileAttachmentCountByInvestigator(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.FileAttachmentCountByInvestigator)));

            list.add(myAssignmentData);
            cursor.moveToNext();
        }
        return list;
    }

    @SuppressLint("Range")
    public List<MyAssignment.MyAssignmentData> getDataMyAssignment_Hold(String serviceID) {
        List<MyAssignment.MyAssignmentData> list = new ArrayList<>();
        MyAssignment.MyAssignmentData myAssignmentData;
        String sql = "select * From " + tblMyAssignmentData_Hold + " WHERE " + VISUS_SQLiteHelper.VisusServicesId + " = " + serviceID;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            myAssignmentData = new MyAssignment.MyAssignmentData();
            myAssignmentData.setInsuranceDataID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceDataID))));
            myAssignmentData.setVisusServicesID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.VisusServicesId))));
            myAssignmentData.setVisusServicesText(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.VisusServicesText_My_AssignMent)));
            myAssignmentData.setInsuranceAssignedOnDate(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceAssignedOnDate)));
            myAssignmentData.setInsuranceSubmittedOnDate(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceSubmittedOnDate)));
            myAssignmentData.settATForInvestigator(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.TATForInvestigator))));
            myAssignmentData.setClaimNumber(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ClaimNumber)));
            myAssignmentData.setPolicyNumber(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.PolicyNumber)));
            myAssignmentData.setInsuranceCompanyName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceCompanyName)));
            myAssignmentData.setCompanyName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.CompanyName)));
            myAssignmentData.setProductID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ProductID))));
            myAssignmentData.setProductName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ProductName)));
            myAssignmentData.setCaseTitle(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.CaseTitle)));
            myAssignmentData.setDateOfAccident(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DateOfAccident)));
            myAssignmentData.setInvInsuranceRelID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvInsuranceRelID)));
            myAssignmentData.setProductSubCategory(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ProductSubCategory)));
            myAssignmentData.setInsuredOrClaimentName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuredOrClaimentName)));
            myAssignmentData.setFileAttachmentCountByInvestigator(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.FileAttachmentCountByInvestigator)));

            list.add(myAssignmentData);
            cursor.moveToNext();
        }
        return list;
    }

    public int delete_tblRequreActionsData() {
        return sqLiteDatabase.delete(VISUS_SQLiteHelper.tblRequreActionsData, null, null);
    }

    public long insertRequreActionsData(RequreActions.RequreActionsData requreActionsData) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.InvestigatorReqActivity_ID, requreActionsData.getInvestigatorReqActivityID() != null ? requreActionsData.getInvestigatorReqActivityID().toString() : "");
        values.put(VISUS_SQLiteHelper.InsuranceClaimTypeID, requreActionsData.getInsuranceClaimTypeID() != null ? requreActionsData.getInsuranceClaimTypeID().toString() : "");
        values.put(VISUS_SQLiteHelper.InsuranceClaimTypeText, requreActionsData.getInsuranceClaimTypeText() != null ? requreActionsData.getInsuranceClaimTypeText() : "");
        values.put(VISUS_SQLiteHelper.InvestigatorReqActivity_Text, requreActionsData.getInvestigatorReqActivityText() != null ? requreActionsData.getInvestigatorReqActivityText() : "");
        values.put(VISUS_SQLiteHelper.IsSelfieRequired, requreActionsData.getSelfieRequired() != null ? requreActionsData.getSelfieRequired().toString() : "");
        values.put(VISUS_SQLiteHelper.IsCompulsory, requreActionsData.getCompulsory() != null ? requreActionsData.getCompulsory().toString() : "");
        values.put(VISUS_SQLiteHelper.IsPhotoRequired, requreActionsData.getPhotoRequired() != null ? requreActionsData.getPhotoRequired().toString() : "");
        values.put(VISUS_SQLiteHelper.MinimumPhotoRequired, requreActionsData.getMinimumPhotoRequired() != null ? requreActionsData.getMinimumPhotoRequired().toString() : "");
        values.put(VISUS_SQLiteHelper.MaximumPhotoRequired, requreActionsData.getMaximumPhotoRequired() != null ? requreActionsData.getMaximumPhotoRequired().toString() : "");
        values.put(VISUS_SQLiteHelper.IsVedioFileRequired, requreActionsData.getVedioFileRequired() != null ? requreActionsData.getVedioFileRequired().toString() : "");
        values.put(VISUS_SQLiteHelper.MinumumVedioFileRequired, requreActionsData.getMinumumVedioFileRequired() != null ? requreActionsData.getMinumumVedioFileRequired().toString() : "");
        values.put(VISUS_SQLiteHelper.MaximumVedioFileRequired, requreActionsData.getMaximumVedioFileRequired() != null ? requreActionsData.getMaximumVedioFileRequired().toString() : "");
        values.put(VISUS_SQLiteHelper.MaxmumSizeOfVedioFileInMB, requreActionsData.getMaxmumSizeOfVedioFileInMB() != null ? requreActionsData.getMaxmumSizeOfVedioFileInMB().toString() : "");
        values.put(VISUS_SQLiteHelper.IsActiveInvestigatorReqActivity, requreActionsData.getActiveInvestigatorReqActivity() != null ? requreActionsData.getActiveInvestigatorReqActivity().toString() : "");
        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tblRequreActionsData, null, values);
        return i;
    }

    @SuppressLint("Range")
    public List<RequreActions.RequreActionsData> getRequreActionsData() {
        List<RequreActions.RequreActionsData> list = new ArrayList<>();
        RequreActions.RequreActionsData requreActionsData;
        String sql = "select * From " + VISUS_SQLiteHelper.tblRequreActionsData;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            requreActionsData = new RequreActions.RequreActionsData();
            requreActionsData.setInvestigatorReqActivityID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvestigatorReqActivity_ID))));
            requreActionsData.setInsuranceClaimTypeID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceClaimTypeID))));
            requreActionsData.setInsuranceClaimTypeText(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InsuranceClaimTypeText)));
            requreActionsData.setInvestigatorReqActivityText(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvestigatorReqActivity_Text)));
            requreActionsData.setSelfieRequired(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.IsSelfieRequired))));
            requreActionsData.setCompulsory(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.IsCompulsory))));
            requreActionsData.setPhotoRequired(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.IsPhotoRequired))));
            requreActionsData.setMinimumPhotoRequired(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.MinimumPhotoRequired))));
            requreActionsData.setMaximumPhotoRequired(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.MaximumPhotoRequired))));
            requreActionsData.setVedioFileRequired(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.IsVedioFileRequired))));
            requreActionsData.setMinumumVedioFileRequired(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.MinumumVedioFileRequired))));
            requreActionsData.setMaximumVedioFileRequired(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.MaximumVedioFileRequired))));
            requreActionsData.setMaxmumSizeOfVedioFileInMB(Integer.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.MaxmumSizeOfVedioFileInMB))));
            requreActionsData.setActiveInvestigatorReqActivity(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.IsActiveInvestigatorReqActivity))));
            list.add(requreActionsData);
            cursor.moveToNext();
        }
        return list;
    }

    public long insertPostInvestigatorActionData(String originalFileName, String serviceTypeID, String serviceID, String invID, String comments, String actionID, String latitude, String longitude, String cellAddress, String iInvInsuranceRelID_SAVING) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.Token, "");
        values.put(VISUS_SQLiteHelper.OriginalFileName, originalFileName);
        values.put(VISUS_SQLiteHelper.ServiceTypeID, serviceTypeID);
        values.put(VISUS_SQLiteHelper.ServiceID, serviceID);
        values.put(VISUS_SQLiteHelper.InvID, invID);
        values.put(VISUS_SQLiteHelper.Comments, comments);
        values.put(VISUS_SQLiteHelper.ActionID, actionID);
        values.put(VISUS_SQLiteHelper.Latitude, latitude);
        values.put(VISUS_SQLiteHelper.Longitude, longitude);
        values.put(VISUS_SQLiteHelper.CellAddress, cellAddress);
        values.put(VISUS_SQLiteHelper.InvInsuranceRelID_SAVING, iInvInsuranceRelID_SAVING);
        values.put(VISUS_SQLiteHelper.isSyncedRequreActionsData, "false");
        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tblPostInvestigatorActionData, null, values);
        return i;
    }

    public long insertPostInvestigatorActionDataNew(SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData) {
        Log.d("TAG", "insertPostInvestigatorActionDataNew: "+new Gson().toJson(investigatorActionData));
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.Token, "");
        values.put(VISUS_SQLiteHelper.OriginalFileName, "");
        values.put(VISUS_SQLiteHelper.ServiceTypeID, investigatorActionData.getServiceTypeID());
        values.put(VISUS_SQLiteHelper.ServiceID, investigatorActionData.getServiceID());
        values.put(VISUS_SQLiteHelper.InvID, investigatorActionData.getInvID());
        values.put(VISUS_SQLiteHelper.Comments, investigatorActionData.getComments());
        values.put(VISUS_SQLiteHelper.ActionID, investigatorActionData.getActionID());
        values.put(VISUS_SQLiteHelper.Latitude, investigatorActionData.getLatitude());
        values.put(VISUS_SQLiteHelper.Longitude, investigatorActionData.getLongitude());
        values.put(VISUS_SQLiteHelper.CellAddress, investigatorActionData.getCellAddress());
        values.put(VISUS_SQLiteHelper.InvInsuranceRelID_SAVING, investigatorActionData.getInvInsuranceRelID());
        values.put(VISUS_SQLiteHelper.isSyncedRequreActionsData, "false");
        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tblPostInvestigatorActionData, null, values);
        return i;
    }


    public int updatetblPostInvestigatorActionDataToken(String token) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VISUS_SQLiteHelper.Token, "Bearer " + token);
        int updateToken_In_tblPostInvestigatorActionData = sqLiteDatabase.update(VISUS_SQLiteHelper.tblPostInvestigatorActionData, contentValues, null, null);
        return updateToken_In_tblPostInvestigatorActionData;
    }

    @SuppressLint("Range")
    public List<SaveInvestigatorActionOnlyData.InvestigatorActionData> getPostInvestigatorActionData() {
        SaveInvestigatorActionOnlyData saveInvestigatorActionData = new SaveInvestigatorActionOnlyData();
        List<SaveInvestigatorActionOnlyData.InvestigatorActionData> list = new ArrayList<>();
        SaveInvestigatorActionOnlyData.InvestigatorActionData postInvestigatorActionData;
        String sql = "select * From " + VISUS_SQLiteHelper.tblPostInvestigatorActionData + " WHERE " + VISUS_SQLiteHelper.isSyncedRequreActionsData + " = 'false'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            postInvestigatorActionData = (saveInvestigatorActionData).new InvestigatorActionData();
            postInvestigatorActionData.setClientID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.common_id)));
            postInvestigatorActionData.setServiceTypeID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ServiceTypeID)));
            postInvestigatorActionData.setServiceID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ServiceID)));
            postInvestigatorActionData.setInvID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvID)));
            postInvestigatorActionData.setComments(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.Comments)));
            postInvestigatorActionData.setActionID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ActionID)));
            postInvestigatorActionData.setLatitude(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.Latitude)));
            postInvestigatorActionData.setLongitude(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.Longitude)));
            postInvestigatorActionData.setCellAddress(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.CellAddress)));
            postInvestigatorActionData.setInvInsuranceRelID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvInsuranceRelID_SAVING)));
            list.add(postInvestigatorActionData);
            cursor.moveToNext();
        }
        return list;
    }

    public int delete_tblPostInvestigatorActionData(String commonID) {
        String whereClause = VISUS_SQLiteHelper.common_id + "=?";
        String[] whereArgs = {commonID};
        return sqLiteDatabase.delete(VISUS_SQLiteHelper.tblPostInvestigatorActionData, whereClause, whereArgs);
    }

    public long insertDeviceInvLocation(DeviceInvLocation deviceInvLocation) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.DeviceLocation_ID, deviceInvLocation.getDeviceLocation_ID());
        values.put(VISUS_SQLiteHelper.DeviceLocation_DeviceID, deviceInvLocation.getDeviceLocation_DeviceID());
        values.put(VISUS_SQLiteHelper.DeviceLocation_InvID, deviceInvLocation.getDeviceLocation_InvID());
        values.put(VISUS_SQLiteHelper.DeviceLocation_Latitude, deviceInvLocation.getDeviceLocation_Latitude());
        values.put(VISUS_SQLiteHelper.DeviceLocation_Longitude, deviceInvLocation.getDeviceLocation_Longitude());
        values.put(VISUS_SQLiteHelper.DeviceLocation_GeoAddress, deviceInvLocation.getDeviceLocation_GeoAddress());
        values.put(VISUS_SQLiteHelper.DeviceLocation_SaveOnDate, deviceInvLocation.getDeviceLocation_SaveOnDate());
        values.put(VISUS_SQLiteHelper.IsLatestDeviceLocation, deviceInvLocation.getIsLatestDeviceLocation());
        values.put(VISUS_SQLiteHelper.IsSavedDeviceLocation, deviceInvLocation.getIsSavedDeviceLocation());
        values.put(VISUS_SQLiteHelper.ExceptionIfAny, deviceInvLocation.getExceptionIfAny());
        values.put(VISUS_SQLiteHelper.isSyncedDeviceInvLocation, "false");

        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tbl_DeviceInvLocation, null, values);
        return i;
    }

    @SuppressLint("Range")
    public List<DeviceInvLocation> getDeviceInvLocation() {
        List<DeviceInvLocation> list = new ArrayList<>();
        DeviceInvLocation deviceInvLocation;
        String sql = "select * From " + VISUS_SQLiteHelper.tbl_DeviceInvLocation + " WHERE " + VISUS_SQLiteHelper.isSyncedDeviceInvLocation + " = 'false'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            deviceInvLocation = new DeviceInvLocation();
            deviceInvLocation.setDeviceLocation_ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DeviceLocation_ID))));
            deviceInvLocation.setDeviceLocation_DeviceID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DeviceLocation_DeviceID))));
            deviceInvLocation.setDeviceLocation_InvID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DeviceLocation_InvID))));
            deviceInvLocation.setDeviceLocation_Latitude(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DeviceLocation_Latitude)));
            deviceInvLocation.setDeviceLocation_Longitude(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DeviceLocation_Longitude)));
            deviceInvLocation.setDeviceLocation_GeoAddress(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DeviceLocation_GeoAddress)));
            deviceInvLocation.setDeviceLocation_SaveOnDate(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.DeviceLocation_SaveOnDate)));
            deviceInvLocation.setIsLatestDeviceLocation(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.IsLatestDeviceLocation))));
            deviceInvLocation.setIsSavedDeviceLocation(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.IsSavedDeviceLocation))));
            deviceInvLocation.setExceptionIfAny(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ExceptionIfAny)));
            list.add(deviceInvLocation);
            cursor.moveToNext();
        }
        return list;
    }

    public int delete_tblDeviceInvLocation(String deviceLocation_SaveOnDate) {
        String whereClause = VISUS_SQLiteHelper.DeviceLocation_SaveOnDate + "=?";
        String[] whereArgs = {deviceLocation_SaveOnDate};
        return sqLiteDatabase.delete(VISUS_SQLiteHelper.tbl_DeviceInvLocation, whereClause, whereArgs);
    }

    public int count_tblDeviceInvLocation() {
        String sql = "select * From " + VISUS_SQLiteHelper.tbl_DeviceInvLocation + " WHERE " + VISUS_SQLiteHelper.isSyncedDeviceInvLocation + " = 'false'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor.getCount();
    }


    public Long insertPostInvestigatorActionDataPhoto(SaveInvestigatorAction.SaveInvestigatorActionData arr) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(VISUS_SQLiteHelper.Token, "");
        values.put(VISUS_SQLiteHelper.InvestigatorCaseActivityPhotoServerID, "");
        values.put(VISUS_SQLiteHelper.InvestigatorCaseActivity_ClientD, arr.getInvestigatorCaseActivity_ClientD());
        values.put(VISUS_SQLiteHelper.InvestigatorCaseActivity_ID, arr.getInvestigatorCaseActivityID());
        values.put(VISUS_SQLiteHelper.InvestigatorCaseActivity_CaseInsuranceID, arr.getInvestigatorCaseActivityCaseInsuranceID());
        values.put(VISUS_SQLiteHelper.InvestigatorCaseActivity_InvID, arr.getInvestigatorCaseActivityInvID());
        values.put(VISUS_SQLiteHelper.InvestigatorRequiredActivityID, arr.getInvestigatorRequiredActivityID());
        values.put(VISUS_SQLiteHelper.ObjInvReqActivity, arr.getObjInvReqActivity());
        values.put(VISUS_SQLiteHelper.OriginalFileName, arr.getOriginalFileName());
        values.put(VISUS_SQLiteHelper.UniqueFileName, arr.getUniqueFileName());
        values.put(VISUS_SQLiteHelper.ActivityFilePath, arr.getActivityFilePath());
        values.put(VISUS_SQLiteHelper.FileSubmittionOnDate, arr.getFileSubmittionOnDate());
        values.put(VISUS_SQLiteHelper.VisusServicesID, arr.getVisusServicesID());
        values.put(VISUS_SQLiteHelper.VisusServicesText, arr.getVisusServicesText());
        values.put(VISUS_SQLiteHelper.InvestigatorObj, arr.getInvestigatorObj());
        values.put(VISUS_SQLiteHelper.InsuranceAssignedOnDate, arr.getInsuranceAssignedOnDate());
        values.put(VISUS_SQLiteHelper.InsuranceSubmittedOnDate, arr.getInsuranceSubmittedOnDate());
        values.put(VISUS_SQLiteHelper.TATForInvestigator, arr.gettATForInvestigator());
        values.put(VISUS_SQLiteHelper.ClaimNumber, arr.getClaimNumber());
        values.put(VISUS_SQLiteHelper.PolicyNumber, arr.getPolicyNumber());
        values.put(VISUS_SQLiteHelper.InsuranceCompanyName, arr.getInsuranceCompanyName());
        values.put(VISUS_SQLiteHelper.CompanyName, arr.getCompanyName());
        values.put(VISUS_SQLiteHelper.ExceptionIfAny, arr.getExceptionIfAny());
        values.put(VISUS_SQLiteHelper.InvestigatorComments, arr.getInvestigatorComments());
        values.put(VISUS_SQLiteHelper.LatitudeAtClickingPhoto, arr.getLatitudeAtClickingPhoto());
        values.put(VISUS_SQLiteHelper.LongitudeAtClickingPhoto, arr.getLongitudeAtClickingPhoto());
        values.put(VISUS_SQLiteHelper.AddressAtClickingPhoto, arr.getAddressAtClickingPhoto());
        values.put(VISUS_SQLiteHelper.InvInsuranceRelID, arr.getInvInsuranceRelID());
        values.put(VISUS_SQLiteHelper.isSyncedRequreActionsData, "false");

        i = sqLiteDatabase.insert(VISUS_SQLiteHelper.tblPostInvestigatorActionDataPhoto, null, values);
        return i;
    }

    @SuppressLint("Range")
    public List<SaveInvestigatorAction.SaveInvestigatorActionData> getPostInvestigatorActionDataPhoto() {
        SaveInvestigatorAction saveInvestigatorAction = new SaveInvestigatorAction();
        List<SaveInvestigatorAction.SaveInvestigatorActionData> list = new ArrayList<>();
        SaveInvestigatorAction.SaveInvestigatorActionData saveInvestigatorActionData;
        String sql = "select * From " + VISUS_SQLiteHelper.tblPostInvestigatorActionDataPhoto + " WHERE " + VISUS_SQLiteHelper.isSyncedRequreActionsData + " = 'false'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            saveInvestigatorActionData = (saveInvestigatorAction).new SaveInvestigatorActionData();
            saveInvestigatorActionData.setInvestigatorCaseActivity_ClientD(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvestigatorCaseActivity_ClientD)));
            saveInvestigatorActionData.setOriginalFileName(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.OriginalFileName)));
            saveInvestigatorActionData.setActivityFilePath(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.ActivityFilePath)));
            saveInvestigatorActionData.setVisusServicesID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.VisusServicesID)));
            saveInvestigatorActionData.setInvestigatorCaseActivityInvID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvestigatorCaseActivity_InvID)));
            saveInvestigatorActionData.setInvestigatorRequiredActivityID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvestigatorRequiredActivityID)));
            saveInvestigatorActionData.setInvInsuranceRelID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvInsuranceRelID)));
            saveInvestigatorActionData.setInvestigatorCaseActivityPhotoServerID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvestigatorCaseActivityPhotoServerID)));
            saveInvestigatorActionData.setInvestigatorCaseActivityCaseInsuranceID(cursor.getString(cursor.getColumnIndex(VISUS_SQLiteHelper.InvestigatorCaseActivity_CaseInsuranceID)));

            list.add(saveInvestigatorActionData);
            cursor.moveToNext();
        }
        return list;
    }

    public void updatePostInvestigatorActionData(String clientID) {
    }

    public int updatePostInvestigatorActionDataPhoto(String investigatorCaseActivityPhotoServerID) {
        String whereClause = VISUS_SQLiteHelper.InvestigatorCaseActivityPhotoServerID + "=?";
        String[] whereArgs = {investigatorCaseActivityPhotoServerID};
        return sqLiteDatabase.delete(VISUS_SQLiteHelper.tblPostInvestigatorActionDataPhoto, whereClause, whereArgs);
    }

    public int updatePostInvestigatorActionDataPhotoNEW(String InvestigatorCaseActivity_ClientD) {
        String whereClause = VISUS_SQLiteHelper.InvestigatorCaseActivity_ClientD + "=?";
        String[] whereArgs = {InvestigatorCaseActivity_ClientD};
        return sqLiteDatabase.delete(VISUS_SQLiteHelper.tblPostInvestigatorActionDataPhoto, whereClause, whereArgs);
    }





    public int updatetblPostInvestigatorActionDataPhotoToken(String token) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VISUS_SQLiteHelper.Token, "Bearer " + token);
        int updateToken_In_tblPostInvestigatorActionData = sqLiteDatabase.update(VISUS_SQLiteHelper.tblPostInvestigatorActionDataPhoto, contentValues, null, null);
        return updateToken_In_tblPostInvestigatorActionData;
    }


    public long update_tblPostInvestigatorActionDataPhoto(String investigatorActionDataServerID, String ClientID) {
        ContentValues contentValues = new ContentValues();
        String whereClause = VISUS_SQLiteHelper.InvestigatorCaseActivity_ClientD + "=?";
        String[] whereArgs = {ClientID};
        contentValues.put(VISUS_SQLiteHelper.InvestigatorCaseActivityPhotoServerID, investigatorActionDataServerID);
        long updateToken_In_tblPostInvestigatorActionData = sqLiteDatabase.update(VISUS_SQLiteHelper.tblPostInvestigatorActionDataPhoto, contentValues, whereClause, whereArgs);
        return updateToken_In_tblPostInvestigatorActionData;
    }

    public int updatePostInvestigatorActionDataPhotoTwoPra(String clicentID,String commonID) {
        String whereClause = VISUS_SQLiteHelper.InvestigatorCaseActivity_ClientD + "=? and"+VISUS_SQLiteHelper.common_id + "=?";
        String[] whereArgs = {clicentID,commonID};
        return sqLiteDatabase.delete(VISUS_SQLiteHelper.tblPostInvestigatorActionDataPhoto, "InvestigatorCaseActivity_ClientD=? and common_id=?", whereArgs);
    }
}
