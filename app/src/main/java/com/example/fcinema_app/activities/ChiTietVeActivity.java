package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.adapters.LichSuVeAdapter;
import com.example.fcinema_app.models.LichSuVeModel;
import com.example.fcinema_app.models.PhimSapChieuModel;

import java.text.SimpleDateFormat;

public class ChiTietVeActivity extends AppCompatActivity {

    private TextView tenPhim, giaTien, trangThai, thoiGian, maVe, phongChieu, ngayChieu, soGhe, hinhThucTT, tongTT;
    private androidx.appcompat.widget.Toolbar mToolbar;
    private SimpleDateFormat mSimpleDateFormat;

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
        soGhe = findViewById(R.id.sogheCTV);
        hinhThucTT = findViewById(R.id.thanhtoanCTV);
        tongTT = findViewById(R.id.tongTTCTV);
        mToolbar = findViewById(R.id.toolbarCTV);

        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
            if(lichSuVeModel.getTrangThai() == 1){
                trangThai.setText("Chưa thanh toán");
                hinhThucTT.setText("Tiền mặt");
            }else{
                trangThai.setText("Đã thanh toán");
                hinhThucTT.setText("Zalopay");
            }
            thoiGian.setText(lichSuVeModel.getThoiGian());
            maVe.setText(lichSuVeModel.getMaVe());
            phongChieu.setText(lichSuVeModel.getPhongChieu());
            ngayChieu.setText(mSimpleDateFormat.format(lichSuVeModel.getNgayChieu()));
            soGhe.setText((lichSuVeModel.getSoGhe().replace("\"","")));
            tongTT.setText(lichSuVeModel.getTongTT());
        }
    }
}