package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ChiTietPhimActivity extends AppCompatActivity {

    ImageView mImageView;
    TextView tvTenPhim, tvGiaPhim, tvTheLoai, tvQuocGia, tvNamSX, tvThoiLuong, tvNgonNgu, tvDaoDien,
            tvNgayChieu, tvCaChieu, tvPhongchieu, tvMoTa;
    androidx.appcompat.widget.Toolbar mToolbar;
    Button  btnChonSuatChieu;
    private SimpleDateFormat mSimpleDateFormat;

    @SuppressLint("MissingInflatedId")
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
        tvMoTa = findViewById(R.id.tvMotaCTP);
        mToolbar = findViewById(R.id.toolbarCTV);
        btnChonSuatChieu = findViewById(R.id.btnChonSUatChieu);

        mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        findViewById(R.id.imgBackFromDetailPC).setOnClickListener(v -> {
            finish();
        });

        PhimModel phimModel = (PhimModel) getIntent().getSerializableExtra("phim");
        if(phimModel != null){
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            Float giaPhim= Float.valueOf(phimModel.getGiaPhim());
            String formatGiaPhim = decimalFormat.format(giaPhim);
            tvTenPhim.setText(phimModel.getTenPhim());
            tvGiaPhim.setText(formatGiaPhim+" đ");
            String imageUrl = phimModel.getImage();
            Glide.with(this)
                    .load(imageUrl)
                    .into(mImageView);
            tvTheLoai.setText((phimModel.getTheLoai()));
            tvQuocGia.setText(phimModel.getNuocSX());
            tvNamSX.setText(phimModel.getNamSX());
            tvThoiLuong.setText(phimModel.getThoiLuong());
            tvNgonNgu.setText(phimModel.getNgonNgu());
            tvDaoDien.setText(phimModel.getDaoDien());
            tvNgayChieu.setText(mSimpleDateFormat.format(phimModel.getNgayChieu()));
            tvCaChieu.setText(phimModel.getCaChieu());
            tvPhongchieu.setText(phimModel.getTenPhong());
            tvMoTa.setText(phimModel.getMoTa());
        }

        btnChonSuatChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietPhimActivity.this,MuaVeActivity.class);
                intent.putExtra("phim", phimModel);
                intent.putExtra("email", getIntent().getSerializableExtra("email"));
                startActivity(intent);
            }
        });
    }
}