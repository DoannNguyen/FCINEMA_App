package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ToggleButton;

import com.example.fcinema_app.R;

public class MuaVeActivity extends AppCompatActivity {

    GridLayout mGridView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_ve);

        mGridView = findViewById(R.id.gridButtonContainer);
        btn = findViewById(R.id.btnXacNhan);

        for (int i = 0 ; i <= 15 ; i++){
            ToggleButton button = new ToggleButton(MuaVeActivity.this);
            button.setText(String.valueOf(i));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            button.setLayoutParams(params);
            mGridView.addView(button);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MuaVeActivity.this, ThanhToanActivity.class));
            }
        });

    }
}