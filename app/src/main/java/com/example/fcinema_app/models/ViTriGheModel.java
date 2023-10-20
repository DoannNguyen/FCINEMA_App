package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

public class ViTriGheModel {
    @SerializedName("tenGhe")
    private String tenGhe;
    @SerializedName("idPhongChieu")
    private int idPhongChieu;
    @SerializedName("idVe")
    private String idVe;

    public ViTriGheModel(String tenGhe, int idPhongChieu, String idVe) {
        this.tenGhe = tenGhe;
        this.idPhongChieu = idPhongChieu;
        this.idVe = idVe;
    }

    public ViTriGheModel() {
    }

    public String getTenGhe() {
        return tenGhe;
    }

    public void setTenGhe(String tenGhe) {
        this.tenGhe = tenGhe;
    }

    public int getIdPhongChieu() {
        return idPhongChieu;
    }

    public void setIdPhongChieu(int idPhongChieu) {
        this.idPhongChieu = idPhongChieu;
    }

    public String getIdVe() {
        return idVe;
    }

    public void setIdVe(String idVe) {
        this.idVe = idVe;
    }
}
