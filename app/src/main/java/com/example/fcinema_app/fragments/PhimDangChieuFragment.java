package com.example.fcinema_app.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.activities.ChiTietPhimActivity;
import com.example.fcinema_app.activities.ThongBaoActivity;
import com.example.fcinema_app.activities.TimKiemActivity;
import com.example.fcinema_app.adapters.PhimAdapter;
import com.example.fcinema_app.models.PhimModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhimDangChieuFragment extends Fragment {

        private ImageSlider mSlider;
        private List<SlideModel> mList;
        private List<PhimModel> mModelList=new ArrayList<>();
         private PhimAdapter mAdapter;
        private  GridView mGridView;
        private androidx.appcompat.widget.Toolbar mToolbar;
        private APIInterface mAPIInterface;

    public PhimDangChieuFragment() {
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
        return inflater.inflate(R.layout.fragment_phim_dang_chieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = new ArrayList<>();
        mModelList = new ArrayList<>();

        mToolbar = view.findViewById(R.id.toolbarPDC);
        mGridView = view.findViewById(R.id.gridview);
        mSlider = view.findViewById(R.id.image_slider);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.navSearch){
                    startActivity(new Intent(getContext(), TimKiemActivity.class));
                    return  true;
                }
                if(item.getItemId() == R.id.navNotification){
                    startActivity(new Intent(getContext(), ThongBaoActivity.class));
                    return  true;
                }
                return false;
            }
        });

        for (int i = 0; i < 6; i++){
            mList.add(new SlideModel(R.drawable.poster, ScaleTypes.FIT));
        }
        getAllPhim();
        mAdapter = new PhimAdapter(getContext(),mModelList);
        mGridView.setAdapter(mAdapter);

        mSlider.setImageList(mList);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietPhimActivity.class);
                intent.putExtra("phim", mModelList.get(i));
                startActivity(intent);
            }
        });

    }
    private void getAllPhim(){
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<PhimModel>> call = mAPIInterface.getAllPhimDC();
        call.enqueue(new Callback<List<PhimModel>>() {
            @Override
            public void onResponse(Call<List<PhimModel>> call, Response<List<PhimModel>> response) {
                if(response.isSuccessful()){
                    mModelList.clear();
                    mModelList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();

                }else{
                    Log.e("TAG", "onResponse: error " );
                }

            }

            @Override
            public void onFailure(Call<List<PhimModel>> call, Throwable t) {

            }
        });
    }
}