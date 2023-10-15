package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.fcinema_app.R;

public class DoiMatKhauActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        mToolbar = findViewById(R.id.toolbarDMK);

        mToolbar.setNavigationIcon(R.drawable.back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}