package com.example.fcinema_app.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.fcinema_app.models.ProgressDialog;
import com.example.fcinema_app.models.TheLoaiModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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
        private TextView tvHelloUser, tvNoItem;
        private APIInterface mAPIInterface;
        private LinearLayout mLayout;
        private List<TextView> textViews = new ArrayList<>();
        private Timer timer;
        private List<String> listDate;
        private ProgressDialog mProgressDialog;

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
        List<TheLoaiModel> list2 = new ArrayList<>();
        listDate = new ArrayList<>();
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.progress_dialog);

        mProgressDialog = new ProgressDialog(dialog);

        viewPager=view.findViewById(R.id.viewPagerSlider);
        circleIndicator=view.findViewById(R.id.circle_indicator);
        Toolbar toolbar = view.findViewById(R.id.toolbarPDC);
        GridView gridView = view.findViewById(R.id.gridview);
        tvHelloUser=view.findViewById(R.id.tvHelloUser);
        mLayout = view.findViewById(R.id.buttonContainer2);
        tvNoItem = view.findViewById(R.id.tvNoItemPhim);
        mAPIInterface = APIClient.getClient().create(APIInterface.class);
        getTime();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.navSearch){
                    Intent intent = new Intent(getContext(),TimKiemActivity.class);
                    intent.putExtra("email",getEmail());
                    startActivity(intent);
                    return  true;
                }
                return false;
            }
        });

        mProgressDialog.DialogShowing();
        mAdapter = new PhimAdapter(getContext(),mModelList);
        getAllPhim(mModelList,mAdapter,mProgressDialog, mAPIInterface);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void getAllPhim(List<PhimModel> modelList, PhimAdapter adapter, ProgressDialog progressDialog, APIInterface APIInterface){
        Call<List<PhimModel>> call = APIInterface.getAllPhimDC();
        call.enqueue(new Callback<List<PhimModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PhimModel>> call, @NonNull Response<List<PhimModel>> response) {
                if(response.isSuccessful()){
                    modelList.clear();
                    assert response.body() != null;
                    modelList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    setupImageSlideShow();
                    if(response.body().size() != 0){
                        tvNoItem.setVisibility(View.GONE);
                    }else{
                        tvNoItem.setVisibility(View.VISIBLE);
                    }
                    progressDialog.DialogDismiss();
                }else{
                    Log.e("TAG", "onResponse: error " );
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<PhimModel>> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }
    private void setupImageSlideShow() {
        imageUrlList.clear();
        for (PhimModel phimModel : mModelList) {
            PhimModel newPhim = new PhimModel(phimModel.getImage());
            imageUrlList.add(newPhim);
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

    private void getPhimByDay(String day ){
        mProgressDialog.DialogShowing();
        Call<List<PhimModel>> call = mAPIInterface.getPhimDCbyTheLoai(day);
        call.enqueue(new Callback<List<PhimModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PhimModel>> call, @NonNull Response<List<PhimModel>> response) {
                if(response.isSuccessful()){
                    mModelList.clear();
                    assert response.body() != null;
                    mModelList.addAll(response.body());
                    if(response.body().size() == 0){
                        tvNoItem.setVisibility(View.VISIBLE);
                    }else{
                        tvNoItem.setVisibility(View.GONE);
                    }
                    mAdapter.notifyDataSetChanged();
                    mProgressDialog.DialogDismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PhimModel>> call, @NonNull Throwable t) {
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
        String id = activity.getEmail();
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

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void getTime(){
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            TextView textView = new TextView(getContext());
            textView.setTag(i);
            textView.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.radius));
            textView.setTextSize(13);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            layoutParams.setMargins(20, 20, 20, 20);
            textView.setLayoutParams(layoutParams);

            if(i == 0){
                textView.setText("Today");
            }else{
                textView.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
            }
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < textViews.size(); j++) {
                        TextView tv = textViews.get(j);
                        tv.setTextColor(Color.BLACK);
                        tv.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.radius));
                    }
                    getPhimByDay(listDate.get(finalI));
                    textView.setTextColor(Color.WHITE);
                    view.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.radius_fill));
                }
            });
            Log.e("TAG", "getTime: "+(new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime())) );
            Log.e("TAG", "getTime: "+ (new SimpleDateFormat("dd/MM").format(calendar.getTime())));
            listDate.add(new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            mLayout.addView(textView);
            textViews.add(textView);
        }

    }
}