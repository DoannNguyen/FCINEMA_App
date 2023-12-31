package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.adapters.DoAnAdapter2;
import com.example.fcinema_app.adapters.LichSuVeAdapter;
import com.example.fcinema_app.models.DoAnModel;
import com.example.fcinema_app.models.LichSuVeModel;
import com.example.fcinema_app.models.PhimSapChieuModel;
import com.example.fcinema_app.models.VeModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietVeActivity extends AppCompatActivity {

    private TextView tenPhim, giaTien, trangThai, thoiGian, maVe, phongChieu, ngayChieu, soGhe, tongSP,tongVe;
    private TextView hinhThucTT, tongTT,tvCaChieu, tvNoProduct;
    private ImageView imgPoster;
    private androidx.appcompat.widget.Toolbar mToolbar;
    private SimpleDateFormat mSimpleDateFormat;
    private RecyclerView mListView;
    private DoAnAdapter2 mAnAdapter2;
    private List<DoAnModel> mList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ve);

        tenPhim = findViewById(R.id.tenPhimCTV);
        giaTien = findViewById(R.id.giaTienCTV);
        trangThai = findViewById(R.id.trangthaiCTV);
        thoiGian = findViewById(R.id.thoigianCTV);
        maVe = findViewById(R.id.maveCTV);
        phongChieu = findViewById(R.id.phongchieuCTV);
        ngayChieu = findViewById(R.id.ngaychieuCTV);
        soGhe = findViewById(R.id.sogheCTV);
        hinhThucTT = findViewById(R.id.thanhtoanCTV);
        tongTT = findViewById(R.id.tongTTCTV);
        mToolbar = findViewById(R.id.toolbarCTV);
        imgPoster=findViewById(R.id.imgPosterDetailVe);
        tvCaChieu=findViewById(R.id.tvCaChieuCTV);
        mListView = findViewById(R.id.lvDoAn3);
        mAnAdapter2 = new DoAnAdapter2(this,mList);
        tvNoProduct = findViewById(R.id.tvNoProduct);
        tongVe=findViewById(R.id.tongTienVeCTV);
        tongSP=findViewById(R.id.tongTienSPCTV);

        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        Intent intent = getIntent();
        LichSuVeModel lichSuVeModel = (LichSuVeModel) intent.getSerializableExtra("LCV");
        if (lichSuVeModel != null){

            String imageUrl = lichSuVeModel.getAnh();
            Glide.with(this)
                    .load(imageUrl)
                    .into(imgPoster);
            tenPhim.setText(lichSuVeModel.getTenPhim());
            Float giaVe= Float.valueOf(lichSuVeModel.getGiaVe());
            String formatGiaVe = decimalFormat.format(giaVe);

            giaTien.setText(formatGiaVe+" đ");
            if(lichSuVeModel.getTrangThai() == 1){
                trangThai.setText("Chưa thanh toán");
                trangThai.setTextColor(ContextCompat.getColor(ChiTietVeActivity.this,R.color.darkRed));
            }else if(lichSuVeModel.getTrangThai()==0){
                trangThai.setText("Đã thanh toán");
                trangThai.setTextColor(ContextCompat.getColor(ChiTietVeActivity.this,R.color.darKGreen));
            }else{
                trangThai.setText("Đã hết hạn");
                trangThai.setTextColor(ContextCompat.getColor(ChiTietVeActivity.this,R.color.earthy));
            }
            hinhThucTT.setText(lichSuVeModel.getPhuongThucTT());
            thoiGian.setText(mSimpleDateFormat.format(lichSuVeModel.getNgayMua()));
            maVe.setText(lichSuVeModel.getMaVe());
            phongChieu.setText(lichSuVeModel.getPhongChieu());
            ngayChieu.setText(mSimpleDateFormat.format(lichSuVeModel.getNgayChieu()));
            Float tongTien= Float.valueOf(lichSuVeModel.getTongTT());

            Float soLuongGhe= Float.valueOf(lichSuVeModel.getSoluongVe());
            Float tongTienVe=giaVe*soLuongGhe;
            Float tongTienSanPham=tongTien-tongTienVe;

            String formatTongTien = decimalFormat.format(tongTien);
            String formatTienve=decimalFormat.format(tongTienVe);
            String formatTienSP=decimalFormat.format(tongTienSanPham);

            tongSP.setText(""+formatTienSP+"đ");
            tongVe.setText(""+formatTienve+"đ");
            tongTT.setText(formatTongTien+" đ");
            soGhe.setText((lichSuVeModel.getSoGhe().replace("\"","")));
            tvCaChieu.setText(lichSuVeModel.getCaChieu());
            getDoAn(lichSuVeModel.getMaVe());
        }
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setAdapter(mAnAdapter2);
        findViewById(R.id.imgBackFromDetailVe).setOnClickListener(v -> {
            finish();
        });
    }
    private void getDoAn(String idVe){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<DoAnModel>> call = apiInterface.getDoAnByVe(idVe);
        call.enqueue(new Callback<List<DoAnModel>>() {
            @Override
            public void onResponse(Call<List<DoAnModel>> call, Response<List<DoAnModel>> response) {
                if(response.isSuccessful()){
                    mList.clear();
                    mList.addAll(response.body());
                    if (mList.size() != 0){
                        tvNoProduct.setVisibility(View.GONE);
                    }
                    mAnAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DoAnModel>> call, Throwable t) {

            }
        });
    }
}