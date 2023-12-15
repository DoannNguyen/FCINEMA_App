package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimSapChieuModel;

import java.text.DecimalFormat;

public class ChiTietPhimSapChieuActivity extends AppCompatActivity {

    private ImageView image;
    private TextView tenPhim, theLoai, quocGia, namSX, thoiLuong, ngonNgu, daoDien, moTa, tvDienVien;
    private androidx.appcompat.widget.Toolbar mToolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phim_sap_chieu);
        image = findViewById(R.id.imageCTPSC);
        tenPhim = findViewById(R.id.tenPhimCTPSC);
        theLoai = findViewById(R.id.theLoaiCTPSC);
        quocGia = findViewById(R.id.QuocGiaCTPSC);
        namSX = findViewById(R.id.NamCTPSC);
        thoiLuong = findViewById(R.id.ThoiLuongCTPSC);
        ngonNgu = findViewById(R.id.NgonNguCTPSC);
        daoDien = findViewById(R.id.DaoDienCTPSC);
        moTa = findViewById(R.id.motaCTPSC);
        mToolbar = findViewById(R.id.toolbarPSC);
        tvDienVien = findViewById(R.id.DienVienCTPSC);


        findViewById(R.id.imgBackFromDetailPSC).setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        PhimSapChieuModel phimSapChieuModel = (PhimSapChieuModel) intent.getSerializableExtra("phimSC");

        if(phimSapChieuModel != null){
            String imageUrl = phimSapChieuModel.getImage(); // URL của ảnh
            Glide.with(this)
                    .load(imageUrl)
                    .into(image);
            tenPhim.setText(phimSapChieuModel.getTenPhim());
            theLoai.setText(phimSapChieuModel.getTheLoai());
            quocGia.setText(phimSapChieuModel.getQuocGia());
            namSX.setText(phimSapChieuModel.getNamSX());
            thoiLuong.setText(phimSapChieuModel.getThoiLuong());
            ngonNgu.setText(phimSapChieuModel.getNgonNgu());
            daoDien.setText(phimSapChieuModel.getDaoDien());
            moTa.setText(phimSapChieuModel.getMoTa());
            if(phimSapChieuModel.getDienVien() != null){
                tvDienVien.setText(phimSapChieuModel.getDienVien().replace("\"","").replace("\\",""));
            }else {
                tvDienVien.setText("");
            }
        }
    }
}