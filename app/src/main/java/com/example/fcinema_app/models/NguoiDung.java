package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class NguoiDung implements Serializable {

    @SerializedName("email")

    private String email;
    @SerializedName("hoTen")

    private String hoTen;
    @SerializedName("matKhau")

    private String matKhau;
    @SerializedName("dienThoai")

    private String dienThoai;
    @SerializedName("anh")

    private String anh;
    @SerializedName("ngaySinh")

    private Date ngaySinh;
    @SerializedName("diaChi")

    private String diaChi;
    @SerializedName("hienThi")

    private Integer hienThi;
    @SerializedName("matKhauCu")

    private String matKhauCu;
    public NguoiDung() {
    }



    public NguoiDung(String email, String hoTen, String matKhau, String dienThoai, String anh, Date ngaySinh, String diaChi, Integer hienThi) {
        this.email = email;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.dienThoai = dienThoai;
        this.anh = anh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.hienThi = hienThi;
    }

    public NguoiDung(String email, String matKhau) {
        this.email = email;
        this.matKhau = matKhau;
    }

    public NguoiDung(String email, String anh, String hoTen){
        this.email = email;
        this.anh = anh;
        this.hoTen = hoTen;
    }

    public NguoiDung(String hoTen, String dienThoai, String anh, Date ngaySinh, String diaChi, Integer hienThi) {
        this.hoTen = hoTen;
        this.dienThoai = dienThoai;
        this.anh = anh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.hienThi = hienThi;
    }

    public NguoiDung(String email, String hoTen, String matKhau, String dienThoai, Date ngaySinh, String diaChi, Integer hienThi) {
        this.email = email;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.dienThoai = dienThoai;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.hienThi = hienThi;
    }

    public NguoiDung(String hoTen, String dienThoai, String anh, Date ngaySinh, String diaChi) {
        this.hoTen = hoTen;
        this.dienThoai = dienThoai;
        this.anh = anh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
    }

    public NguoiDung(String email, String matKhau, Integer hienThi) {
        this.email = email;
        this.matKhau = matKhau;
        this.hienThi = hienThi;
    }

    public NguoiDung(String matKhau) {
        this.matKhau = matKhau;
    }
    public NguoiDung(String matKhauCu,String matKhau,  int hienThi) {
        this.matKhau = matKhau;
        this.matKhauCu=matKhauCu;
        this.hienThi=hienThi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getHienThi() {
        return hienThi;
    }

    public void setHienThi(Integer hienThi) {
        this.hienThi = hienThi;
    }

    public String getMatKhauCu() {
        return matKhauCu;
    }

    public void setMatKhauCu(String matKhauCu) {
        this.matKhauCu = matKhauCu;
    }
}
