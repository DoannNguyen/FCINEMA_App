package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.example.fcinema_app.models.ProgressDialog;

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
    private TextView tvTongTien, tvTienDoAn;
    private Button btnTiepTuc;
    private ImageView imgBack;
    private NumberFormat formatter;
    private ProgressDialog mProgressDialog ;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_do_an);

        onBindView();
        ListView listView = findViewById(R.id.lvDoAn);
        setupProgressDialog(mProgressDialog,mDialog);
        formatter = new DecimalFormat("###,###,##0");
        mAdapter = new DoAnAdapter(ChonDoAnActivity.this,mList);
        getDoAn();
        listView.setAdapter(mAdapter);

        Bundle bundle = getIntent().getBundleExtra("value");
        PhimModel phimModel = (PhimModel) bundle.getSerializable("phim");
        Integer soVe = bundle.getInt("soLuongVe");
        tvTienDoAn.setText(formatter.format(mAdapter.getSum())+"đ");
        tvTongTien.setText((formatter.format(soVe*Integer.parseInt(phimModel.getGiaPhim()) + mAdapter.getSum())+"đ"));
        mAdapter.setOnPlusItemClick(new OnPlusItemClick() {
            @Override
            public void OnPlusClick() {
                tvTienDoAn.setText(formatter.format(mAdapter.getSum())+"đ");
                tvTongTien.setText((formatter.format(soVe*Integer.parseInt(phimModel.getGiaPhim()) + mAdapter.getSum())+"đ"));
            }
        });
        mAdapter.setOnMinusItemClick(new OnMinusItemClick() {
            @Override
            public void onMinusClick() {
                tvTienDoAn.setText(formatter.format(mAdapter.getSum())+"đ");
                tvTongTien.setText((formatter.format(soVe*Integer.parseInt(phimModel.getGiaPhim()) + mAdapter.getSum())+"đ"));
            }
        });
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.DialogShowing();
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

    private void onBindView() {
        mList = new ArrayList<>();
        tvTongTien = findViewById(R.id.tvTongTien);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        imgBack = findViewById(R.id.imgBack);
        tvTienDoAn = findViewById(R.id.tvTienDoAn);
        mDialog = new Dialog(ChonDoAnActivity.this);
        mDialog.setContentView(R.layout.progress_dialog);
        mProgressDialog = new ProgressDialog(mDialog, "Đang tải...");
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
    private void setupProgressDialog(ProgressDialog progressDialog,Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width =  WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 300;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.7f);
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressDialog.DialogDismiss();
    }
}