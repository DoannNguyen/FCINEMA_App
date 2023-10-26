package com.example.fcinema_app.Utils;

import com.example.fcinema_app.models.NguoiDung;

public interface NguoiDungCallback {
    void onNguoiDungReceived(NguoiDung nguoiDung);
    void onError(String errorMessage);
}