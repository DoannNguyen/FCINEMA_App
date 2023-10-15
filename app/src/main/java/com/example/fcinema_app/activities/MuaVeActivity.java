package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fcinema_app.R;
import com.example.fcinema_app.models.PhimModel;

import java.text.SimpleDateFormat;

public class MuaVeActivity extends AppCompatActivity {

    private GridLayout mGridView;
    private Button btnMuaVe;
    private androidx.appcompat.widget.Toolbar mToolbar;
    private TextView tvTenPhim, tvNgayChieu, tvCaChieu, tvThoiLuong, tvPhongChieu, tvTienVe, tvSoLuong;
    private SimpleDateFormat mSimpleDateFormat;
    private int clickedButtonCount = 0;
    private PhimModel model;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_ve);

        mGridView = findViewById(R.id.gridButtonContainer);
        btnMuaVe = findViewById(R.id.btnXacNhan);
        mToolbar = findViewById(R.id.toolbarDV);
        tvTenPhim = findViewById(R.id.tvTenPhimDV);
        tvCaChieu = findViewById(R.id.tvCaChieuDV);
        tvNgayChieu = findViewById(R.id.tvNgayChieuDV);
        tvThoiLuong = findViewById(R.id.tvThoiLuongDV);
        tvPhongChieu = findViewById(R.id.tvPhongChieuDV);
        tvTienVe = findViewById(R.id.tvTienveDV);
        tvSoLuong = findViewById(R.id.tvSoLuongDV);

        mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        for (int i = 0 ; i <= 15 ; i++){
            ToggleButton button = new ToggleButton(MuaVeActivity.this);
            button.setText(String.valueOf(i));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            button.setLayoutParams(params);
            button.setOnClickListener(new ToggleButtonClickListener(i));
            mGridView.addView(button);
        }

        model = (PhimModel) getIntent().getSerializableExtra("phim");
        if(model != null){
            tvTenPhim.setText(model.getTenPhim());
            tvPhongChieu.setText(model.getTenPhong());
            tvThoiLuong.setText(model.getThoiLuong());
            tvCaChieu.setText(model.getCaChieu());
            tvNgayChieu.setText(mSimpleDateFormat.format(model.getNgayChieu()));
            tvTienVe.setText(model.getGiaPhim());
        }

        btnMuaVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MuaVeActivity.this, ThanhToanActivity.class));
            }
        });

    }
    private class ToggleButtonClickListener implements View.OnClickListener {
        private int buttonIndex;


        public ToggleButtonClickListener(int index) {
            this.buttonIndex = index;
        }

        @Override
        public void onClick(View view) {
            // Xử lý sự kiện khi nút ToggleButton được nhấn
            ToggleButton button = (ToggleButton) view;
            boolean isChecked = button.isChecked();
            // Thực hiện các tác vụ cần thiết dựa trên trạng thái của nút
            // và cung cấp thông tin về buttonIndex để xác định nút cụ thể đã được nhấn
            if(isChecked){
                clickedButtonCount++;
                Toast.makeText(MuaVeActivity.this, ""+buttonIndex, Toast.LENGTH_SHORT).show();
                tvSoLuong.setText(""+clickedButtonCount);

            }else{
                clickedButtonCount--;
                tvSoLuong.setText(""+clickedButtonCount);
            }
        }
    }
}