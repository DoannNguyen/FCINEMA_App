package com.example.fcinema_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fcinema_app.R;
import com.example.fcinema_app.activities.DoiMatKhauActivity2;
import com.example.fcinema_app.activities.DoiThongTinActivity;


public class CaiDatFragment extends Fragment {

    private Button btnDoiMatKhau, btnSuaThogTin;

    public CaiDatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cai_dat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSuaThogTin = view.findViewById(R.id.btnChinhSuaThongTin);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);

        btnSuaThogTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoiThongTinActivity.class));
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DoiMatKhauActivity2.class));
            }
        });
    }
}