package com.example.fcinema_app.Utils;

import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.PhimSapChieuModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("getAllPhimDC")
    Call<List<PhimModel>> getAllPhimDC();
    @GET("getAllPhimSC")
    Call<List<PhimSapChieuModel>> getAllPhimSC();
}
