package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.adapters.PhimAdapter;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity {

    private ImageView imgBack;
    private GridView mGridView;
    private PhimAdapter mAdapter;
    private List<PhimModel> mList;
    private APIInterface mAPIInterface;
    private androidx.appcompat.widget.SearchView mEditText;
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
        mList = new ArrayList<>();
        mAdapter = new PhimAdapter(TimKiemActivity.this,mList);
        getAllPhim(mList,mAdapter, mAPIInterface);
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
        mEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                tvNoItem.setVisibility(View.GONE);
                Log.e("TAG", "onQueryTextChange: "+mAdapter.getCount() );
                if(mAdapter.getCount() == 0){
                    tvNoItem.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

    }
    private void getAllPhim(List<PhimModel> modelList, PhimAdapter adapter, APIInterface APIInterface){
        Call<List<PhimModel>> call = APIInterface.getAllPhimDC();
        call.enqueue(new Callback<List<PhimModel>>() {
            @Override
            public void onResponse(Call<List<PhimModel>> call, Response<List<PhimModel>> response) {
                if(response.isSuccessful()){
                    modelList.clear();
                    modelList.addAll(response.body());
                    adapter.notifyDataSetChanged();
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