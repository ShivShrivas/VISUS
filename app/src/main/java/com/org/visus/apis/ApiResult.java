package com.org.visus.apis;

import com.google.gson.JsonObject;
import com.org.visus.models.SaveInvestigatorAction;

public class ApiResult {
    private boolean isSuccess;
    private JsonObject successResponse;
    private String errorMessage;

    // Constructor for success
    public ApiResult(JsonObject successResponse) {
        this.isSuccess = true;
        this.successResponse = successResponse;
        this.errorMessage = null;
    }

    // Constructor for failure
    public ApiResult(String errorMessage) {
        this.isSuccess = false;
        this.successResponse = null;
        this.errorMessage = errorMessage;
    }

    // Constructor for success
    public ApiResult(String successMessage,boolean status) {
        this.isSuccess = true;
        this.successResponse = null;
        this.errorMessage = successMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public JsonObject getSuccessResponse() {
        return successResponse;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
