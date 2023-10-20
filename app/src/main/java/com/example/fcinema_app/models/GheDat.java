package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

public class GheDat {
    @SerializedName("tenGhe")
    private String tenGhe;

    public String getTenGhe() {
        return tenGhe;
    }

    public void setTenGhe(String tenGhe) {
        this.tenGhe = tenGhe;
    }
}
