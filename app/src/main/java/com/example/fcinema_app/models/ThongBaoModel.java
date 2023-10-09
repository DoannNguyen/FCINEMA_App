package com.example.fcinema_app.models;

public class ThongBaoModel {
    private String tenPhim, thoiGian;

    public ThongBaoModel(String tenPhim, String thoiGian) {
        this.tenPhim = tenPhim;
        this.thoiGian = thoiGian;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
