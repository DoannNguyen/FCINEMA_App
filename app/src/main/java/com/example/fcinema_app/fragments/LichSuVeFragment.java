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
import android.widget.ListView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.activities.ChiTietVeActivity;
import com.example.fcinema_app.adapters.LichSuVeAdapter;
import com.example.fcinema_app.models.LichSuVeModel;

import java.util.ArrayList;
import java.util.List;


public class LichSuVeFragment extends Fragment {

    ListView listView;
    List<LichSuVeModel> list;

    LichSuVeAdapter lichSuVeAdapter;

    public LichSuVeFragment() {
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
        return inflater.inflate(R.layout.fragment_lich_su_ve, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listview);
        list = new ArrayList<>();

        for (int i = 0; i < 8 ; i++ ){
            LichSuVeModel model = new LichSuVeModel("Rambo"+i, "50.000", "Đã thanh toan", "10h-12h", "VIET01", "20-12-2023", "01", "Ca3", "C14", "Tiền mặt", "50.000", "01");
            list.add(model);

        }
        lichSuVeAdapter = new LichSuVeAdapter(getContext(),list);
        listView.setAdapter(lichSuVeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietVeActivity.class);
                intent.putExtra("LCV", list.get(i));
                startActivity(intent);
            }
        });


    }
}