package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.nio.Buffer;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

public class PhimModel implements Serializable {
    @SerializedName("anh")
    private String image;
    @SerializedName("idPhim")
    private String idPhim;
    @SerializedName("tenPhim")
    private String tenPhim;
    @SerializedName("ngonNgu")
    private String ngonNgu;
    @SerializedName("moTa")
    private String moTa;
    @SerializedName("hangSX")
    private String hangSX;
    @SerializedName("nuocSX")
    private String nuocSX;
    @SerializedName("namSX")
    private String namSX;
    @SerializedName("thoiLuong")
    private String thoiLuong;
    @SerializedName("daoDien")
    private String daoDien;
    @SerializedName("trangThai")
    private String trangThai;
    @SerializedName("ngayChieu")
    private Date ngayChieu;
    @SerializedName("caChieu")
    private String caChieu;
    @SerializedName("giaPhim")
    private String giaPhim;
    @SerializedName("tenPhongChieu")
    private String tenPhong;
    @SerializedName("tenTheLoai")
    private String theLoai;
    @SerializedName("idLichChieu")
    private int idLichChieu;
    @SerializedName("idPhongChieu")
    private int idPhongChieu;
    @SerializedName("dienVien")
    private String dienVien;

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

    public PhimModel(String image) {
        this.image = image;
    }

    private class anh{
        @SerializedName("type")
        private String type;
        @SerializedName("data")
        private ArrayList<Integer> mArrayList;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<Integer> getArrayList() {
            return mArrayList;
        }

        public void setArrayList(ArrayList<Integer> arrayList) {
            mArrayList = arrayList;
        }
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

    public String getDienVien() {
        return dienVien;
    }

    public void setDienVien(String dienVien) {
        this.dienVien = dienVien;
    }
}
