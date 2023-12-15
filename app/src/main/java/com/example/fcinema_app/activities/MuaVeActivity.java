package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.GheDat;
import com.example.fcinema_app.models.PhimModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MuaVeActivity extends AppCompatActivity {

    private GridLayout mGridView;
    private Button btnMuaVe;
    private androidx.appcompat.widget.Toolbar mToolbar;
    private TextView tvTenPhim, tvNgayChieu, tvCaChieu, tvThoiLuong, tvPhongChieu, tvTienVe, tvSoLuong, tvTongThanhToan;
    private SimpleDateFormat mSimpleDateFormat;
    private ImageView imgPoster;
    private int clickedButtonCount = 0;
    private int tongTT = 0;
    private PhimModel model;
    private ArrayList<Integer> in;
    private ArrayList<Integer> in2;
    private List<ToggleButton> toggleButtonList ;
    DecimalFormat decimalFormat=new DecimalFormat("###,###");
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
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
        tvTongThanhToan = findViewById(R.id.tvTongThanhToanDV);
        imgPoster=findViewById(R.id.imgPosterMV);

        mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        in = new ArrayList<>();
        in2 = new ArrayList<>();
        toggleButtonList = new ArrayList<>();

        findViewById(R.id.imgBackFromOrderVe).setOnClickListener(v -> {
            finish();
        });

        model = (PhimModel) getIntent().getSerializableExtra("phim");
        if(model != null){

            if (model.getImage() == null || model.getImage().isEmpty()) {
                imgPoster.setImageResource(R.drawable.img_default);
            } else {
                String imageUrl = model.getImage();
                Glide.with(this)
                        .load(imageUrl)
                        .into(imgPoster);
            }
            String formatVe=decimalFormat.format(Float.parseFloat(model.getGiaPhim()));
            tvTenPhim.setText(model.getTenPhim());
            tvPhongChieu.setText(model.getTenPhong());
            tvThoiLuong.setText(model.getThoiLuong());
            tvCaChieu.setText(model.getCaChieu());
            tvNgayChieu.setText(mSimpleDateFormat.format(model.getNgayChieu()));
            tvTienVe.setText(formatVe+"đ");
        }

        for (int i = 0 ; i <= 19 ; i++){
            ToggleButton button = new ToggleButton(MuaVeActivity.this);

//            button.setTextSize(13);

            button.setText(ConverterChairName(i));
            button.setTextOn(ConverterChairName(i));
            button.setTextOff(ConverterChairName(i));
            button.setBackground(ContextCompat.getDrawable(MuaVeActivity.this,R.drawable.custom_toggle_button));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            params.width = 100;
            params.height = 100;
            params.setGravity(Gravity.CENTER);

            button.setLayoutParams(params);
            button.setOnClickListener(new ToggleButtonClickListener(i));
            mGridView.addView(button);
            toggleButtonList.add(button);
        }


        getVeDat();

        btnMuaVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChonDoAnActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("phim", model);
                bundle.putString("email", getIntent().getStringExtra("email"));
                bundle.putInt("soLuongVe", clickedButtonCount);
                bundle.putIntegerArrayList("ghe", in);
                intent.putExtra("value", bundle);
                if(clickedButtonCount != 0){
                    startActivity(intent);
                }else{
                    Toast.makeText(MuaVeActivity.this, "Bạn phải chọn ghế trước khi xác nhận", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private class ToggleButtonClickListener implements View.OnClickListener {
        private int buttonIndex;
        public ToggleButtonClickListener(int index) {
            this.buttonIndex = index;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {

            ToggleButton button = (ToggleButton) view;
            boolean isChecked = button.isChecked();

            if(isChecked){
                button.setBackground(ContextCompat.getDrawable(MuaVeActivity.this,R.drawable.custom_toggle_button_checked));
                clickedButtonCount++;
                in.add(buttonIndex);
                tvSoLuong.setText(""+clickedButtonCount);
                tongTT = clickedButtonCount*Integer.parseInt(model.getGiaPhim());
                String tongTienVe=decimalFormat.format(tongTT);
                tvTongThanhToan.setText(tongTienVe+"đ");
            }else{
                button.setBackground(ContextCompat.getDrawable(MuaVeActivity.this,R.drawable.custom_toggle_button));
                in.remove(Integer.valueOf(buttonIndex));
                clickedButtonCount--;
                tvSoLuong.setText(""+clickedButtonCount);
                tongTT = clickedButtonCount*Integer.parseInt(model.getGiaPhim());
                String tongTienVe=decimalFormat.format(tongTT);
                tvTongThanhToan.setText(tongTienVe+"đ");
            }
        }
    }
    private void getVeDat(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<GheDat>> call = apiInterface.getGheDat(model.getIdLichChieu());
        call.enqueue(new Callback<List<GheDat>>() {
            @Override
            public void onResponse(Call<List<GheDat>> call, retrofit2.Response<List<GheDat>> response) {
                if(response.isSuccessful()){

                   for(int i = 0 ; i < response.body().size() ; i++){
                       String[] data = response.body().get(i).getTenGhe().replace("\"","").split(", ");
                       Log.e("TAG", "onResponse: "+data.length );
                        for(int j = 0; j < data.length ; j++){
                            in2.add(ConverterIndexChair(data[j]));
                        }
                   }
                    for (int i = 0; i < toggleButtonList.size(); i++) {
                        ToggleButton toggleButton = toggleButtonList.get(i);
                        if (in2.contains(i)) {
                            toggleButton.setEnabled(false);
                            toggleButton.setChecked(true);
                            toggleButton.setBackground(ContextCompat.getDrawable(MuaVeActivity.this,R.drawable.custom_toggle_button_focus));
                        } else {
                            toggleButton.setEnabled(true);
                        }
                    }
                    Log.e("TAG", "onResponse: "+in2.size());
                }
            }

            @Override
            public void onFailure(Call<List<GheDat>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

    public static String ConverterChairName(int i){
        if(i/5 < 1){
            return "A"+(i%5 + 1);
        }
        if(1 <= (i/5) && (i/5) < 2){
            return "B"+(i%5 + 1);
        }
        if(2 <= (i/5) && (i/5) < 3){
            return "C"+(i%5 + 1);
        }
        if(3 <= (i/5) && (i/5) < 4){
            return "D"+(i%5 + 1);
        }
        return null;
    }

    private int ConverterIndexChair(String chair){
        int D = 0;
        if((chair.charAt(0)) == 'A'){
            D += Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        if((chair.charAt(0)) == 'B'){
            D = 5 + Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        if((chair.charAt(0)) == 'C'){
            D = 10 + Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        if((chair.charAt(0)) == 'D'){
            D = 15 + Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        return D;
    }
}