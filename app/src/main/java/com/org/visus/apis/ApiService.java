package com.org.visus.apis;

import com.google.gson.JsonObject;
import com.org.visus.models.DeviceInfo;
import com.org.visus.models.DeviceInvLocation;
import com.org.visus.models.DeviceOfInvStatus;
import com.org.visus.models.DeviceRegistrationResponse;
import com.org.visus.models.DeviceStatusResponse;
import com.org.visus.models.GI_ODResponse;
import com.org.visus.models.GI_PAResponse;
import com.org.visus.models.GI_TheftResponse;
import com.org.visus.models.GetServices;
import com.org.visus.models.GiODInsuCheckList;
import com.org.visus.models.GiPAInsuCheckList;
import com.org.visus.models.GiTheftInsuCheckList;
import com.org.visus.models.InvReqActivityFile;
import com.org.visus.models.Investigator;

import com.org.visus.models.LifeInsuranceCheckList;
import com.org.visus.models.MACTInsuCheckList;
import com.org.visus.models.MACTResponse;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.RequreActions;
import com.org.visus.models.SMSAsOTP;
import com.org.visus.models.SaveInvestigatorAction;
import com.org.visus.models.SaveInvestigatorActionOnlyData;
import com.org.visus.models.TokenResponse;
import com.org.visus.models.TotalCases;
import com.org.visus.models.requset.GI_OD;
import com.org.visus.models.requset.GI_PA;
import com.org.visus.models.requset.GI_Theft;
import com.org.visus.models.requset.MACT;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("token")
    Call<TokenResponse> getToken(@Field("UserName") String UserName, @Field("Password") String Password, @Field("grant_type") String grant_type);

    @POST("api/device/deviceInfoData")
    Call<DeviceRegistrationResponse> deviceRegistration(@Header("Authorization") String Authorization, @Body DeviceInfo value);

    @GET("api/ins/findValidIns")
    Call<Investigator> getInvestigatorInfoAccordingToInvestigatorID(@Header("Authorization") String Authorization, @Query("insCode") String insCode);

    @GET("api/sms/getsmsAsOtp")
    Call<SMSAsOTP> getSmsAsOtp(@Header("Authorization") String Authorization, @Query("insID") String insID, @Query("DeviceID") String DeviceID);

    @GET("api/VisusServices/getservices")
    Call<GetServices> getServices(@Header("Authorization") String Authorization);

    @GET("api/investigationUsedFor/gettotalCases")
    Call<TotalCases> getTotalCases(@Header("Authorization") String Authorization, @Query("InsID") String InsID);

    @GET("api/MyAssignmentList/getMyAssignment")
    Call<MyAssignment> getMyAssignment(@Header("Authorization") String Authorization, @Query("ServicesID") String ServicesID, @Query("InvID") String InvID);

    @GET("api/MyAssignmentList/getMyAssignmentAll")
    Call<MyAssignment> getMyPendingAssignmentAll(@Header("Authorization") String Authorization, @Query("InvID") String InvID);
    /*api/MyAssignmentList/getMyAssignment
    api/MyPendingAssignment/getMyAssignment*/


    @GET("api/MyHoldAssignmentList/getMyAssignmentAll")
    Call<MyAssignment> getMyHoldAssignmentAll(@Header("Authorization") String Authorization, @Query("InvID") String InvID);


    @GET("api/MyAssignmentList/getMyReqActionList")
    Call<RequreActions> getMyReqActionList(@Header("Authorization") String Authorization);

    @Multipart
    @POST("api/MyAssignmentList/SaveInvestigatorActionData")
    Call<SaveInvestigatorAction> postInvestigatorActionData(@Header("Authorization") String Authorization,
                                                            @Part MultipartBody.Part OriginalFileName,
                                                            @Query("ServiceTypeID") String ServiceTypeID,
                                                            @Query("ServiceID") String ServiceID,
                                                            @Query("InvID") String InvID,
                                                            @Query("Comments") String Comments,
                                                            @Query("ActionID") String ActionID,
                                                            @Query("Latitude") String Latitude,
                                                            @Query("Longitude") String Longitude,
                                                            @Query("CellAddress") String CellAddress,
                                                            @Query("ClientID") String ClientID, @Query("InvInsuranceRelID")
                                                            String InvInsuranceRelID);

    @Multipart
    @POST("api/invActionPhoto/SaveInvestigatorActionPhotoData")
    Call<SaveInvestigatorAction> postInvestigatorActionDataPhoto(@Header("Authorization") String Authorization,
                                                                 @Part MultipartBody.Part OriginalFileName,
                                                                 @Query("ServiceTypeID") String ServiceTypeID,
                                                                 @Query("ServiceID") String ServiceID,
                                                                 @Query("InvID") String InvID,
                                                                 @Query("ActionID") String ActionID,
                                                                 @Query("ClientID") String ClientID,
                                                                 @Query("InvInsuranceRelID") String InvInsuranceRelID,
                                                                 @Query("InvestigatorServiceCaseAction_ID") String InvestigatorServiceCaseAction_ID);
    @Multipart
    @POST("api/invActionPhoto/SaveInvestigatorActionlstPhotoData")
    Call<JsonObject> saveInvestigatorActionlstPhotoData(@Header("Authorization") String Authorization,
                                                        @Part List<MultipartBody.Part> OriginalFileName,
                                                        @Query("ServiceTypeID") String ServiceTypeID,
                                                        @Query("ServiceID") String ServiceID,
                                                        @Query("InvID") String InvID,
                                                        @Query("ActionID") String ActionID,
                                                        @Query("ClientID") String ClientID,
                                                        @Query("InvInsuranceRelID") String InvInsuranceRelID,
                                                        @Query("InvestigatorServiceCaseAction_ID") String InvestigatorServiceCaseAction_ID);


    @GET("api/MyPendingAssignment/getMyAssignment")
    Call<MyAssignment> getMyPendingAssignment(@Header("Authorization") String Authorization, @Query("ServicesID") String ServicesID, @Query("InvID") String InvID);


    @POST("api/MyPendingAssignment/SubmitMyAssignment")
    Call<Boolean> postMyAssignmentFinal(@Header("Authorization") String Authorization, @Query("ServiceTypeID") String ServiceTypeID, @Query("ServiceID") String ServiceID, @Query("InvID") String InvID, @Query("SubmitDateTime") String SubmitDateTime, @Query("TAT") String TAT);

    @GET("api/myUploadedFile/getInvReqActivityFile")
    Call<InvReqActivityFile> getInvReqActivityFile(@Header("Authorization") String Authorization, @Query("ServiceTypeID") String ServiceTypeID, @Query("ServiceID") String ServiceID, @Query("InvID") String InvID, @Query("InvInsuRelID") String InvInsuRelID);

    @GET("api/LiCheckList/getLifeInsuCheckList")
    Call<LifeInsuranceCheckList> getLifeInsuCheckList(@Header("Authorization") String Authorization, @Query("lifeInsuranceID") String lifeInsuranceID, @Query("InvID") String InvID);

    @POST("api/LiCheckList/saveLifeInsuCheckListData")
    Call<LifeInsuranceCheckList> postLifeInsurance(@Header("Authorization") String Authorization, @Body LifeInsuranceCheckList.LifeInsuranceCheckListData value);

    @GET("api/giODCheckList/getGiODInsuCheckList")
    Call<GiODInsuCheckList> getGiODInsuCheckList(@Header("Authorization") String Authorization);

    @POST("api/giODCheckList/saveGeneralInsuODCheckListData")
    Call<GI_ODResponse> postGI_OD(@Header("Authorization") String Authorization, @Body GI_OD value);

    @GET("api/giTheftCheckList/getGiTheftInsuCheckList")
    Call<GiTheftInsuCheckList> getGiTheftInsuCheckList(@Header("Authorization") String Authorization);

    @POST("api/giTheftCheckList/saveGeneralInsuTheftCheckListData")
    Call<GI_TheftResponse> postGI_Theft(@Header("Authorization") String Authorization, @Body GI_Theft value);

    @GET("api/giPACheckList/getGiPAInsuCheckList")
    Call<GiPAInsuCheckList> getGiPAInsuCheckList(@Header("Authorization") String Authorization);

    @POST("api/giPACheckList/saveGeneralInsuPACheckListData")
    Call<GI_PAResponse> postGI_PA(@Header("Authorization") String Authorization, @Body GI_PA value);

    @GET("api/InvMACTCheckList/getMACTInsuCheckList")
    Call<MACTInsuCheckList> getMACTInsuCheckList(@Header("Authorization") String Authorization);

    @POST("api/InvMACTCheckList/saveMACTCheckListData")
    Call<MACTResponse> postMACT(@Header("Authorization") String Authorization, @Body MACT value);


    @POST("api/deviceLocation/SaveDeviceLocation")
    Call<DeviceInvLocation> postDeviceInvLocation(@Header("Authorization") String Authorization, @Body DeviceInvLocation value);
