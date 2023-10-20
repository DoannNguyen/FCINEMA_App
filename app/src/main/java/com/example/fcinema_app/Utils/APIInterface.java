package com.example.fcinema_app.Utils;

import com.example.fcinema_app.models.GheDat;
import com.example.fcinema_app.models.LichSuVeModel;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.PhimSapChieuModel;
import com.example.fcinema_app.models.RequestData;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    //lấy toàn bộ phim đang
    @GET("phimDC")
    Call<List<PhimModel>> getAllPhimDC();
    //lấy toàn bộ phim sắp chiếu
    @GET("phimSC")
    Call<List<PhimSapChieuModel>> getAllPhimSC();
    //post dữ liệu vé và vị trí ghế ngồi
    @POST("ve")
    Call<ResponseBody> addDevice(@Body RequestData requestData);
    //lấy toàn bộ vé đã đặt
    @GET("ve")
    Call<List<LichSuVeModel>> getVeDat();
    @GET("ghe/{id}")
    Call<List<GheDat>> getGheDat(@Path("id") int id);
}
