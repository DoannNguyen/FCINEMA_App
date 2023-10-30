package com.example.fcinema_app.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fcinema_app.MainActivity;
import com.example.fcinema_app.R;
import com.example.fcinema_app.Utils.APIClient;
import com.example.fcinema_app.Utils.APIInterface;
import com.example.fcinema_app.Utils.NguoiDungCallback;
import com.example.fcinema_app.activities.ChiTietPhimActivity;
import com.example.fcinema_app.activities.TimKiemActivity;
import com.example.fcinema_app.adapters.ImageSlideShowAdapter;
import com.example.fcinema_app.adapters.PhimAdapter;
import com.example.fcinema_app.models.NguoiDung;
import com.example.fcinema_app.models.PhimModel;
import com.example.fcinema_app.models.TheLoaiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhimDangChieuFragment extends Fragment {

        private ViewPager viewPager;
        private CircleIndicator circleIndicator;
        private ImageSlideShowAdapter imageSlideShowAdapter;
        private List<PhimModel> mModelList=new ArrayList<>();
        private List<PhimModel> imageUrlList = new ArrayList<>();

        private PhimAdapter mAdapter;
        private  GridView mGridView;
        private TextView tvHelloUser;
        private androidx.appcompat.widget.Toolbar mToolbar;
        private APIInterface mAPIInterface;
        private LinearLayout mLayout;
        private List<TheLoaiModel> mList2;
        private List<TextView> textViews = new ArrayList<>();
        private Timer timer;

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
        return inflater.inflate(R.layout.fragment_phim_dang_chieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mModelList = new ArrayList<>();
        mList2 = new ArrayList<>();

        viewPager=view.findViewById(R.id.viewPagerSlider);
        circleIndicator=view.findViewById(R.id.circle_indicator);
        mToolbar = view.findViewById(R.id.toolbarPDC);
        mGridView = view.findViewById(R.id.gridview);
        tvHelloUser=view.findViewById(R.id.tvHelloUser);
        mLayout = view.findViewById(R.id.buttonContainer2);
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        getTheLoai();

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.navSearch){
                    startActivity(new Intent(getContext(), TimKiemActivity.class));
                    return  true;
                }
                return false;
            }
        });


        getAllPhim();

        mAdapter = new PhimAdapter(getContext(),mModelList);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietPhimActivity.class);
                intent.putExtra("phim", mModelList.get(i));
                intent.putExtra("email", getEmail());
                startActivity(intent);
            }
        });
        getNguoiDung();

    }

    private void getAllPhim(){
        Call<List<PhimModel>> call = mAPIInterface.getAllPhimDC();
        call.enqueue(new Callback<List<PhimModel>>() {
            @Override
            public void onResponse(Call<List<PhimModel>> call, Response<List<PhimModel>> response) {
                if(response.isSuccessful()){
                    mModelList.clear();
                    mModelList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();

                    setupImageSlideShow();
                }else{
                    Log.e("TAG", "onResponse: error " );
                }

            }

            @Override
            public void onFailure(Call<List<PhimModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }
    private void setupImageSlideShow() {
        for (PhimModel phimModel : mModelList) {
            phimModel=new PhimModel(phimModel.getImage());
            imageUrlList.add(phimModel);

        }
        imageSlideShowAdapter=new ImageSlideShowAdapter(getContext(),imageUrlList);
        viewPager.setAdapter(imageSlideShowAdapter);
        circleIndicator.setViewPager(viewPager);
        imageSlideShowAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideShow();
    }
    private void autoSlideShow(){
        if(imageUrlList==null || imageUrlList.isEmpty() || viewPager==null){
            return;
        }

        if(timer==null){
            timer=new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currenPhoto=viewPager.getCurrentItem();
                        int totalPhoto=imageUrlList.size()-1;
                        if(currenPhoto<totalPhoto){
                            currenPhoto++;
                            viewPager.setCurrentItem(currenPhoto);
                        }else{
                            viewPager.setCurrentItem(0);

                        }
                    }
                });

            }
        },500,3000);


    }
    private void getTheLoai(){
        Call<List<TheLoaiModel>> call = mAPIInterface.getTheLoai();
        call.enqueue(new Callback<List<TheLoaiModel>>() {
            @Override
            public void onResponse(Call<List<TheLoaiModel>> call, Response<List<TheLoaiModel>> response) {
                if(response.isSuccessful()){
                    mList2.clear();
                    mList2.addAll(response.body());

                    for(int i = 0 ; i <= mList2.size() ; i ++){
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
                        }else{
                            textView.setText(mList2.get(i - 1).getTenTheLoai());
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
                                    getAllPhim();

                                }else{
                                    getPhimByTheLoai(Integer.parseInt(mList2.get(finalI - 1).getId()));

                                }
                            }
                        });
                        mLayout.addView(textView);
                        textViews.add(textView);

                    }
                    if (!textViews.isEmpty()) {
                        textViews.get(0).setBackground(ContextCompat.getDrawable(getContext(),R.drawable.radius));
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
        Call<List<PhimModel>> call = mAPIInterface.getPhimDCbyTheLoai(id);
        call.enqueue(new Callback<List<PhimModel>>() {
            @Override
            public void onResponse(Call<List<PhimModel>> call, Response<List<PhimModel>> response) {
                if(response.isSuccessful()){
                    mModelList.clear();
                    mModelList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhimModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

    private void getNguoiDung(){
        MainActivity activity=(MainActivity) getActivity();
        activity.getNguoiDungByEmail(new NguoiDungCallback() {
            @Override
            public void onNguoiDungReceived(NguoiDung nguoiDung) {
                if(!nguoiDung.getHoTen().isEmpty() && nguoiDung.getHoTen()!=null){
                    tvHelloUser.setText("Xin chào "+nguoiDung.getHoTen());
                }else{
                    tvHelloUser.setText("Xin chào");
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private String getEmail(){
        MainActivity activity=(MainActivity) getActivity();
        String id=activity.getEmail();
        return id;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }
}