package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.LichSuVeModel;
import com.example.fcinema_app.models.PhimSapChieuModel;

public class ChiTietVeActivity extends AppCompatActivity {

    private TextView tenPhim, giaTien, trangThai, thoiGian, maVe, phongChieu, ngayChieu, caChieu, soGhe, hinhThucTT, tongTT;
    private androidx.appcompat.widget.Toolbar mToolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ve);

        tenPhim = findViewById(R.id.tenPhimCTV);
        giaTien = findViewById(R.id.giaTienCTV);
        trangThai = findViewById(R.id.trangthaiCTV);
        thoiGian = findViewById(R.id.thoigianCTV);
        maVe = findViewById(R.id.maveCTV);
        phongChieu = findViewById(R.id.phongchieuCTV);
        ngayChieu = findViewById(R.id.ngaychieuCTV);
        caChieu = findViewById(R.id.cachieuCTV);
        soGhe = findViewById(R.id.sogheCTV);
        hinhThucTT = findViewById(R.id.thanhtoanCTV);
        tongTT = findViewById(R.id.tongTTCTV);
        mToolbar = findViewById(R.id.toolbarCTV);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        LichSuVeModel lichSuVeModel = (LichSuVeModel) intent.getSerializableExtra("LCV");
        if (lichSuVeModel != null){
            tenPhim.setText(lichSuVeModel.getTenPhim());
            giaTien.setText(lichSuVeModel.getGiaVe());
            trangThai.setText((lichSuVeModel.getTrangThai()));
            thoiGian.setText(lichSuVeModel.getThoiGian());
            maVe.setText(lichSuVeModel.getMaVe());
            phongChieu.setText(lichSuVeModel.getPhongChieu());
            ngayChieu.setText(lichSuVeModel.getNgayChieu());
            caChieu.setText(lichSuVeModel.getCaChieu());
            soGhe.setText(lichSuVeModel.getSoGhe());
            hinhThucTT.setText(lichSuVeModel.getHinhThucTT());
            tongTT.setText(lichSuVeModel.getTongTT());
        }
    }
}