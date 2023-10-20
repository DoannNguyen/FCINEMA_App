package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class VeModel implements Serializable {
    @SerializedName("idVe")
    private String idVe;
    @SerializedName("soVe")
    private int soVe;
    @SerializedName("ngayMua")
    private String ngayMua;
    @SerializedName("tongTien")
    private int tongTien;
    @SerializedName("ngayTT")
    private String ngayTT;
    @SerializedName("email")
    private String email;
    @SerializedName("idNhanVien")
    private String idNV;
    @SerializedName("idLichChieu")
    private int idLichChieu;
    @SerializedName("idTT")
    private int idTT;

    public VeModel() {
    }

    public VeModel(String idVe, int soVe, String ngayMua, int tongTien, String ngayTT, int idLichChieu) {
        this.idVe = idVe;
        this.soVe = soVe;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
        this.ngayTT = ngayTT;
        this.idLichChieu = idLichChieu;
    }

    public String getIdVe() {
        return idVe;
    }

    public void setIdVe(String idVe) {
        this.idVe = idVe;
    }

    public int getSoVe() {
        return soVe;
    }

    public void setSoVe(int soVe) {
        this.soVe = soVe;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(String ngayTT) {
        this.ngayTT = ngayTT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNV() {
        return idNV;
    }

    public void setIdNV(String idNV) {
        this.idNV = idNV;
    }

    public int getIdLichChieu() {
        return idLichChieu;
    }

    public void setIdLichChieu(int idLichChieu) {
        this.idLichChieu = idLichChieu;
    }

    public int getIdTT() {
        return idTT;
    }

    public void setIdTT(int idTT) {
        this.idTT = idTT;
    }
}
