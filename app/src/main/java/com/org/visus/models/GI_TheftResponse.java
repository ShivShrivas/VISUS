package com.org.visus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.org.visus.models.requset.GI_Theft;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GI_TheftResponse implements Serializable {
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
    private List<GI_Theft> data = new ArrayList<>();

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

    public List<GI_Theft> getData() {
        return data;
    }

    public void setData(List<GI_Theft> data) {
        this.data = data;
    }


}
