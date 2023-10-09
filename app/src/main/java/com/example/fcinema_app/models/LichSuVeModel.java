package com.example.fcinema_app.models;

import java.io.Serializable;

public class LichSuVeModel implements Serializable {
    private String tenPhim, giaVe, trangThai, thoiGian, maVe, ngayChieu, phongChieu, caChieu, soGhe, hinhThucTT, tongTT, soluongVe;



    public LichSuVeModel() {
    }

    public LichSuVeModel(String tenPhim, String giaVe, String trangThai, String thoiGian, String maVe, String ngayChieu, String phongChieu, String caChieu, String soGhe, String hinhThucTT, String tongTT, String soluongVe) {
        this.tenPhim = tenPhim;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
        this.thoiGian = thoiGian;
        this.maVe = maVe;
        this.ngayChieu = ngayChieu;
        this.phongChieu = phongChieu;
        this.caChieu = caChieu;
        this.soGhe = soGhe;
        this.hinhThucTT = hinhThucTT;
        this.tongTT = tongTT;
        this.soluongVe = soluongVe;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(String giaVe) {
        this.giaVe = giaVe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public String getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getPhongChieu() {
        return phongChieu;
    }

    public void setPhongChieu(String phongChieu) {
        this.phongChieu = phongChieu;
    }

    public String getCaChieu() {
        return caChieu;
    }

    public void setCaChieu(String caChieu) {
        this.caChieu = caChieu;
    }

    public String getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(String soGhe) {
        this.soGhe = soGhe;
    }

    public String getHinhThucTT() {
        return hinhThucTT;
    }

    public void setHinhThucTT(String hinhThucTT) {
        this.hinhThucTT = hinhThucTT;
    }

    public String getTongTT() {
        return tongTT;
    }

    public void setTongTT(String tongTT) {
        this.tongTT = tongTT;
    }

    public String getSoluongVe() {
        return soluongVe;
    }

    public void setSoluongVe(String soluongVe) {
        this.soluongVe = soluongVe;
    }
}
