package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DoAnModel implements Serializable {
    @SerializedName("idDoAn")
    private int idDoAn;
    @SerializedName("tenDoAn")
    private String tenDoAn;
    @SerializedName("coSan")
    private int kho;
    @SerializedName("giaDoAn")
    private float giaDoAn;
    @SerializedName("anh")
    private String anh;
    private int soLuong;

    public DoAnModel() {
    }

    public DoAnModel(int idDoAn, String tenDoAn, int kho, float giaDoAn, String anh, int soLuong) {
        this.idDoAn = idDoAn;
        this.tenDoAn = tenDoAn;
        this.kho = kho;
        this.giaDoAn = giaDoAn;
        this.anh = anh;
        this.soLuong = soLuong;
    }

    public int getIdDoAn() {
        return idDoAn;
    }

    public void setIdDoAn(int idDoAn) {
        this.idDoAn = idDoAn;
    }

    public String getTenDoAn() {
        return tenDoAn;
    }

    public void setTenDoAn(String tenDoAn) {
        this.tenDoAn = tenDoAn;
    }

    public int getKho() {
        return kho;
    }

    public void setKho(int kho) {
        this.kho = kho;
    }

    public float getGiaDoAn() {
        return giaDoAn;
    }

    public void setGiaDoAn(float giaDoAn) {
        this.giaDoAn = giaDoAn;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
