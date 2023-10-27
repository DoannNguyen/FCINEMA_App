package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.GheDat;
import com.example.fcinema_app.models.PhimModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MuaVeActivity extends AppCompatActivity {

    private GridLayout mGridView;
    private Button btnMuaVe;
    private androidx.appcompat.widget.Toolbar mToolbar;
    private TextView tvTenPhim, tvNgayChieu, tvCaChieu, tvThoiLuong, tvPhongChieu, tvTienVe, tvSoLuong, tvTongThanhToan;
    private SimpleDateFormat mSimpleDateFormat;
    private int clickedButtonCount = 0;
    private int tongTT = 0;
    private PhimModel model;
    private ArrayList<Integer> in;
    private ArrayList<Integer> in2;
    private List<ToggleButton> toggleButtonList ;

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

        mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        in = new ArrayList<>();
        in2 = new ArrayList<>();
        toggleButtonList = new ArrayList<>();

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        model = (PhimModel) getIntent().getSerializableExtra("phim");
        if(model != null){
            tvTenPhim.setText(model.getTenPhim());
            tvPhongChieu.setText(model.getTenPhong());
            tvThoiLuong.setText(model.getThoiLuong());
            tvCaChieu.setText(model.getCaChieu());
            tvNgayChieu.setText(mSimpleDateFormat.format(model.getNgayChieu()));
            tvTienVe.setText(model.getGiaPhim());
        }

        for (int i = 0 ; i <= 15 ; i++){
            ToggleButton button = new ToggleButton(MuaVeActivity.this);
            button.setText(ConverterChairName(i));
            button.setTextOn(ConverterChairName(i));
            button.setTextOff(ConverterChairName(i));
            button.setBackgroundResource(R.drawable.custom_toggle_button);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            button.setLayoutParams(params);
            button.setOnClickListener(new ToggleButtonClickListener(i));
            mGridView.addView(button);
            toggleButtonList.add(button);
        }

//        for (int i = 0; i < toggleButtonList.size(); i++) {
//            ToggleButton toggleButton = toggleButtonList.get(i);
//            int index = i + 1; // Giả sử index bắt đầu từ 1
//        }
        getVeDat();

        btnMuaVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
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
                clickedButtonCount++;
                in.add(buttonIndex);
                tvSoLuong.setText(""+clickedButtonCount);
                tongTT = clickedButtonCount*Integer.parseInt(model.getGiaPhim());
                tvTongThanhToan.setText(""+tongTT);
            }else{
                in.remove(Integer.valueOf(buttonIndex));
                clickedButtonCount--;
                tvSoLuong.setText(""+clickedButtonCount);
                tongTT = clickedButtonCount*Integer.parseInt(model.getGiaPhim());
                tvTongThanhToan.setText(""+tongTT);
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
        if(i/4 < 1){
            return "A"+(i%4 + 1);
        }
        if(1 <= (i/4) && (i/4) < 2){
            return "B"+(i%4 + 1);
        }
        if(2 <= (i/4) && (i/4) < 3){
            return "C"+(i%4 + 1);
        }
        if(3 <= (i/4) && (i/4) < 4){
            return "D"+(i%4 + 1);
        }
        return null;
    }

    private int ConverterIndexChair(String chair){
        int D = 0;
        if((chair.charAt(0)) == 'A'){
            D += Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        if((chair.charAt(0)) == 'B'){
            D = 4 + Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        if((chair.charAt(0)) == 'C'){
            D = 8 + Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        if((chair.charAt(0)) == 'D'){
            D = 12 + Integer.parseInt(String.valueOf(chair.charAt(1))) - 1;
        }
        return D;
    }

}