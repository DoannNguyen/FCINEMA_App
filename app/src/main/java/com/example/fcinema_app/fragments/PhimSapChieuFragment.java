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
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.activities.ChiTietPhimSapChieuActivity;
import com.example.fcinema_app.adapters.PhimSapChieuAdapter;
import com.example.fcinema_app.models.PhimSapChieuModel;
import com.example.fcinema_app.models.TheLoaiModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhimSapChieuFragment extends Fragment {

    private GridView gridView;
    private List<PhimSapChieuModel> list;
    private PhimSapChieuAdapter phimSapChieuAdapter;
    private APIInterface mAPIInterface;
    private List<TheLoaiModel> mList;
    private LinearLayout mLayout;

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
        mLayout = view.findViewById(R.id.buttonContainer);

        list = new ArrayList<>();
        mList = new ArrayList<>();
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        getAllPhimSC();
        getTheLoai();

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

    private void getAllPhimSC(){

        Call<List<PhimSapChieuModel>> call = mAPIInterface.getAllPhimSC();
        call.enqueue(new Callback<List<PhimSapChieuModel>>() {
            @Override
            public void onResponse(Call<List<PhimSapChieuModel>> call, Response<List<PhimSapChieuModel>> response) {
                if(response.isSuccessful()){
                    list.clear();
                    list.addAll(response.body());
                    phimSapChieuAdapter.notifyDataSetChanged();
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
    private void getTheLoai(){
        Call<List<TheLoaiModel>> call = mAPIInterface.getTheLoai();
        call.enqueue(new Callback<List<TheLoaiModel>>() {
            @Override
            public void onResponse(Call<List<TheLoaiModel>> call, Response<List<TheLoaiModel>> response) {
                    if(response.isSuccessful()){
                        mList.clear();
                        mList.addAll(response.body());
                        for(int i = 0 ; i <= mList.size() ; i ++){
                            Button button = new Button(getContext());
                            if((i - 1) < 0){
                                button.setText("Tất cả");
                            }else{
                                button.setText(mList.get(i - 1).getTenTheLoai());
                            }
                            int finalI = i;
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if((finalI - 1) < 0){
                                        getAllPhimSC();
                                    }else{
                                        getPhimByTheLoai(Integer.parseInt(mList.get(finalI - 1).getId()));
                                    }
                                }
                            });
                            mLayout.addView(button);
                        }
                    }
            }

            @Override
            public void onFailure(Call<List<TheLoaiModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

    private void getPhimByTheLoai( int id){
        Call<List<PhimSapChieuModel>> call = mAPIInterface.getPhimSCbyTheLoai(id);
        call.enqueue(new Callback<List<PhimSapChieuModel>>() {
            @Override
            public void onResponse(Call<List<PhimSapChieuModel>> call, Response<List<PhimSapChieuModel>> response) {
                if(response.isSuccessful()){
                    list.clear();
                    list.addAll(response.body());
                    phimSapChieuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhimSapChieuModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

}