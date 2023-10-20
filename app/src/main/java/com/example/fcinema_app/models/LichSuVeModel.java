package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class LichSuVeModel implements Serializable {
    @SerializedName("tenPhim")
    private String tenPhim;
    @SerializedName("giaPhim")
    private String giaVe;
    @SerializedName("trangThai")
    private int trangThai;
    @SerializedName("caChieu")
    private String thoiGian;
    @SerializedName("idVe")
    private String maVe;
    @SerializedName("ngayChieu")
    private Date ngayChieu;
    @SerializedName("tenPhongChieu")
    private String phongChieu;
    @SerializedName("tenGhe")
    private String soGhe;
    private String hinhThucTT;
    @SerializedName("tongTien")
    private String tongTT;
    @SerializedName("soVe")
    private String soluongVe;


    public LichSuVeModel() {
    }

    public LichSuVeModel(String tenPhim, String giaVe, int trangThai, String thoiGian, String maVe, Date ngayChieu, String phongChieu, String soGhe, String hinhThucTT, String tongTT, String soluongVe) {
        this.tenPhim = tenPhim;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
        this.thoiGian = thoiGian;
        this.maVe = maVe;
        this.ngayChieu = ngayChieu;
        this.phongChieu = phongChieu;

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

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
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

    public Date getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(Date ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getPhongChieu() {
        return phongChieu;
    }

    public void setPhongChieu(String phongChieu) {
        this.phongChieu = phongChieu;
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
