package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fcinema_app.R;

public class XacNhanMailActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_mail);

        mToolbar = findViewById(R.id.toolbarQMK);
        btnSend = findViewById(R.id.btnSendOPT);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: " );
                finish();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XacNhanMailActivity.this, DoiMatKhauActivity.class));
            }
        });
    }
}