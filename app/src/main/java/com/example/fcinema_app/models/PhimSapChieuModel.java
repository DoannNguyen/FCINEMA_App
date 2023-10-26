package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Blob;

public class PhimSapChieuModel implements Serializable {
    @SerializedName("anh")
    private String image;
    @SerializedName("tenPhim")
    private String tenPhim;
    @SerializedName("tenTheLoai")
    private String theLoai;
    @SerializedName("nuocSX")
    private String quocGia;
    @SerializedName("namSX")
    private String namSX;
    @SerializedName("thoiLuong")
    private String thoiLuong;
    @SerializedName("ngonNgu")
    private String ngonNgu;
    @SerializedName("daoDien")
    private String daoDien;
    @SerializedName("moTa")
    private String moTa;

    public PhimSapChieuModel() {
    }

    public PhimSapChieuModel(String image, String tenPhim, String theLoai, String quocGia, String namSX, String thoiLuong, String ngonNgu, String daoDien, String moTa) {
        this.image = image;
        this.tenPhim = tenPhim;
        this.theLoai = theLoai;
        this.quocGia = quocGia;
        this.namSX = namSX;
        this.thoiLuong = thoiLuong;
        this.ngonNgu = ngonNgu;
        this.daoDien = daoDien;
        this.moTa = moTa;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getNamSX() {
        return namSX;
    }

    public void setNamSX(String namSX) {
        this.namSX = namSX;
    }

    public String getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(String thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
