package com.example.fcinema_app.Utils;

import com.example.fcinema_app.models.BanerModel;
import com.example.fcinema_app.models.DoAnModel;
import com.example.fcinema_app.models.GheDat;
import com.example.fcinema_app.models.LichSuVeModel;
import com.example.fcinema_app.models.NguoiDung;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.PhimSapChieuModel;
import com.example.fcinema_app.models.RequestAuthEmail;
import com.example.fcinema_app.models.RequestData;
import com.example.fcinema_app.models.ResetPasswordRequest;
import com.example.fcinema_app.models.TheLoaiModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

//    POST[]/nguoidung/xacthuc
    @POST("/nguoidung/xacthuc")
    Call<NguoiDung> authEmail(@Body RequestAuthEmail requestAuthEmail);
//    POST[]/nguoidung/dangnhap
    @POST("/nguoidung/dangnhap")
    Call<ResponseBody> loginUser(@Body NguoiDung nguoiDung);
//    POST[]/nguoidung/dangky
    @POST("/nguoidung/dangky")
    Call<ResponseBody> regiserUser(@Body RequestAuthEmail requestAuthEmail);
//POST[]/resetMatKhauRequest
    @POST("/resetMatKhauRequest")
    Call<NguoiDung> resetMatKhauRequest(@Body ResetPasswordRequest resetPasswordRequest);
//    POST[]/comfirmRestMatKhau
    @POST("/comfirmResetMatKhau")
    Call<Void> comfirmRestMatKhau(@Body ResetPasswordRequest resetPasswordRequest);
//    GET[]/nguoidung/thongtin/:email
    @GET("/nguoidung/thongtin/{email}")
    Call<NguoiDung> getNguoiDungByEmail (@Path("email") String email);
//    PUT[]/nguoidung/doithon'gtin/:email
    @PUT("/nguoidung/doithongtin/{email}")
    Call<Void> changeThongTinNguoiDungByEmail(@Path("email")String emaill,@Body NguoiDung nguoiDung);
//    PUT[]/nguoidung/doimatkhau/:email
    @PUT("/nguoidung/doimatkhau/{email}")
    Call<Void> changeMatKhauNguoiDungByEmail(@Path("email")String email,@Body NguoiDung nguoiDung);
//    PUT[]/resquestXoaTaiKhoan


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
    @GET("/ve/dsve/{email}")

    Call<List<LichSuVeModel>> getVeDat(@Path("email") String email);
    @GET("ghe/{id}")
    Call<List<GheDat>> getGheDat(@Path("id") int id);
    @GET("theLoai")
    Call<List<TheLoaiModel>> getTheLoai();
    @GET("phimSC/{id}")
    Call<List<PhimSapChieuModel>> getPhimSCbyTheLoai(@Path("id")int id);
    @GET("phimDC/{day}")
    Call<List<PhimModel>> getPhimDCbyTheLoai(@Path("day")String day);
    @GET("doan")
    Call<List<DoAnModel>> getDoAn();
    @POST("/dangky/gg")
    Call<ResponseBody> regisWithGG(@Body NguoiDung nguoiDung);
    @GET("/doan/{veid}")
    Call<List<DoAnModel>> getDoAnByVe(@Path("veid")String id);

    // baners
    @GET("/banner")
    Call<List<BanerModel>> getBaners();

}
