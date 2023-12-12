package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

public class RequestAuthEmail {
    @SerializedName("email")
    private String email;
    @SerializedName("resetCodeBody")
    private int resetCodeBody;

    private NguoiDung nguoiDung;

    public RequestAuthEmail(String email, NguoiDung nguoiDung) {
        this.email = email;
        this.nguoiDung = nguoiDung;
    }

    public RequestAuthEmail(int resetCodeBody, NguoiDung nguoiDung) {
        this.resetCodeBody = resetCodeBody;
        this.nguoiDung = nguoiDung;
    }

    public RequestAuthEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}
