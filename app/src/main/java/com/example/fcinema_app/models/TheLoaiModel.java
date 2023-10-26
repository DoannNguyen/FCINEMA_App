package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

public class TheLoaiModel {
    @SerializedName("idTheLoai")
    String id;
    @SerializedName("tenTheLoai")
    String tenTheLoai;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
}
