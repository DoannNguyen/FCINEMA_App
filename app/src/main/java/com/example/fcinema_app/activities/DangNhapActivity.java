package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;

public class DangNhapActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvDangKy, tvQuenMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        btnLogin = findViewById(R.id.btnLogin);
        tvDangKy = findViewById(R.id.tvDangKyNgay);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
            }
        });
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
            }
        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhapActivity.this, XacNhanMailActivity.class));
            }
        });
    }
}