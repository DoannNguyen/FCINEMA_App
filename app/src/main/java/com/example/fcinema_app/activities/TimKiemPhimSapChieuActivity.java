package com.example.fcinema_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.fcinema_app.adapters.PhimSapChieuAdapter;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.PhimSapChieuModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimKiemPhimSapChieuActivity extends AppCompatActivity {

    private EditText edSearch;
    private ImageView imgBack;
    private TextView tvHidden;
    private GridView gridViewSearch;
    private List<PhimSapChieuModel> list=new ArrayList<>();
    private List<PhimSapChieuModel> originalList=new ArrayList<>();
    private PhimSapChieuAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_phim_sap_chieu);
        edSearch=findViewById(R.id.edSearchNewFilm);
        gridViewSearch=findViewById(R.id.gridViewSearchNewFilm);
        tvHidden=findViewById(R.id.tvNotificationSearchNewFilm);
        imgBack=findViewById(R.id.imgBackFromSearchNewFilm);

        adapter=new PhimSapChieuAdapter(this,list);
        gridViewSearch.setAdapter(adapter);
        gridViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TimKiemPhimSapChieuActivity.this, ChiTietPhimSapChieuActivity.class);
                intent.putExtra("phimSC", (PhimSapChieuModel) adapter.getItem(i));
                startActivity(intent);
                finish();

            }
        });

        imgBack.setOnClickListener(v -> {
            finish();
        });

        getAllPhimSC();
        onChangeText();

    }

    private void updateVisibility() {
        if (list.isEmpty()) {
            tvHidden.setVisibility(View.VISIBLE);
        } else {
            tvHidden.setVisibility(View.GONE);
        }
    }
    private void onChangeText(){
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterList(String searchText) {
        List<PhimSapChieuModel> filteredList = new ArrayList<>();

        for (PhimSapChieuModel phim : originalList) {
            if (phim.getTenPhim().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(phim);
            }
        }

        list.clear();
        list.addAll(filteredList);

        adapter.notifyDataSetChanged();
        updateVisibility();
    }
    private void getAllPhimSC(){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);

        Call<List<PhimSapChieuModel>> call = apiInterface.getAllPhimSC();
        call.enqueue(new Callback<List<PhimSapChieuModel>>() {
            @Override
            public void onResponse(Call<List<PhimSapChieuModel>> call, Response<List<PhimSapChieuModel>> response) {
                if(response.isSuccessful()){
                    list.clear();
                    originalList.addAll(response.body());
                    list.addAll(originalList);
                    adapter.notifyDataSetChanged();
                    updateVisibility();
                }else{
                    Log.e("TAG", "onResponse: error" );
                }
            }

            @Override
            public void onFailure(Call<List<PhimSapChieuModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+ t.getMessage());
            }
        });
    }
}