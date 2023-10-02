package com.example.fcinema_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fcinema_app.fragments.CaiDatFragment;
import com.example.fcinema_app.fragments.LichSuVeFragment;
import com.example.fcinema_app.fragments.PhimDangChieuFragment;
import com.example.fcinema_app.fragments.PhimSapChieuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame_layout,fragment);
        fragmentTransaction.commit();
    }
}