package com.example.fcinema_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.RequestData;
import com.example.fcinema_app.models.VeModel;
import com.example.fcinema_app.models.ViTriGheModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private TextView tvTenPhim, tvThoiLuong, tvNgayChieu, tvPhongChieu, tvCaChieu, tvViTri, tvGia, tvSoLuong, tvTongTT;
    private SimpleDateFormat mSimpleDateFormat;
    private VeModel veModel;
    private ViTriGheModel viTriGheModel;
    private Button btnXacNhan;
    private Gson mGson;
    private JsonArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        tvTenPhim =findViewById(R.id.tvTenPhimXNDV);
        tvThoiLuong = findViewById(R.id.tvThoiLuongXNDV);
        tvNgayChieu = findViewById(R.id.tvNgayChieuXNDV);
        tvPhongChieu = findViewById(R.id.tvPhongChieuXNDV);
        tvCaChieu = findViewById(R.id.tvCaChieuXNDV);
        tvViTri = findViewById(R.id.tvVidTriGhe);
        tvGia = findViewById(R.id.tvGIaiVeXNDV);
        tvSoLuong = findViewById(R.id.tvSoLuongXNDV);
        tvTongTT = findViewById(R.id.tvTongThanhToanXNDV);
        btnXacNhan = findViewById(R.id.btnXacNhanDatVe);


        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Bundle bundle = getIntent().getBundleExtra("value");
        PhimModel model = (PhimModel) bundle.getSerializable("phim");
        int soLuongGhe = (Integer) bundle.getInt("soLuongVe");
        ArrayList<Integer> ghe = bundle.getIntegerArrayList("ghe");
        StringBuilder stringBuilder = new StringBuilder();
        jsonArray = new JsonArray();
        for (Integer value : ghe) {
            stringBuilder.append(value).append(", ");
            jsonArray.add(value);
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        Log.e("TAG", "onCreate: "+jsonArray );

       if(model != null && soLuongGhe != 0){
           tvTenPhim.setText(model.getTenPhim());
           tvThoiLuong.setText(model.getThoiLuong());
           tvNgayChieu.setText(mSimpleDateFormat.format(model.getNgayChieu()));
           tvPhongChieu.setText(model.getTenPhong());
           tvCaChieu.setText(model.getCaChieu());
           tvViTri.setText(stringBuilder.toString());
           tvGia.setText(model.getGiaPhim());
           tvSoLuong.setText(""+soLuongGhe);
           tvTongTT.setText(""+(soLuongGhe*Integer.parseInt(model.getGiaPhim())));
       }

       veModel = new VeModel();
       veModel.setIdVe("VIE"+ new Date().getTime());
        veModel.setSoVe(soLuongGhe);
        veModel.setNgayMua(mSimpleDateFormat.format(Calendar.getInstance().getTime()));
        veModel.setTongTien((soLuongGhe*Integer.parseInt(model.getGiaPhim())));
        veModel.setNgayTT(mSimpleDateFormat.format(Calendar.getInstance().getTime()));
        veModel.setIdLichChieu(model.getIdLichChieu());

         viTriGheModel = new ViTriGheModel();
        viTriGheModel.setTenGhe(stringBuilder.toString());
        viTriGheModel.setIdPhongChieu(model.getIdPhongChieu());
        viTriGheModel.setIdVe(veModel.getIdVe());

        mToolbar = findViewById(R.id.toolbarTT);


        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVeAndViTriGhe();
            }
        });
    }

    private void AddVeAndViTriGhe(){
        RequestData requestData = new RequestData(veModel, viTriGheModel, jsonArray);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.addDevice(requestData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(ThanhToanActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }
}