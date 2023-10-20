package com.example.fcinema_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.activities.ChiTietVeActivity;
import com.example.fcinema_app.adapters.LichSuVeAdapter;
import com.example.fcinema_app.models.LichSuVeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        getVeDat();
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
    private void getVeDat() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<LichSuVeModel>> call = apiInterface.getVeDat();
        call.enqueue(new Callback<List<LichSuVeModel>>() {
            @Override
            public void onResponse(Call<List<LichSuVeModel>> call, Response<List<LichSuVeModel>> response) {
                if(response.isSuccessful()){
                    list.clear();
                    list.addAll(response.body());
                    lichSuVeAdapter.notifyDataSetChanged();
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
}