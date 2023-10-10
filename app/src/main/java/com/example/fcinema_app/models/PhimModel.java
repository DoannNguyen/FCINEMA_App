package com.example.fcinema_app.models;

import java.io.Serializable;

public class PhimModel implements Serializable {
    int image;
    String idPhim, tenPhim, ngonNgu, moTa, hangSX, nuocSX, namSX, thoiLuong, daoDien, trangThai;
    String ngayChieu, caChieu, giaPhim, tenPhong, theLoai;

    public PhimModel(int image, String idPhim, String tenPhim, String ngonNgu, String moTa, String hangSX, String nuocSX, String namSX, String thoiLuong, String daoDien, String trangThai, String ngayChieu, String caChieu, String giaPhim, String tenPhong, String theLoai) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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

    public String getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
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
}
