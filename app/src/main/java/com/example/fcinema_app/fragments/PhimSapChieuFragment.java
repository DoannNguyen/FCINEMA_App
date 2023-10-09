package com.example.fcinema_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.activities.ChiTietPhimSapChieuActivity;
import com.example.fcinema_app.adapters.PhimSapChieuAdapter;
import com.example.fcinema_app.models.PhimSapChieuModel;

import java.util.ArrayList;
import java.util.List;


public class PhimSapChieuFragment extends Fragment {

    GridView gridView;
    List<PhimSapChieuModel> list;
    PhimSapChieuAdapter phimSapChieuAdapter;

    public PhimSapChieuFragment() {
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
        return inflater.inflate(R.layout.fragment_phim_sap_chieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.gridViewPSC);
        list = new ArrayList<>();

        for(int i = 0; i <6 ; i++){
            PhimSapChieuModel model = new PhimSapChieuModel(R.drawable.imagedemo,"Ky Sinh Trung "+i, "Kinh di, than thoai", "Mỹ", "2023", "1h30p", "Tiếng anh", "Kim Cho Mun", "mo ta");
            list.add(model);
        }
        phimSapChieuAdapter = new PhimSapChieuAdapter(getContext(),list);
        gridView.setAdapter(phimSapChieuAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietPhimSapChieuActivity.class);
                intent.putExtra("phimSC", list.get(i));
                startActivity(intent);
            }
        });
    }
}