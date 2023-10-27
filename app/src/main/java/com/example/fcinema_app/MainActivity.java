package com.example.fcinema_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.Utils.NguoiDungCallback;
import com.example.fcinema_app.activities.DangKyActivity;
import com.example.fcinema_app.activities.ThanhToanActivity;
import com.example.fcinema_app.fragments.CaiDatFragment;
import com.example.fcinema_app.fragments.LichSuVeFragment;
import com.example.fcinema_app.fragments.PhimDangChieuFragment;
import com.example.fcinema_app.fragments.PhimSapChieuFragment;
import com.example.fcinema_app.models.NguoiDung;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.nav_view);

        ReplaceFragment(new PhimDangChieuFragment());

        mNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_home){
                ReplaceFragment(new PhimDangChieuFragment());
                return true;
            }
            if(item.getItemId() == R.id.nav_reel){
                ReplaceFragment(new PhimSapChieuFragment());
                return true;
            }
            if(item.getItemId() == R.id.nav_history){
                ReplaceFragment(new LichSuVeFragment());
                return true;
            }
            if(item.getItemId() == R.id.nav_setting){
                ReplaceFragment(new CaiDatFragment());
                return true;
            }
            return false;
        });

        getEmail();
        getNguoiDungByEmail(new NguoiDungCallback() {
            @Override
            public void onNguoiDungReceived(NguoiDung nguoiDung) {

            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame_layout,fragment);
        fragmentTransaction.commit();
    }
    public void getNguoiDungByEmail(final NguoiDungCallback callback){
        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<NguoiDung> call=apiInterface.getNguoiDungByEmail(getEmail());
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful()) {
                    NguoiDung nguoiDung = response.body();
                    callback.onNguoiDungReceived(nguoiDung);
                } else {
                    callback.onError("Lỗi");
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Log.i("Lỗi", t.getMessage());

            }
        });

    }
    public String getEmail(){
        Intent ilogin=getIntent();
        String email=ilogin.getStringExtra("email");
        return email;
    }
}