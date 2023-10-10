package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimSapChieuModel;

public class ChiTietPhimSapChieuActivity extends AppCompatActivity {

    private ImageView image;
    private TextView tenPhim, theLoai, quocGia, namSX, thoiLuong, ngonNgu, daoDien, moTa;
    private androidx.appcompat.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phim_sap_chieu);
        image = findViewById(R.id.imageCTPSC);
        tenPhim = findViewById(R.id.tenPhimCTPSC);
        theLoai = findViewById(R.id.theLoaiCTPSC);
        quocGia = findViewById(R.id.theLoaiCTPSC);
        namSX = findViewById(R.id.NamCTPSC);
        thoiLuong = findViewById(R.id.ThoiLuongCTPSC);
        ngonNgu = findViewById(R.id.NgonNguCTPSC);
        daoDien = findViewById(R.id.NgonNguCTPSC);
        moTa = findViewById(R.id.motaCTPSC);
        mToolbar = findViewById(R.id.toolbarPSC);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        PhimSapChieuModel phimSapChieuModel = (PhimSapChieuModel) intent.getSerializableExtra("phimSC");

        if(phimSapChieuModel != null){
            image.setImageResource(phimSapChieuModel.getImage());
            tenPhim.setText(phimSapChieuModel.getTenPhim());
            theLoai.setText(phimSapChieuModel.getTheLoai());
            quocGia.setText(phimSapChieuModel.getQuocGia());
            namSX.setText(phimSapChieuModel.getNamSX());
            thoiLuong.setText(phimSapChieuModel.getThoiLuong());
            ngonNgu.setText(phimSapChieuModel.getNgonNgu());
            daoDien.setText(phimSapChieuModel.getDaoDien());
            moTa.setText(phimSapChieuModel.getMoTa());

        }
    }
}