/*
    {"DeviceLocation_ID":-1,"DeviceLocation_DeviceID":2,"DeviceLocation_InvID":11,"DeviceLocation_Latitude": "38.897676","DeviceLocation_Longitude":"77.036530","DeviceLocation_GeoAddress":"Khalilabad Sant kabir nagar","DeviceLocation_SaveOnDate":"","IsLatestDeviceLocation":true,"IsSavedDeviceLocation":false,"ExceptionIfAny":""}
*/

    @GET("api/device/deviceStatus")
    Call<DeviceStatusResponse> getDeviceOfInvStatus(@Header("Authorization") String Authorization, @Query("InvID") String InvID, @Query("deviceID") String deviceID);

    @GET("api/MyPendingHoldAssignment/getHoldMyAssignment")
    Call<MyAssignment> getMyPendingAssignment_Hold(@Header("Authorization") String Authorization, @Query("ServicesID") String ServicesID, @Query("InvID") String InvID);


    @GET("api/investigationUsedForHold/gettotalCases")
    Call<TotalCases> getTotalCases_Hold(@Header("Authorization") String Authorization, @Query("InsID") String InsID);


    @POST("api/invActionPhoto/SaveInvestigatorActionDataOnly")
    Call<SaveInvestigatorActionOnlyData> postInvestigatorActionDataNew(@Header("Authorization") String Authorization, @Body SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData);


    @POST("api/invActionPhoto/SaveInvestigatorActionListDataOnly")
    Call<SaveInvestigatorActionOnlyData> SaveInvestigatorActionListDataOnly(@Header("Authorization") String Authorization, @Body List<SaveInvestigatorActionOnlyData.InvestigatorActionData> investigatorActionData);

}
