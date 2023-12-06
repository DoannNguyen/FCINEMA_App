package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.Utils.OnMinusItemClick;
import com.example.fcinema_app.Utils.OnPlusItemClick;
import com.example.fcinema_app.adapters.DoAnAdapter;
import com.example.fcinema_app.models.DoAnModel;
import com.example.fcinema_app.models.PhimModel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChonDoAnActivity extends AppCompatActivity {

    private List<DoAnModel> mList;
    private DoAnAdapter mAdapter;
    private TextView tvTongTien;
    private Button btnTiepTuc;
    private ImageView imgBack;
    private NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_do_an);

        mList = new ArrayList<>();
        ListView listView = findViewById(R.id.lvDoAn);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        imgBack = findViewById(R.id.imgBack);
        formatter = new DecimalFormat("###,###,##0");
        mAdapter = new DoAnAdapter(ChonDoAnActivity.this,mList);
        getDoAn();
        listView.setAdapter(mAdapter);

        Bundle bundle = getIntent().getBundleExtra("value");
        PhimModel phimModel = (PhimModel) bundle.getSerializable("phim");
        Integer soVe = bundle.getInt("soLuongVe");

        tvTongTien.setText((formatter.format(soVe*Integer.parseInt(phimModel.getGiaPhim()) + mAdapter.getSum())+"đ"));
        mAdapter.setOnPlusItemClick(new OnPlusItemClick() {
            @Override
            public void OnPlusClick() {
                tvTongTien.setText((formatter.format(soVe*Integer.parseInt(phimModel.getGiaPhim()) + mAdapter.getSum())+"đ"));
            }
        });
        mAdapter.setOnMinusItemClick(new OnMinusItemClick() {
            @Override
            public void onMinusClick() {
                tvTongTien.setText((formatter.format(soVe*Integer.parseInt(phimModel.getGiaPhim()) + mAdapter.getSum())+"đ"));
            }
        });
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<DoAnModel> doAnModelList = new ArrayList<>();
                mList.forEach(doan -> {
                    if(doan.getSoLuong() > 0){
                        doAnModelList.add(doan);
                    }
                });
                bundle.putSerializable("doAn", (Serializable) doAnModelList);
                Intent intent = new Intent(ChonDoAnActivity.this, ThanhToanActivity.class);
                intent.putExtra("value", bundle);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getDoAn(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<DoAnModel>> call = apiInterface.getDoAn();
        call.enqueue(new Callback<List<DoAnModel>>() {
            @Override
            public void onResponse(Call<List<DoAnModel>> call, Response<List<DoAnModel>> response) {
                if(response.isSuccessful()){
                    mList.clear();
                    mList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DoAnModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }
}