package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("resetCodeBody")
    private int resetCodeBody;

    @SerializedName("matKhau")
    private String newPassword;

    public ResetPasswordRequest(String email) {
        this.email = email;
    }

    public ResetPasswordRequest(String email, String newPassword, int resetCodeBody) {
        this.email = email;
        this.newPassword = newPassword;
        this.resetCodeBody = resetCodeBody;

    }
}