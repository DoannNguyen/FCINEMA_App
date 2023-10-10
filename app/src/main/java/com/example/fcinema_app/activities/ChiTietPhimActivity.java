package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimModel;

public class ChiTietPhimActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView tvTenPhim, tvGiaPhim, tvTheLoai, tvQuocGia, tvNamSX, tvThoiLuong, tvNgonNgu, tvDaoDien,
            tvNgayChieu, tvCaChieu, tvPhongchieu, tvMoTa;
    androidx.appcompat.widget.Toolbar mToolbar;
    Button  btnChonSuatChieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phim);

        tvTenPhim = findViewById(R.id.tvTenPhimCTP);
        tvGiaPhim = findViewById(R.id.tvGiaPhimCTP);
        mImageView = findViewById(R.id.img_postterCTP);
        tvTheLoai = findViewById(R.id.tvTheLoaiCTP);
        tvQuocGia = findViewById(R.id.tvQuocGiaCTP);
        tvNamSX = findViewById(R.id.tvNamCTP);
        tvThoiLuong = findViewById(R.id.tvThoiLongCTP);
        tvNgonNgu = findViewById(R.id.tvNgonNguCTP);
        tvDaoDien = findViewById(R.id.tvDaoDienCTP);
        tvNgayChieu = findViewById(R.id.tvNgayChieuCTP);
        tvCaChieu = findViewById(R.id.tvCaChieuCTP);
        tvPhongchieu = findViewById(R.id.tvPhongChieuCTP);
        mToolbar = findViewById(R.id.toolbarCTV);
        btnChonSuatChieu = findViewById(R.id.btnChonSUatChieu);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        PhimModel phimModel = (PhimModel) getIntent().getSerializableExtra("phim");
        if(phimModel != null){
            tvTenPhim.setText(phimModel.getTenPhim());
            tvGiaPhim.setText(phimModel.getGiaPhim());
            mImageView.setImageResource(phimModel.getImage());
            tvTheLoai.setText((phimModel.getTheLoai()));
            tvQuocGia.setText(phimModel.getNuocSX());
            tvNamSX.setText(phimModel.getNamSX());
            tvThoiLuong.setText(phimModel.getThoiLuong());
            tvNgonNgu.setText(phimModel.getNgonNgu());
            tvDaoDien.setText(phimModel.getDaoDien());
            tvNgayChieu.setText(phimModel.getNgayChieu());
            tvCaChieu.setText(phimModel.getCaChieu());
            tvPhongchieu.setText(phimModel.getTenPhong());
        }

        btnChonSuatChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietPhimActivity.this,MuaVeActivity.class);
                intent.putExtra("phim", phimModel);
                startActivity(intent);
            }
        });
    }
}