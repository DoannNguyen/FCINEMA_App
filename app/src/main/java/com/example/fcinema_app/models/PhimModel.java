package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class PhimModel implements Serializable {
    @SerializedName("anh")
    String image;
    @SerializedName("idPhim")
    String idPhim;
    @SerializedName("tenPhim")
    String tenPhim;
    @SerializedName("ngonNgu")
    String ngonNgu;
    @SerializedName("moTa")
    String moTa;
    @SerializedName("hangSX")
    String hangSX;
    @SerializedName("nuocSX")
    String nuocSX;
    @SerializedName("namSX")
    String namSX;
    @SerializedName("thoiLuong")
    String thoiLuong;
    @SerializedName("daoDien")
    String daoDien;
    @SerializedName("trangThai")
    String trangThai;
    @SerializedName("ngayChieu")
    Date ngayChieu;
    @SerializedName("caChieu")
    String caChieu;
    @SerializedName("giaPhim")
    String giaPhim;
    @SerializedName("tenPhongChieu")
    String tenPhong;
    @SerializedName("tenTheLoai")
    String theLoai;
    @SerializedName("idLichChieu")
    int idLichChieu;
    @SerializedName("idPhongChieu")
    int idPhongChieu;

    public int getIdPhongChieu() {
        return idPhongChieu;
    }

    public void setIdPhongChieu(int idPhongChieu) {
        this.idPhongChieu = idPhongChieu;
    }

    public PhimModel(String image, String idPhim, String tenPhim, String ngonNgu, String moTa, String hangSX, String nuocSX, String namSX, String thoiLuong, String daoDien, String trangThai, Date ngayChieu, String caChieu, String giaPhim, String tenPhong, String theLoai) {
        this.image = image;
        this.idPhim = idPhim;
        this.tenPhim = tenPhim;
        this.ngonNgu = ngonNgu;
        this.moTa = moTa;
        this.hangSX = hangSX;
        this.nuocSX = nuocSX;
        this.namSX = namSX;
        this.thoiLuong = thoiLuong;
        this.daoDien = daoDien;
        this.trangThai = trangThai;
        this.ngayChieu = ngayChieu;
        this.caChieu = caChieu;
        this.giaPhim = giaPhim;
        this.tenPhong = tenPhong;
        this.theLoai = theLoai;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public PhimModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdPhim() {
        return idPhim;
    }

    public void setIdPhim(String idPhim) {
        this.idPhim = idPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHangSX() {
        return hangSX;
    }

    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }

    public String getNuocSX() {
        return nuocSX;
    }

    public void setNuocSX(String nuocSX) {
        this.nuocSX = nuocSX;
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

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(Date ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getCaChieu() {
        return caChieu;
    }

    public void setCaChieu(String caChieu) {
        this.caChieu = caChieu;
    }

    public String getGiaPhim() {
        return giaPhim;
    }

    public void setGiaPhim(String giaPhim) {
        this.giaPhim = giaPhim;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public int getIdLichChieu() {
        return idLichChieu;
    }

    public void setIdLichChieu(int idLichChieu) {
        this.idLichChieu = idLichChieu;
    }
}
