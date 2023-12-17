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
    private String caChieu;
    @SerializedName("idVe")
    private String maVe;
    @SerializedName("ngayChieu")
    private Date ngayChieu;
    @SerializedName("ngayMua")
    private Date ngayMua;
    @SerializedName("tenPhongChieu")
    private String phongChieu;
    @SerializedName("tenGhe")
    private String soGhe;
    @SerializedName("tongTien")
    private String tongTT;
    @SerializedName("soVe")
    private String soluongVe;
    @SerializedName("phuongThucTT")
    private String phuongThucTT;
    @SerializedName("anh")

    private String anh;
    public LichSuVeModel() {
    }

    public LichSuVeModel(String tenPhim, String giaVe, int trangThai, String caChieu, String maVe, Date ngayChieu,Date ngayMua, String phongChieu, String soGhe, String hinhThucTT, String tongTT, String soluongVe,String phuongThucTT,String anh) {
        this.tenPhim = tenPhim;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
        this.caChieu = caChieu;
        this.maVe = maVe;
        this.ngayChieu = ngayChieu;
        this.phongChieu = phongChieu;

        this.soGhe = soGhe;
        this.tongTT = tongTT;
        this.soluongVe = soluongVe;
        this.phuongThucTT=phuongThucTT;
        this.anh=anh;
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


    public String getCaChieu() {
        return caChieu;
    }

    public void setCaChieu(String caChieu) {
        this.caChieu = caChieu;
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

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
