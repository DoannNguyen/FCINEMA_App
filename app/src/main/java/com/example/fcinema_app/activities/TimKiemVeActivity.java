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
import com.example.fcinema_app.adapters.LichSuVeAdapter;
import com.example.fcinema_app.models.LichSuVeModel;
import com.example.fcinema_app.models.PhimSapChieuModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemVeActivity extends AppCompatActivity {
    private EditText edSearch;
    private ImageView imgBack;
    private TextView tvHidden;
    private GridView gridViewSearch;
    private List<LichSuVeModel>list=new ArrayList<>();
    private List<LichSuVeModel>originalList=new ArrayList<>();
    private LichSuVeAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_ve);
        edSearch=findViewById(R.id.edSearchTicket);
        tvHidden=findViewById(R.id.tvNotificationSearchTicket);
        gridViewSearch=findViewById(R.id.gridViewSearchTicket);

        adapter=new LichSuVeAdapter(this,list);
        gridViewSearch.setAdapter(adapter);
        gridViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TimKiemVeActivity.this, ChiTietVeActivity.class);
                intent.putExtra("LCV", (LichSuVeModel) adapter.getItem(i));
                startActivity(intent);
                finish();

            }
        });


        getAllVeByEmail();
        onchangeText();

    }
    private void updateVisibility() {
        if (list.isEmpty()) {
            tvHidden.setVisibility(View.VISIBLE);
        } else {
            tvHidden.setVisibility(View.GONE);
        }
    }

    private void onchangeText(){
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
        List<LichSuVeModel> filteredList = new ArrayList<>();

        for (LichSuVeModel ve : originalList) {
            if (ve.getTenPhim().toLowerCase().contains(searchText.toLowerCase()) ||
                ve.getMaVe().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(ve);
            }
        }

        list.clear();
        list.addAll(filteredList);

        adapter.notifyDataSetChanged();
        updateVisibility();
    }


    private void getAllVeByEmail() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<LichSuVeModel>> call = apiInterface.getVeDat(getEmail());
        call.enqueue(new Callback<List<LichSuVeModel>>() {
            @Override
            public void onResponse(Call<List<LichSuVeModel>> call, Response<List<LichSuVeModel>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() != 0){
                        list.clear();
                        assert response.body() != null;
                        originalList.addAll(response.body());
                        list.addAll(originalList);
                        adapter.notifyDataSetChanged();
                        updateVisibility();
                    }
                }else{
                    Log.e("TAG", "onResponse: " );
                }
            }

            @Override
            public void onFailure(Call<List<LichSuVeModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

    private String getEmail(){
        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        return email;
    }
}