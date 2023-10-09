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
    TextView tvTenPhim, tvGiaPhim;
    androidx.appcompat.widget.Toolbar mToolbar;
    Button  btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phim);

        tvTenPhim = findViewById(R.id.tvTenPhimCTP);
        tvGiaPhim = findViewById(R.id.tvGiaPhimCTP);
        mImageView = findViewById(R.id.img_postterCTP);
        mToolbar = findViewById(R.id.toolbar);
        btn = findViewById(R.id.btnChonSUatChieu);

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
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietPhimActivity.this,MuaVeActivity.class);
                intent.putExtra("phim", phimModel);
                startActivity(intent);
            }
        });
    }
}