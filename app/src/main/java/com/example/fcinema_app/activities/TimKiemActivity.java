package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.adapters.PhimAdapter;
import com.example.fcinema_app.fragments.PhimDangChieuFragment;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.ProgressDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity {

    private ImageView imgBack;
    private GridView mGridView;
    private PhimAdapter mAdapter;
    private List<PhimModel> mList;
    private ProgressDialog mProgressDialog;
    private Dialog mDialog;
    private APIInterface mAPIInterface;
    private EditText mEditText;
    private TextView tvNoItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        imgBack = findViewById(R.id.imgBackFromSearch);
        mGridView = findViewById(R.id.gridViewSearch);
        mEditText = findViewById(R.id.edSearch);
        tvNoItem = findViewById(R.id.tvNoItemSearch);
        imgBack.setOnClickListener(view -> {
            finish();
        });
        mGridView.setTextFilterEnabled(true);
        mAPIInterface  = APIClient.getClient().create(APIInterface.class);
        mDialog = new Dialog(TimKiemActivity.this);
        mProgressDialog = new ProgressDialog(mDialog);
        mProgressDialog.DialogShowing();

        mList = new ArrayList<>();
        mAdapter = new PhimAdapter(TimKiemActivity.this,mList);
        getAllPhim(mList,mAdapter, mProgressDialog, mAPIInterface);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TimKiemActivity.this, ChiTietPhimActivity.class);
                intent.putExtra("phim", (PhimModel) mAdapter.getItem(i));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);

            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence);
                if(mAdapter.getCount() == 0){
                    tvNoItem.setVisibility(View.VISIBLE);
                }else{
                    tvNoItem.setVisibility(View.GONE);
                }
                if(charSequence.length() == 0){
                    tvNoItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void getAllPhim(List<PhimModel> modelList, PhimAdapter adapter, ProgressDialog progressDialog, APIInterface APIInterface){
        Call<List<PhimModel>> call = APIInterface.getAllPhimDC();
        call.enqueue(new Callback<List<PhimModel>>() {
            @Override
            public void onResponse(Call<List<PhimModel>> call, Response<List<PhimModel>> response) {
                if(response.isSuccessful()){
                    modelList.clear();
                    modelList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressDialog.DialogDismiss();
                }else{
                    Log.e("TAG", "onResponse: error " );
                }

            }

            @Override
            public void onFailure(Call<List<PhimModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }
}