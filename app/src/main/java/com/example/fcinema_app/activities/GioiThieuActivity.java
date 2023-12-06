package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.fcinema_app.R;

public class GioiThieuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioi_thieu);

        findViewById(R.id.imgBackFromAbout).setOnClickListener(v -> {
            finish();
        });
    }
}