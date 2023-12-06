package com.example.fcinema_app.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.activities.ChiTietPhimSapChieuActivity;
import com.example.fcinema_app.activities.TimKiemPhimSapChieuActivity;
import com.example.fcinema_app.adapters.PhimSapChieuAdapter;
import com.example.fcinema_app.models.PhimSapChieuModel;
import com.example.fcinema_app.models.ProgressDialog;
import com.example.fcinema_app.models.TheLoaiModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhimSapChieuFragment extends Fragment {

    private GridView gridView;
    private ImageView imgSearch;
    private List<PhimSapChieuModel> list;
    private PhimSapChieuAdapter phimSapChieuAdapter;
    private APIInterface mAPIInterface;
    private List<TheLoaiModel> mList;
    private LinearLayout mLayout;
    private List<TextView> textViews = new ArrayList<>();
    private TextView tvNoItem;
    private ProgressDialog mProgressDialog;
    private Dialog mDialog;

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
        imgSearch=view.findViewById(R.id.imgSearchNewFilm);
        tvNoItem = view.findViewById(R.id.tvNoItemPSC);
        tvNoItem.setVisibility(View.GONE);

        mDialog = new Dialog(getContext());
        mDialog.setContentView(R.layout.progress_dialog);
        mProgressDialog = new ProgressDialog(mDialog);
        list = new ArrayList<>();
        mList = new ArrayList<>();
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        mProgressDialog.DialogShowing();
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

        imgSearch.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TimKiemPhimSapChieuActivity.class));
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
                    tvNoItem.setVisibility(View.GONE);
                    mProgressDialog.DialogDismiss();
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
                        assert response.body() != null;
                        mList.addAll(response.body());
                        for(int i = 0 ; i <= mList.size() ; i ++){
                            TextView textView = new TextView(getContext());
                            textView.setTag(i);
                            textView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radius));
                            textView.setTextSize(13);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );

                            layoutParams.setMargins(20, 20, 20, 20);
                            textView.setLayoutParams(layoutParams);

                            if((i - 1) < 0){
                                textView.setText("Tất cả");
                                textView.setTextColor(Color.WHITE);
                                textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.radius_fill));
                            }else{
                                textView.setText(mList.get(i - 1).getTenTheLoai());
                            }
                            int finalI = i;
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    for (int j = 0; j < textViews.size(); j++) {
                                        TextView tv = textViews.get(j);
                                        tv.setTextColor(Color.BLACK);
                                        tv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.radius));
                                    }

                                    textView.setTextColor(Color.WHITE);
                                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.radius_fill));

                                    if((finalI - 1) < 0){
                                        getAllPhimSC();
                                    }else{
                                        getPhimByTheLoai(Integer.parseInt(mList.get(finalI - 1).getId()));
                                    }
                                }
                            });
                            mLayout.addView(textView);
                            textViews.add(textView);
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
        mProgressDialog.DialogShowing();
        Call<List<PhimSapChieuModel>> call = mAPIInterface.getPhimSCbyTheLoai(id);
        call.enqueue(new Callback<List<PhimSapChieuModel>>() {
            @Override
            public void onResponse(Call<List<PhimSapChieuModel>> call, Response<List<PhimSapChieuModel>> response) {
                if(response.isSuccessful()){
                    list.clear();
                    list.addAll(response.body());
                    if (list.size() == 0){
                        tvNoItem.setVisibility(View.VISIBLE);
                    }else{
                        tvNoItem.setVisibility(View.GONE);
                    }
                    phimSapChieuAdapter.notifyDataSetChanged();
                    mProgressDialog.DialogDismiss();
                }
            }

            @Override
            public void onFailure(Call<List<PhimSapChieuModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

}